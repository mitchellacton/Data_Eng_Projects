package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.util.Calendar;
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
public class TraderAccountServiceIntTest {
  @Autowired
  private TraderDao traderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderAccountService traderAccountService;


  @Before
  public void setUp(){
    Trader trader = new Trader();
    trader.setId(1);
    trader.setFirstName("Bill");
    trader.setLastName("Todd");
    Calendar calendar = Calendar.getInstance();
    calendar.set(1973,03,26);
    trader.setDob(calendar.getTime());
    trader.setCountry("Egypt");
    trader.setEmail("billtodd@egypt.com");
    traderDao.save(trader);

    Account account = new Account();
    account.setId(1); //same with id in Trader table
    account.setTraderId(1);
    account.setAmount(0.0);
    accountDao.save(account);
  }

  @After
  public void tearDown() {
    accountDao.deleteAll();
    traderDao.deleteAll();
  }

  @Test
  public void createTraderAndAccount() {
    TraderAccountView traderAccountView = traderAccountService.createTraderAndAccount(trader);
    assertEquals(2, accountDao.count());
  }

  @Test
  public void deleteTraderById() {
    traderAccountService.deleteTraderById(1);
    assertEquals(0, traderDao.count());
  }

  @Test
  public void deposit() {
    traderAccountService.deposit(1, 100.00);
    assertEquals(new Double(1000), accountDao.findById(1).get().getAmount());
  }

  @Test
  public void withdraw() {
    traderAccountService.deposit(1, 100.0);
    traderAccountService.withdraw(1, 99.0);
    assertEquals(new Double(1.0), accountDao.findById(1).get().getAmount());
  }
}