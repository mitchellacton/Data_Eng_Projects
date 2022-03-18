package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig dataConfig = new MarketDataConfig();
    dataConfig.setHost("https://cloud.iexapis.com/v1/");
    dataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
    return dataConfig;
  }

  /**
   * Setup connectionManager for passing in MarketDataDao
   *
   * @return HttpClientConnectionManager
   */
  @Bean
  public HttpClientConnectionManager httpClientConnectionManager() {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(50);
    connectionManager.setDefaultMaxPerRoute(50);
    return connectionManager;
  }

  @Bean
  public DataSource datasource() {
    String jdbcUrl =
        "jdbc:postgresql://" +
            System.getenv("PSQL_HOST") + ":" +
            System.getenv("PSQL_PORT") + "/" +
            System.getenv("PSQL_DB");

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(jdbcUrl);
    basicDataSource.setUsername(System.getenv("PSQL_USER"));
    basicDataSource.setPassword(System.getenv("PSQL_PASSWORD"));
    return basicDataSource;
  }
}