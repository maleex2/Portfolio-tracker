package Model;

import java.sql.Timestamp;

public class StockHolding {

  private String ticker;
  private String name;
  private int shares;
  private double pricePerShare=0;
  private double totalValue;
  private Timestamp latestUpdate;
  private double lastValue;

  public StockHolding(String ticker, String name, int shares, double pricePerShare) {
    setTicker(ticker);
    setName(name);
    setShares(shares);
    setPricePerShare(pricePerShare);
    setLatestUpdate(new Timestamp(System.currentTimeMillis()));

  }

  public String getTicker(){
    return ticker;
  }
  public void setTicker(String ticker){
    this.ticker=ticker;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getShares() {
    return shares;
  }

  public void setShares(int shares) {
    this.shares = shares;
    setTotalValue();
  }

  public double getPricePerShare() {
    return pricePerShare;
  }

  public void setPricePerShare(double pricePerShare) {
    setLastValue(this.pricePerShare);
    this.pricePerShare = pricePerShare;
    setTotalValue();
  }

  public double getTotalValue() {
    return totalValue;
  }

  public void setTotalValue() {
    this.totalValue = getShares()*getPricePerShare();
  }

  public Timestamp getLatestUpdate() {
    return latestUpdate;
  }

  public void setLatestUpdate(Timestamp latestUpdate) {
    this.latestUpdate = latestUpdate;
  }
  public void setLastValue(double pricePerShare){
    lastValue=pricePerShare;
  }

  public double growSinceLastTime(){
    return pricePerShare-lastValue;
  }
  //TODO remove!
  /***
   * Utility stuff
   */
  @Override
   public String toString(){
     return getTicker()+" "+getName()+" "+getShares()+" "+getPricePerShare()+" "+getTotalValue();
  }

}
