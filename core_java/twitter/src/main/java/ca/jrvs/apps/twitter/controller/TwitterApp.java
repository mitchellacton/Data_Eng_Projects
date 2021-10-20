package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.List;

public class TwitterApp {

  private Controller controller;

  public TwitterApp(Controller controller) {
    this.controller = controller;
  }

  public static void main(String[] args) {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    TwitterDao dao = new TwitterDao(httpHelper);
    TwitterService service = new TwitterService(dao);
    TwitterController controller = new TwitterController(service);
    TwitterApp twitterApp = new TwitterApp(controller);

    twitterApp.run(args);
  }

  public void run(String[] args){
    if (args[0].equals("post")){
      displayTweet(controller.postTweet(args));
    } else if (args[0].equals("show")){
      displayTweet(controller.showTweet(args));
    }else if (args[0].equals("delete")) {
      List<Tweet> deletedTweets = controller.deleteTweet(args);
      for(Tweet tweet:deletedTweets){
        displayTweet(tweet);
      }
    }else {
      throw new IllegalArgumentException("First argument: \"post\"|\"show\"|\"delete\"");
    }
  }

  public void displayTweet(Tweet tweet){
    try {
      System.out.println(JsonParser.toJson(tweet, true, false));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Tweet conversion to JSON failed", e);
    }
  }
}