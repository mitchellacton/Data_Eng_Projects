package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource){
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  @Override
  public <S extends Quote> S save(S quote) {
    if (existsById(quote.getId())) {
      int updateRowNo = updateOne(quote);
      if (updateRowNo != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      addOne(quote);
    }
    return quote;
  }

  private void addOne(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int row = simpleJdbcInsert.execute(parameterSource);
    if (row != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
    }
  }

  private int updateOne(Quote quote) {
    String update_sql = "UPDATE quote SET last_price=?, bid_price=?, "
        + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
    return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
  }

  private Object[] makeUpdateValues(Quote quote){
    return new Object[]{
        quote.getLastPrice(),
        quote.getBidPrice(), quote.getBidSize(),
        quote.getAskPrice(), quote.getAskSize(),
        quote.getId()
    };
  }

  @Override
  public <S extends Quote> List<S> saveAll(Iterable<S> quotes){
    List<S> result = new ArrayList<>();
    for (S quote : quotes) {
      result.add(this.save(quote));
    }
    return result;
  }

  @Override
  public Optional<Quote> findById(String ticker){
    String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    Optional<Quote> result = Optional.empty();

    try {
      result = Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery,
          BeanPropertyRowMapper.newInstance(Quote.class), ticker));
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find trader id:" + ticker, e);
    }
    if (result.isPresent()) {
      return result;
    }
    return Optional.empty();
  }

  @Override
  public boolean existsById(String ticker) {
    return findById(ticker).isPresent();
  }

  @Override
  public void deleteById(String ticker) {
    if (ticker == null) {
      throw new IllegalArgumentException("ID can't be null");
    }
    String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
    jdbcTemplate.update(deleteSql, ticker);
  }

  @Override
  public List<Quote> findAll() {
    List<Quote> quotes = jdbcTemplate.query("SELECT * FROM " + TABLE_NAME,
        BeanPropertyRowMapper.newInstance(Quote.class));
    return quotes;
  }

  @Override
  public long count() {
    return jdbcTemplate.queryForObject("SELECT count(*) FROM " + TABLE_NAME, Long.class);
  }

  Override
  public void deleteAll() {
    String deleteAll = "DELETE FROM " + TABLE_NAME;
    jdbcTemplate.update(deleteAll);
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> strings) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(Quote entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> entities) {
    throw new UnsupportedOperationException("Not implemented");
  }

}
