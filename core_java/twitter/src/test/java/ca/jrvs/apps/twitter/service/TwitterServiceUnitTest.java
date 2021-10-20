package ca.jrvs.apps.twitter.service;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
  @Mock
  CrdDao mockDao;

  @InjectMocks
  TwitterService service;

  @Test
  public void postTweet() {
    Tweet tweet = new Tweet();
    String text = "Codeword: Sassafrass";
    Coordinates coords = new Coordinates();
    coords.setCoordinates(new float[] {44f, 81f});
    tweet.setText(text);
    tweet.setCoordinates(coords);

    when(mockDao.create(any())).thenReturn(new Tweet());
    service.postTweet(tweet);
  }

  @Test
  public void showTweet() {
    when(mockDao.findById(any())).thenReturn(new Tweet());
    service.showTweet("8374598237459832745", new String[] {"id", "text"});
  }

  @Test
  public void deleteTweets() {
    String[] ids = {"9837453894758934", "398475893745987345", "38947598237459832"};
    when(mockDao.deleteById(any())).thenReturn(new Tweet());
    service.deleteTweets(ids);
  }

}
