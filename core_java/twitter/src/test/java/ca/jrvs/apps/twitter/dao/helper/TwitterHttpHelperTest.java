package ca.jrvs.apps.twitter.dao.helper;

import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class TwitterHttpHelperTest extends TestCase {

  public void testHttpPost() throws Exception{
    String status = "you can lead a robot horse to water but you cant make it think";
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    TwitterHttpHelper httpHelper = new TwitterHttpHelper();
    URI uri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status));

    HttpResponse response = httpHelper.httpPost(uri);
    String expected = "{\"created_at...";
    assertEquals(expected.substring(2,11), EntityUtils.toString(response.getEntity()).substring(2,11));
  }

  public void testHttpGet() throws Exception{
    TwitterHttpHelper httpHelper = new TwitterHttpHelper();
    URI uri = new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=mitchactonDev");

    HttpResponse response = httpHelper.httpGet(uri);
    String expected = "[{\"created_at...";
    assertEquals(expected.substring(2,11), EntityUtils.toString(response.getEntity()).substring(2,11));
  }
}