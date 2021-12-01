package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

public class TraderDaoTest {

  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = {TestConfig.class})
  @Sql({"classpath:schema.sql"})
  public class TraderDaoIntTest {
    @Autowired
    private TraderDao traderDao;
    private Trader trader;

    @Before
    public void setUp() throws Exception {
      trader = new Trader();
      trader.setId(1);
      trader.setFirstName("Bill");
      trader.setLastName("Todd");
      Calendar calendar = Calendar.getInstance();
      calendar.set(1974,03,026);
      trader.setDob(calendar.getTime());
      trader.setCountry("Egypt");
      trader.setEmail("billtodd@egypt.com");
      traderDao.save(trader);
    }

    @After
    public void tearDown() {
      traderDao.deleteById(1);
    }

    @Test
    public void count(){
      assertEquals(1,traderDao.count());
    }

    @Test
    public void findAll(){
      assertEquals(1,traderDao.findAll().size());
    }
  }
}