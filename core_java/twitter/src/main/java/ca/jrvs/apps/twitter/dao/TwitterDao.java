package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";

  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet tweet){
    URI uri;
    try {
      uri = getPostUri(tweet);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Could not create URI", e);
    }
    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponse(response, HTTP_OK);
  }

  @Override
  public Tweet findById(String id) {
    URI uri;
    try {
      uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL
      + id);
    } catch (URISyntaxException e) {
      throw new RuntimeException("Could not create URI", e);
    }
    HttpResponse response = httpHelper.httpGet(uri);
    return parseResponse(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String id) {
    URI uri;
    try {
      uri = new URI(API_BASE_URI + DELETE_PATH + id + ".json");
    } catch (URISyntaxException e) {
      throw new RuntimeException("Could not create URI", e);
    }
    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponse(response, HTTP_OK);
  }

  public Tweet parseResponse(HttpResponse response, Integer expectedStatusCode){
    Tweet tweet;
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status: " + status);
    }
    if (response.getEntity() == null) {
      throw new RuntimeException("Empty response body");
    }

    String jsonStr;
    try {
      jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert entity to String", e);
    }

    try {
      tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
    }catch (IOException e) {
      throw new RuntimeException("Unable to convert JSON string to Object", e);
    }
    return tweet;
  }


  public URI getPostUri(Tweet tweet) throws URISyntaxException {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    URI uri = null;
    try {
      uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status"
          + EQUAL + percentEscaper.escape(tweet.getText()) + AMPERSAND + "long"
          + EQUAL + tweet.getCoordinates().getCoordinates(0)
          + AMPERSAND + "lat" + EQUAL + tweet.getCoordinates().getCoordinates(1));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Couldn't create URI", e);
    }
    return uri;
  }
}
