import Model.StockHolding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

public class StockHoldingTest {
  private StockHolding testStock1;
  private StockHolding testStock2;
  @Before
  public void SetUp() {
     testStock1 = new StockHolding("test1", "test1", 1, 1);
     testStock2 = new StockHolding("test2", "test2", 1, 1);
  }
  @Test
  public void checkEquals(){
    Assert.assertNotEquals(testStock1, testStock2);
  }

  @Test
  public void checkGrowth(){
    testStock1.setPricePerShare(2);
    int growth = (int)testStock1.growSinceLastTime();
    Assert.assertEquals(1,growth);
  }

  @Test
  public void CheckSetShares(){
    testStock1.setShares(2);
    Assert.assertEquals(testStock1.getShares(),2);
  }

  @Test
  public void CheckLatestUpdate() throws InterruptedException {
    Timestamp time=testStock1.getLatestUpdate();
    testStock1.setPricePerShare(1);
    Timestamp time2=testStock1.getLatestUpdate();
    Assert.assertEquals(time,time2);
    Thread.sleep(10);
    testStock1.setPricePerShare(2);
    Timestamp time3=testStock1.getLatestUpdate();
    Assert.assertNotEquals(time2,time3);

  }

  @Test
  public void CheckLastValue(){
    Assert.assertEquals(testStock1.getPricePerShare(),testStock1.getLastValue(),0);
    testStock1.setPricePerShare(2);
    Assert.assertNotEquals(testStock1.getPricePerShare(),testStock1.getLastValue(),0);
  }



}
