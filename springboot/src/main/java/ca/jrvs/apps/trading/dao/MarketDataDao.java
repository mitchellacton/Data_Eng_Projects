package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.helper.JsonParser;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;

/**
 * Responsible for getting quotes from IEX
 */
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }


  /**
   * Get and IexQuote
   * @param ticker
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));
    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      iexQuote = Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }
    return iexQuote;
  }

  /**
   * Get Quotes from IEX
   * @param tickers list of tickers
   * @return a list of IexQuote objects
   * @throws IllegalArgumentException if any ticker is invalid or tickers is empty
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    List<IexQuote> quotes = new ArrayList<>();
    String tickersStr = String.join(",", tickers);
    try {
      final String IEX_FULL_BATCH_URL = String.format(IEX_BATCH_URL, tickersStr.toString());
      String response = executeHttpGet(IEX_FULL_BATCH_URL).orElseThrow(
          () -> new IllegalArgumentException("tickers invalid or empty"));
      JSONObject jsonQuotes = new JSONObject(response);

      for (String key : tickers) {
        if (jsonQuotes.has(key)) {
          JSONObject jsonQuote = jsonQuotes.getJSONObject(key);
          quotes.add(JsonParser.toObjectFromJson(jsonQuote.get("quote").toString(), IexQuote.class));
        } else {
          throw new DataRetrievalFailureException("Unknown Symbol:" + key);
        }
      }
      return quotes;
    } catch (DataRetrievalFailureException e) {
      throw new RuntimeException("Failed to retrieve quotes", e);
    } catch (IOException e) {
      throw new RuntimeException("Failed to retrieve quotes", e);
    }
  }

  /**
   * Execute a get and return http entity/body as a string
   * Tip: use EntityUtils.toString to process HTTP entity
   * @param url resource URL
   * @return http response body or Optional.empty for 404 responses
   * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
   */
  private Optional<String> executeHttpGet(String url) throws DataRetrievalFailureException, IOException {
    Optional<String> responseBody;

    HttpGet request = new HttpGet(url);
    HttpClient httpClient = getHttpClient();
    HttpResponse response = httpClient.execute(request);
    if (response.getStatusLine().getStatusCode() ==404) {
      return Optional.empty();
    } else if (response.getStatusLine().getStatusCode() ==200) {
      responseBody = Optional.of(EntityUtils.toString(response.getEntity()));
    } else {
      throw new RuntimeException("Unexpected status code");
    }
    return responseBody;
  }

  private CloseableHttpClient getHttpClient(){
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented!");
  }
}
