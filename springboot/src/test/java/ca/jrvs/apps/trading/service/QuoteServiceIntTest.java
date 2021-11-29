package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  private Quote testQuote;

  @Before
  public void setUp() throws Exception {
    quoteDao.deleteAll();
  }

  @Test
  public void findIexQuoteByTicker() {
    assertEquals("aapl",quoteService.findIexQuoteByTicker("aapl").getSymbol());
  }

  @Test
  public void updateMarketData() {
    testQuote = new Quote();
    testQuote.setAskPrice(10d);
    testQuote.setAskSize(10);
    testQuote.setBidPrice(10.2d);
    testQuote.setBidSize(10);
    testQuote.setId("aapl");
    testQuote.setLastPrice(10.1d);
    quoteDao.save(testQuote);

    assertEquals(testQuote,quoteDao.findById("aapl").get());
    quoteService.updateMarketData();
    assertNotEquals(testQuote,quoteDao.findById("aapl").get());
  }

  @Test
  public void saveQuote() {
    List<String> tickers = new ArrayList<>(Arrays.asList("aapl","tsla"));
    quoteService.saveQuotes(tickers);
    assertEquals("aapl",quoteDao.findById("aapl").get().getId());
    assertEquals("tsla",quoteDao.findById("tsla").get().getId());
  }

  @Test
  public void saveQuotes() {
    quoteService.saveQuote("aapl");
    testQuote = new Quote();
    testQuote.setAskPrice(10d);
    testQuote.setAskSize(10);
    testQuote.setBidPrice(10.2d);
    testQuote.setBidSize(10);
    testQuote.setId("tsla");
    testQuote.setLastPrice(10.1d);
    quoteService.saveQuote(testQuote);
    assertEquals(testQuote, quoteDao.findById("tsla").get());
  }

  @Test
  public void findAllQuotes() {
    testQuote = new Quote();
    testQuote.setAskPrice(10d);
    testQuote.setAskSize(10);
    testQuote.setBidPrice(10.2d);
    testQuote.setBidSize(10);
    testQuote.setId("aapl");
    testQuote.setLastPrice(10.1d);
    quoteService.saveQuote(testQuote);

    Quote testQuote2 = new Quote();
    testQuote2.setId("tsla");
    quoteService.saveQuote(testQuote2);

    List<Quote> quotes = new ArrayList<>();
    quotes.add(testQuote);
    quotes.add(testQuote2);

    List<Quote> actual = quoteService.findAllQuotes();
    assertEquals(quotes, actual);
  }
}