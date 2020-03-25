package Model;


import Controller.FolioTracker;
import View.FolioPanel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
  private String name;
  public static HashMap<String,StockHolding> map=new HashMap<>();

  public Portfolio(String name){
    this.name=name;
  }



  public static void addStock(String [] stockEntry){


    String ticker = String.valueOf(stockEntry[0]);
    String name = String.valueOf(stockEntry[0]);
    int shares = Integer.parseInt(stockEntry[1]);
    double pps = Double.parseDouble(stockEntry[2]);

    System.out.println("I get here");
    ArrayList<StockHolding> list=new ArrayList<>();

     list.add(new StockHolding(ticker, name, shares, pps));

     for(StockHolding s : list) {
      map.put(s.getTicker(), s);
       System.out.println(map);
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

  public static List<StockHolding> getStockList(){
    return new ArrayList<>(map.values());
  }

  public String getName(){
    return name;
  }

}


