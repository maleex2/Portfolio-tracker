import Model.Account;
import Model.FolioPanelTableModel;
import Model.Portfolio;
import Model.StockHolding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.spy;

public class AccountTest {
  private Account account1;
  private Portfolio portfolioInstance1;
  private Portfolio portfolioInstance2;
  private StockHolding testStock1;
  private  StockHolding testStock2;
  private StockHolding testStock3;
  private  StockHolding testStock4;
  private StockHolding testStock5;

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  FolioPanelTableModel tableModel1;
  FolioPanelTableModel tableModel2;
  @Before
  public void setUp() {
    account1=new Account("test1");

    portfolioInstance1 = new Portfolio("Testing1");
    testStock1 = new StockHolding("test1", "test1", 1, 1);
    testStock2 = new StockHolding("test2", "test2", 1, 1);
    portfolioInstance1.addStock(testStock1);
    portfolioInstance1.addStock(testStock2);

    portfolioInstance2 = new Portfolio("Testing2");
    testStock3 = new StockHolding("test3", "test3", 1, 1);
    testStock4 = new StockHolding("test4", "test4", 1, 1);
    portfolioInstance2.addStock(testStock3);
    portfolioInstance2.addStock(testStock4);

    tableModel1=spy(new FolioPanelTableModel(portfolioInstance1.getStockList()));
    tableModel2=spy(new FolioPanelTableModel(portfolioInstance2.getStockList()));
  }

  @Test
  public void CheckAddPortfolio(){
    account1.addPortfolio(portfolioInstance1);
    Assert.assertTrue(account1.getPortfolioList().contains(portfolioInstance1));
    account1.addPortfolio(portfolioInstance1);
    Assert.assertEquals(1,account1.getPortfolioList().size());
    account1.addPortfolio(portfolioInstance2);
    Assert.assertEquals(2,account1.getPortfolioList().size());
  }


  @Test
  public void CheckRemovePortfolio(){
    account1.addPortfolio(portfolioInstance1);
    account1.removePortfolio(portfolioInstance1);
    Assert.assertEquals(0,account1.getPortfolioList().size());
  }
}
