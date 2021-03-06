package ca.jrvs.apps.twitter.dao.helper;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class TwitterHttpHelper implements HttpHelper{

  private OAuthConsumer consumer;
  private HttpClient httpClient;

  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    httpClient = HttpClientBuilder.create().build();
  }

  public TwitterHttpHelper() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    httpClient = HttpClientBuilder.create().build();

  }

  private HttpResponse httpRequestExecutor(HttpMethod method, URI uri, StringEntity stringEntity) throws OAuthException, IOException {
    if (method == HttpMethod.GET) {
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } else if (method == HttpMethod.POST) {
      HttpPost request = new HttpPost(uri);
      if (stringEntity != null) {
        request.setEntity(stringEntity);
      }
      consumer.sign(request);
      return httpClient.execute(request);
    } else {
      throw new IllegalArgumentException("Invalid HTTP method: " + method.name());
    }

  }

  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      return httpRequestExecutor(HttpMethod.POST, uri, null);
    } catch (IOException e) {
      throw new RuntimeException("IOException: Request failed!", e);
    } catch (OAuthException e) {
      throw new RuntimeException("OAuthException: Request failed!", e);
    }
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    try {
      return httpRequestExecutor(HttpMethod.GET, uri, null);
    } catch (IOException e) {
      throw new RuntimeException("IOException: Request failed!", e);
    } catch (OAuthException e) {
      throw new RuntimeException("OAuthException: Request failed!", e);
    }
  }
}
