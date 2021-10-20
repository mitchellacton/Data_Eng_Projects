package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoTest extends TestCase {

  @Test
  public void testCreate(){

    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(httpHelper);

    String hashTag = " #GoodbyeWorld";
    String text = "Your birthright was to die";
    Coordinates coordinates = new Coordinates();
    float lon = 32.039f;
    float lat = 125.763f;
    coordinates.setCoordinates(new float[] {lat, lon});

    Tweet postTweet = new Tweet();
    postTweet.setText(text + hashTag);
    postTweet.setCoordinates(coordinates);

    Tweet response = twitterDao.create(postTweet);
    assertEquals(postTweet.getText(), response.getText());
    assertNotNull(response.getCoordinates());
    assertEquals(2, response.getCoordinates().size());
    assertEquals(postTweet.getCoordinates().getCoordinates(0), response.getCoordinates().getCoordinates(0));
    assertEquals(postTweet.getCoordinates().getCoordinates(1), response.getCoordinates().getCoordinates(1));
    assertTrue(response.getText().contains("#GoodbyeWorld"));
  }
  @Test
  public void testFindById() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(httpHelper);

    Tweet response = twitterDao.findById("1450194861840379904");
    System.out.println(response);
    assertEquals(response.getText(), "Your birthright was to die #GoodbyeWorld");
  }
  @Test
  public void testDeleteById() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(httpHelper);

    Tweet response = twitterDao.deleteById("1450194861840379904");
    System.out.println(response);
    assertNotNull(response.getText());
  }
}