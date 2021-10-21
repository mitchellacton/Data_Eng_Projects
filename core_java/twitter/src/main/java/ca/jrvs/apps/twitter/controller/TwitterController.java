package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{
  private static final String COORD_SEP = ":";
  private static final String COMMA = ", ";

  private Service service;
  @Autowired
  public TwitterController (Service service) {
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3){
      throw new IllegalArgumentException(
          "Invalid arguments\n USAGE: TwitterApp post \"text\" \"latitude:longitude\"");
    }

    String text = args[1];
    String coords = args[2];
    String[] coordsArr = coords.split(COORD_SEP);

    if (coordsArr.length != 2) {
      throw new IllegalArgumentException(
          "Invalid coordinates \n COORDINATES FORMAT: \"latitude:longitude\"");
    }
    if (StringUtils.isEmpty(text)){
      throw new IllegalArgumentException(
          "No tweet text provided \n USAGE: TwitterApp post \"text\" \"latitude:longitude\"");
    }
    float lat;
    float lon;
    try {
      lat = Float.parseFloat(coordsArr[0]);
      lon = Float.parseFloat(coordsArr[1]);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Invalid coordinates \n COORDINATES FORMAT: \"latitude:longitude\");", e);
    }
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new float[] {lon, lat});
    tweet.setCoordinates(coordinates);
    tweet.setText(text);

    return service.postTweet(tweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if (args.length < 2){
      throw new IllegalArgumentException(
          "Not enough arguments\n USAGE: TwitterApp show \"id\" \"field1, field2, ...\"");
    }

    String id = args[1];
    String fields = args[2];
    String[] fieldsArr = fields.split(COMMA);


    if (StringUtils.isEmpty(id)){
      throw new IllegalArgumentException(
          "No tweet id provided \n USAGE: TwitterApp post \"id\" \"field1, field2, ...\"");
    }

    return service.showTweet(id, fieldsArr);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException(
          "Usage: TwitterApp delete \"tweet_id1, tweet_id2, ... \"");
    }
    String fields = args[1];
    String[] fieldsArr = fields.split(COMMA);

    return service.deleteTweets(fieldsArr);
  }

}
