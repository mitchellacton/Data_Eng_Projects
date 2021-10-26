package ca.jrvs.apps.twitter.service;


import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import static org.junit.Assert.*;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {
  private TwitterHttpHelper httpHelper;
  private TwitterDao dao;
  private TwitterService service;
  private Tweet tweet;

  @Before
  public void setup(){
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    this.httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    this.dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(this.dao);

    String text = "Codeword: Sassafrass";
    Coordinates coords = new Coordinates();
    coords.setCoordinates(new float[] {26f, 28f});

    this.tweet = new Tweet();
    this.tweet.setText(text);
    this.tweet.setCoordinates(coords);
  }

  @Test
  public void postTweetTest(){
    Tweet response = service.postTweet(tweet);
    assertTrue(response.getText().contains("Sassafrass"));
  }

  @Test
  public void showTweetTest(){
    String testId = "1449074897444032526";
    Tweet response = service.showTweet(testId, new String[] {"text"});
    assertTrue(response.getText().contains("robot"));
  }

  @Test
  public void deleteTweetsTest(){
    // requires test ids to be updated since tweets are deleted after each test
    String[]  ids = new String[] {"1450835871872262146", "1450835890927030274"};
    List<Tweet> deletedTweets = service.deleteTweets(ids);
    assertTrue(deletedTweets.size() == 2);

  }
}
