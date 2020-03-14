package Model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
  private String name;
  private HashMap<String,StockHolding> map=new HashMap<>();

  public Portfolio(String name){
    this.name=name;
  }



  public void addStock(String ticker){
    //TODO connect to server, and get the stock
    /***
     * Code for test! Remove later!
     ***/
    ArrayList<StockHolding> list=new ArrayList<>();
    for(int i=0;i<6;i++) {
      list.add(new StockHolding(""+i,""+i,i,i));
    }
    for(StockHolding s : list) {
      map.put(s.getTicker(), s);
    }

  }

  public void removeStock(String ticker){
    map.remove(ticker);
  }

  public void refreshStocks(){

  }

  public double getTotalValue() {
    double total = 0;
    for (StockHolding stock : map.values()) {
      total += stock.getTotalValue();
    }
    return total;
  }

  public List<StockHolding> getStockList(){
    return new ArrayList<>(map.values());
  }

  public String getName(){
    return name;
  }

}


