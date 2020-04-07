package Model;

import java.sql.Timestamp;

public class StockHolding {

  private String ticker;
  private String name;
  private int shares;
  private double pricePerShare;
  private double totalValue;
  private Timestamp latestUpdate;
  private double lastValue;

  public StockHolding(String ticker, String name, int shares, double pricePerShare) {
    setTicker(ticker);
    setName(name);
    setShares(shares);
    setPricePerShareInitial(pricePerShare);
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

  public void setPricePerShareInitial(double pricePerShare){
    this.pricePerShare = pricePerShare;
    setLastValue(this.pricePerShare);
    setTotalValue();
  }

  public void setPricePerShare(double pricePerShare) {
    if(pricePerShare==getPricePerShare()) {
      //set correct time
      if (new Timestamp(System.currentTimeMillis()).getTime() - latestUpdate.getTime() > (36*Math.pow(10,5))) {
        setLastValue(this.pricePerShare);
        this.pricePerShare = pricePerShare;
        setTotalValue();
        setLatestUpdate(new Timestamp(System.currentTimeMillis()));
      }
    }else{
      setLastValue(this.pricePerShare);
      this.pricePerShare = pricePerShare;
      setTotalValue();
      setLatestUpdate(new Timestamp(System.currentTimeMillis()));
    }
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

  /*
   private String ticker;
  private String name;
  private int shares;
  private double pricePerShare=0;
  private double totalValue;
  private Timestamp latestUpdate;
  private double lastValue;
   */


  @Override
  public boolean equals(Object o){
    StockHolding stock=(StockHolding)o;
    if(this.ticker.equals(stock.ticker)){
          return this.pricePerShare == stock.pricePerShare;
      }
    return false;
  }
}

