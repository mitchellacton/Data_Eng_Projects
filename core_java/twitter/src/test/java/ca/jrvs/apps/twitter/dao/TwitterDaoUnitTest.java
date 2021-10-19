package ca.jrvs.apps.twitter.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
  private Tweet tweet;
  private String tweetJsonStr = "{\n"
      + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
      + "   \"id\":1097607853932564480,\n"
      + "   \"id_str\":\"1097607853932564480\",\n"
      + "   \"text\":\"test with loc223\",\n"
      + "   \"entities\":{\n"
      + "        \"hashtags\":[],\n"
      + "        \"user_mentions\":[]\n"
      + "   },\n"
      + "   \"coordinates\":null,\n"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";
  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  @Before
  public void setup() {
//    this.tweet = new Tweet();
//    String hashTag = " #GoodbyeWorld";
//    String text = "Your birthright was to die";
//    Coordinates coordinates = new Coordinates();
//    float lon = 32.039f;
//    float lat = 125.763f;
//    coordinates.setCoordinates(new float[] {lat, lon});
//
//    this.tweet.setText(text + hashTag);
//    this.tweet.setCoordinates(coordinates);
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
  }

  @Test
  public void findById() throws IOException {
    // failure
    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock exception"));
    Tweet tweet = Mockito.spy(Tweet.class);
    try {
      dao.findById("4815162342");
    } catch(RuntimeException e) {
      assertTrue(true);
    }
    // success
    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expected = JsonParser.toObjectFromJson(tweetJsonStr, tweet.getClass());
    doReturn(expected).when(spyDao).parseResponse(any(), anyInt());
    Tweet successTweet = spyDao.findById("1001001");
    assertNotNull(successTweet);
    assertNotNull(successTweet.getText());

  }

  @Test
  public void create() throws IOException{
    // failure case
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock test"));
    Tweet testTweet = new Tweet();
    String hashTag = " #123";
    String text = "abc ";
    Coordinates coordinates = new Coordinates();
    float lon = 32.039f;
    float lat = 125.763f;
    coordinates.setCoordinates(new float[] {lat, lon});
    testTweet.setText(text + hashTag);
    testTweet.setCoordinates(coordinates);

    try {
      dao.create(testTweet);
    } catch (RuntimeException e) {
      assertTrue(true);
    }
    // success case
    Tweet tweet = Mockito.spy(Tweet.class);
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expected = JsonParser.toObjectFromJson(tweetJsonStr, tweet.getClass());
    doReturn(expected).when(spyDao).parseResponse(any(), anyInt());
    Tweet successTweet = spyDao.create(testTweet);
    assertNotNull(successTweet);
    assertNotNull(successTweet.getText());
  }

  @Test
  public void deleteById() throws IOException{
    // failure
    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock exception"));
    Tweet tweet = Mockito.spy(Tweet.class);
    try {
      dao.deleteById("4815162342");
    } catch(RuntimeException e) {
      assertTrue(true);
    }
    // success
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expected = JsonParser.toObjectFromJson(tweetJsonStr, tweet.getClass());
    doReturn(expected).when(spyDao).parseResponse(any(), anyInt());
    Tweet successTweet = spyDao.deleteById("1001001");
    assertNotNull(successTweet);
    assertNotNull(successTweet.getText());

  }
}


