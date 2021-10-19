package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterService implements Service {
  private CrdDao dao;

  @Autowired
  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    if (validText(tweet.getText()) && validCoordinates(tweet.getCoordinates().getCoordinates())){
      return (Tweet) dao.create(tweet);
    } else{
      throw new IllegalArgumentException("Tweet exceeds 140 character limit!");
    }
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    if (validId(id)) {
      Tweet response = (Tweet) dao.findById(id);
      Tweet returnTweet = new Tweet();
      for (String field:fields){
        switch (field) {
          case "created_at":
            returnTweet.setCreatedAt(response.getCreatedAt());
            break;
          case "id":
            returnTweet.setId(response.getId());
            break;
          case "id_str":
            returnTweet.setIdStr(response.getIdStr());
            break;
          case "text":
            returnTweet.setText(response.getText());
            break;
          case "entities":
            returnTweet.setEntities(response.getEntities());
            break;
          case "coordinates":
            returnTweet.setCoordinates(response.getCoordinates());
            break;
          case "retweet_count":
            returnTweet.setRetweetCount(response.getRetweetCount());
            break;
          case "favorite_count":
            returnTweet.setFavoriteCount(response.getFavoriteCount());
            break;
          case "retweeted":
            returnTweet.setRetweeted(response.getRetweeted());
            break;
          case "favorited":
            returnTweet.setFavorited(response.getFavorited());
            break;
        }
      }
      return returnTweet;
    } else {
      throw new IllegalArgumentException("Invalid tweet ID!");
    }
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> tweets = new ArrayList<>();
    for (String id:ids) {
      if (validId(id)){
        tweets.add((Tweet) dao.deleteById(id));
      } else {
        throw new IllegalArgumentException("Invalid tweet ID!");
      }
    }
    return tweets;
  }

  public boolean validText(String text) {
    return text.length()<=140;
  }

  public boolean validCoordinates(float[] coords) {
    if (coords[0]> -180 && coords[0] < 180 && coords[1] > -90 && coords[1] < 90){
      return true;
    } else {
      return false;
    }
  }

  public boolean validId(String id) {
    try {
      Long.parseUnsignedLong(id);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

}
