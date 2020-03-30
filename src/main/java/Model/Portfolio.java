package Model;



//import sun.invoke.empty.Empty;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;

import java.util.List;


public class Portfolio {
  private String name;
  //private HashMap<String,StockHolding> map=new HashMap<>();
  private List<StockHolding> list=new ArrayList<StockHolding>();
  public FolioPanelTableModel tableModel;

  public Portfolio(String name){
    this.name=name;
  }



  public void addStock(String [] stockEntry){

    if( String.valueOf(stockEntry[0]).equals("")){
      System.out.println("Empty ticker");
      //display error alert
    }else{
      String ticker = String.valueOf(stockEntry[0]);
      String name = String.valueOf(stockEntry[0]);
      int shares = Integer.parseInt(stockEntry[1]);
      double pps = Double.parseDouble(stockEntry[2]);

      System.out.println("I get here");
      StockHolding stock=new StockHolding(ticker, name, shares, pps);
      if(!list.contains(stock)) {
        System.out.println("Doesn't contain it?!");
        list.add(stock);
      }

      for(StockHolding s:list){
        System.out.println(s);
      }
      System.out.println("AND: "+stock);
    }




  }

  public void removeStock(String tickerName){
    //TODO find stock in list by ticker and remove
    System.out.println("remove is called");
    int removed = 0;
    List<StockHolding> toRemove = new ArrayList<StockHolding>();

    for(StockHolding stock : list){
      if(stock.getName().equals(tickerName)){
       toRemove.add(stock);
        removed = 1;
      }
    }

    if(!toRemove.isEmpty()){
      list.remove(toRemove.get(0));
    }


    if(removed == 0){
      JOptionPane.showMessageDialog(null, "There is no ticker with that name, nothing has been removed.");
    }

  }

  public  void refreshStocks() throws NoSuchTickerException, WebsiteDataException {
    System.out.println("refresh is called!");
    int temp = 0;
    for(StockHolding stock : list){
      double originalPPS = stock.getPricePerShare();
      double newPPS = 0;
      System.out.println("Getting value for "+stock.getTicker());
      newPPS = Double.parseDouble(StrathQuoteServer.getLastValue(stock.getTicker()));


      if(newPPS != originalPPS){
        System.out.println("Price changed");
        stock.setPricePerShare(newPPS);
        tableModel.fireTableDataChanged();

      }

      temp++;
    }


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


