package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  /**
   *
   * @param ticker id
   * @return IexQuote objectt
   * @throws IllegalArgumentException if ticker is invalid
   */
  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao.findById(ticker).orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
  }

  public void updateMarketData() {
    List<Quote> quotes = this.findAllQuotes();
    List<String> tickers = quotes.stream().map(quote -> quote.getId()).collect(Collectors.toList());
    saveQuotes(tickers);
  }
  public static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    Quote quote = new Quote();
    quote.setId(iexQuote.getSymbol());
    quote.setLastPrice(iexQuote.getLatestPrice());
    quote.setBidPrice(Double.valueOf(iexQuote.getIexBidPrice()));
    quote.setBidSize(iexQuote.getIexBidSize());
    quote.setAskPrice(Double.valueOf(iexQuote.getIexAskPrice()));
    quote.setAskSize(iexQuote.getIexAskSize());
    return quote;
  }
  public Quote saveQuote(String ticker) {
    Optional<IexQuote> iexQuote = marketDataDao.findById(ticker);
    return quoteDao.save(buildQuoteFromIexQuote(iexQuote.orElseThrow(() -> new
        IllegalArgumentException("Invalid ticker ID"))));
  }

  public List<Quote> saveQuotes(List<String> tickers) {
    List<Quote> quotes = tickers.stream().map(ticker -> saveQuote(ticker)).
        collect(Collectors.toList());
    return quotes;
  }

  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  public List<Quote> findAllQuotes() {
    return quoteDao.findAll();
  }

}
