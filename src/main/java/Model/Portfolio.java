package Model;



import java.util.ArrayList;

import java.util.List;


public class Portfolio {
  private String name;
  //private HashMap<String,StockHolding> map=new HashMap<>();
  private List<StockHolding> list=new ArrayList<StockHolding>();

  public Portfolio(String name){
    this.name=name;
  }



  public void addStock(String [] stockEntry){


    String ticker = String.valueOf(stockEntry[0]);
    String name = String.valueOf(stockEntry[0]);
    int shares = Integer.parseInt(stockEntry[1]);
    double pps = Double.parseDouble(stockEntry[2]);

    System.out.println("I get here");
    StockHolding stock=new StockHolding(ticker, name, shares, pps);
    if(!list.contains(stock)) {
      System.out.println("Doesn't containt?!");
      list.add(stock);
    }

    for(StockHolding s:list){
      System.out.println(s);
    }
    System.out.println("AND: "+stock);


  }

  public void removeStock(String ticker){
    //TODO find stock in list by ticker and remove

  }

  public void refreshStocks(){
   //TODO I would add some sort of connection to server here and check if stock was changed, if yes -> update our list and fire a tableDataChange in tableModel
  }

  public double getTotalValue() {
   //TODO calculate and return the total number of all total values from all the stocks in a list
    return 0;
  }

  public List<StockHolding> getStockList(){
    return list;
  }

  public String getName(){
    return name;
  }

}


