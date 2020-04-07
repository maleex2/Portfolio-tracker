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



  public void addStock(StockHolding stock) {
    if (stock.getName().equals("")) {
      //display error alert
    } else {

      if (!list.contains(stock)) {
        list.add(stock);
      } else {
        list.remove(stock);
        list.add(stock);
      }
    }
  }


  public void removeStock(String tickerName){
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

  public  boolean refreshStocks() throws NoSuchTickerException, WebsiteDataException, WebsiteConnectionException {
    boolean changed=false;
    for(StockHolding stock : list){
      double newPPS = 0;
      newPPS = Double.parseDouble(StrathQuoteServer.getLastValue(stock.getTicker()));
      stock.setPricePerShare(newPPS);
      changed=true;
      tableModel.fireTableDataChanged();

    }
    return changed;
  }

  public double getTotalValue() {
    double totalValue = 0;
    for (StockHolding stock : list) {
      totalValue += stock.getTotalValue();
    }
    return totalValue;

  }

  public List<StockHolding> getStockList(){
    return list;
  }

  public String getName(){
    return name;
  }

  public void addTableModel(FolioPanelTableModel tableModel){
    this.tableModel=tableModel;
  }

  @Override
  public boolean equals(Object o){
    Portfolio portfolio=(Portfolio)o;
    return this.name.toUpperCase().equals(portfolio.name.toUpperCase());
  }
}


