package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);
  abstract public JdbcTemplate getJdbcTemplate();
  abstract public SimpleJdbcInsert getSimpleJdbcInsert();
  abstract public String getTableName();
  abstract public String getIdColumnName();
  abstract Class<T> getEntityClass();

  @Override
  public <S extends T> S save(S entity) {
    if (existsById(entity.getId())) {
      if (updateOne(entity) != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      addOne(entity);
    }
    return entity;
  }

  private <S extends T> void addOne(S entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

    Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId(newId.intValue());
  }

  abstract public int updateOne(T entity);

  @Override
  public Optional<T> findById(Integer id) {
    Optional<T> entity = Optional.empty();
    String selectSql = "SELECT * FROM" + getTableName() + "WHERE" + getIdColumnName() + "=?";

    try {
      entity = Optional.ofNullable((T) getJdbcTemplate()
          .queryForObject(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()), id));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find trader id:" +id, e);
    }
    return entity;
  }

  @Override
  public boolean existsById(Integer id) {
    return findById(id).isPresent();
  }

  @Override
  public List<T> findAll() {
    String sqlQuery = "SELECT * FROM " + getTableName();
    List<T> entities = getJdbcTemplate().query(sqlQuery, BeanPropertyRowMapper.newInstance(getEntityClass()));
    return entities;
  }

  @Override
  public List<T> findAllById(Iterable<Integer> ids) {
    String sqlQuery = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    List<T> entities = getJdbcTemplate().query(sqlQuery, BeanPropertyRowMapper.newInstance(getEntityClass()));
    return entities;
  }

  @Override
  public void deleteById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("Invalid ID");
    }
    String sqlQuery = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
    getJdbcTemplate().update(sqlQuery, id);
  }

  @Override
  public long count() {
    String sqlQuery = "SELECT count(*) FROM " + getTableName();
    return getJdbcTemplate().queryForObject(sqlQuery, Long.class);
  }

  @Override
  public void deleteAll() {
    String sqlQuery = "DELETE FROM " + getTableName();
    getJdbcTemplate().update(sqlQuery);
  }
  public <S extends T> List<S> saveAll(Iterable<S> iterable) {
    List<S> result = new ArrayList<>();
    for (S quote : iterable) {
      result.add(this.save(quote));
    }
    return result;
  }
}
