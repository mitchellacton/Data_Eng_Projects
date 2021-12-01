package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {
  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      PositionDao positionDao, SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  public TraderAccountView createTraderAndAccount(Trader trader) {
    Account account = new Account();
    account.setTraderId(trader.getId());
    account.setAmount(0.0);
    return new TraderAccountView(traderDao.save(trader), accountDao.save(account));
  }

  public void deleteTraderById(Integer traderId) {
    Account account = accountDao.findById(traderId).get();
    if (account.getAmount().equals(0.0) && !positionDao.existsById(traderId)) {
      securityOrderDao.deleteById(traderId);
      accountDao.deleteById(traderId);
      traderDao.deleteById(traderId);
    } else {
      throw new RuntimeException("Unable to delete Trader");
    }
  }

  public Account deposit(Integer traderId, Double fund) {
    if (fund <= 0) {
      throw new IllegalArgumentException("Can't deposit zero funds");
    }
    Account account = accountDao.findById(traderId).get();
    account.setAmount(account.getAmount() + fund);
    return account;
  }

  public Account withdraw(Integer traderId, Double fund) {
    Account account = accountDao.findById(traderId).get();
    if (account.getAmount()<fund) {
      throw new IllegalArgumentException("Insufficient funds");
    }
    account.setAmount(account.getAmount() - fund);
    return account;

  }

}
