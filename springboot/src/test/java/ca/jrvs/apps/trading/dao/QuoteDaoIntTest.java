package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;
  private Quote savedQuote;
  @Before
  public void insertOne(){
    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
  }

  @After
  public void tearDown() throws Exception {
    quoteDao.deleteById(savedQuote.getId());
  }

  @Test
  public void save() {
    assertEquals(savedQuote, quoteDao.findById("aapl").get());
  }

  @Test
  public void saveAll() {
    Quote newQuote = new Quote();
    newQuote.setAskPrice(10d);
    newQuote.setAskSize(10);
    newQuote.setBidPrice(10.2d);
    newQuote.setBidSize(10);
    newQuote.setId("tsla");
    newQuote.setLastPrice(10.1d);

    List<Quote> quotes = new ArrayList<>();
    quotes.add(newQuote);
    quoteDao.saveAll(quotes);
    assertEquals(2, quoteDao.findAll().size());
  }

  @Test
  public void findById() {
    assertEquals(savedQuote, quoteDao.findById("aapl").get());
  }

  @Test
  public void existsById() {
    assertTrue(quoteDao.existsById("aapl"));
  }

  @Test
  public void findAll() {
    List<Quote> quotes = new ArrayList<>();
    quoteDao.findAll();
    assertEquals(2, quotes.size());

  }

  @Test
  public void count() {
    assertEquals(2, quoteDao.count());
  }

  @Test
  public void deleteAll() {
    quoteDao.deleteAll();
    assertEquals(0, quoteDao.count());
  }
}