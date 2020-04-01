package Model;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FolioPanelTableModel extends AbstractTableModel {

  private static String[] COLUMN_NAMES={"Ticker","Name","Shares","PPS","Change","Total"};
  private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
  private static String greenName="greenT.png";
  private static File GREEN = new File(classLoader.getResource(greenName).getFile());
  private static String redName="redT.png";
  private static File RED = new File(classLoader.getResource(redName).getFile());
  private static String neutralName="neutral.png";
  private static File NEUTRAL = new File(classLoader.getResource(neutralName).getFile());

  private List<StockHolding> stockList;


  public FolioPanelTableModel(List<StockHolding> list){
    super();
    this.stockList=list;
  }

  @Override
  public Class<?> getColumnClass(int column) {
    switch (column) {
      case 0:
      case 1:
        return String.class;
      case 2:
        return Integer.class;
      case 4:
        return ImageIcon.class;
      default:
        return Double.class;
    }
  }




  @Override
  public String getColumnName(int column) {
    return COLUMN_NAMES[column];
  }
  @Override
  public int getRowCount() {
    return stockList.size();
  }

  @Override
  public int getColumnCount(){
    return COLUMN_NAMES.length;
  }

  @Override
  //Columns {"Ticket","Name","Shares","PPS","Total"};
  public Object getValueAt(int rowIndex, int columnIndex) {


    Object o;
    switch(columnIndex){
      case 0:

        o=stockList.get(rowIndex).getTicker();
        break;
      case 1:
        o=stockList.get(rowIndex).getName();
        break;
      case 2:
        o=stockList.get(rowIndex).getShares();
        break;
      case 3:
        o=stockList.get(rowIndex).getPricePerShare();
        break;
      case 4:
        if( stockList.get(rowIndex).growSinceLastTime() >0){
          o=new ImageIcon(GREEN.getAbsolutePath());
        }else if(stockList.get(rowIndex).growSinceLastTime() <0){
          o=new ImageIcon(RED.getAbsolutePath());
        }else{
          o=new ImageIcon(NEUTRAL.getAbsolutePath());
        }
        break;
      default:
        o=stockList.get(rowIndex).getTotalValue();
        break;
    }

    return o;

  }

  @Override
  public void setValueAt(Object value, int row, int col){
    switch(col){
      case 0:
        stockList.get(row).setTicker((String)value);

        break;
      case 1:
        stockList.get(row).setName((String)value);

        break;
      case 2:
        stockList.get(row).setShares((int)value);
        break;
      case 3:
        stockList.get(row).setPricePerShare((double)value);
        break;
    }
    fireTableDataChanged();
  }

  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    if(columnIndex==1 ) {
      return true;
    }else{
      return false;
    }
  }

  public void fireTableChangeOnAddRow(){
    this.fireTableRowsInserted(stockList.size()-1,stockList.size()-1);
  }


  public List<StockHolding> getTableModelStockList(){
    return stockList;
  }



}