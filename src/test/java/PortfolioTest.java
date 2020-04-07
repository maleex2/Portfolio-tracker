import Model.*;


import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;



import static org.mockito.Mockito.spy;

public class PortfolioTest {
  private Portfolio portfolioInstance;
  private StockHolding testStock1;
  private StockHolding testStock2;
  private StockHolding testStock3;
  private StockHolding testStock4;
  private StockHolding testStock5;


  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  FolioPanelTableModel tableModel;

  @Before
  public void setUp(){
    portfolioInstance = new Portfolio("Testing");
    testStock1 = new StockHolding("test1", "test1", 1, 1);
    testStock2 = new StockHolding("test2", "test2", 1, 1);
    testStock3 = new StockHolding("test3", "test3", 1, 1);
    testStock4 = new StockHolding("test4", "test4", 1, 1);
    testStock5 =  new StockHolding("dow", "test4", 1, 1);
    tableModel=spy(new FolioPanelTableModel(portfolioInstance.getStockList()));
  }


  @Test
  public void checkAdd() {

    portfolioInstance.addStock(testStock1);
    portfolioInstance.addStock(testStock2);

    int newLength = portfolioInstance.getStockList().size();

    Assert.assertEquals(2, newLength);

  }

  @Test
  public void checkRemove() {
    portfolioInstance.addStock(testStock1);
    portfolioInstance.addStock(testStock2);
    portfolioInstance.addStock(testStock3);
    portfolioInstance.addStock(testStock4);

    portfolioInstance.removeStock("test1");
    portfolioInstance.removeStock("test2");

    int length = portfolioInstance.getStockList().size();
    Assert.assertEquals(2, length);

  }

  @Test
  public void checkTotalValue() {
    portfolioInstance.addStock(testStock1);
    portfolioInstance.addStock(testStock2);
    portfolioInstance.addStock(testStock3);
    portfolioInstance.addStock(testStock4);


    double totalValue = portfolioInstance.getTotalValue();
    int valueToInt = (int) totalValue;
    Assert.assertEquals(4, valueToInt);


  }

  @Test
  public void checkRefresh() throws WebsiteDataException, NoSuchTickerException, WebsiteConnectionException {
    portfolioInstance.addTableModel(tableModel);
    portfolioInstance.addStock(testStock5);

    double storedValue = testStock5.getPricePerShare();
    System.out.println();
    portfolioInstance.refreshStocks();

    double newValue =testStock5.getPricePerShare();
    Assert.assertTrue(storedValue != newValue);

  }

  @Test
  public void CheckEquals(){
    Portfolio portfolioInstance2 = new Portfolio("Testing2");
    Assert.assertNotEquals(portfolioInstance, portfolioInstance2);
  }

}