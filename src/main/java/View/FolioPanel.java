package View;

import Model.FolioPanelTableModel;
import Model.StockHolding;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class FolioPanel extends JPanel {

  private String name;
  private JPanel mainCard = new JPanel(new BorderLayout(0, 40));
  private JPanel buttons=new JPanel();
  private JTable table;
  private JButton removeStock = new JButton("Remove Stock");
  private JButton addWatch=new JButton("Add Stock");
  private JButton refresh=new JButton("Update");
  public FolioPanelTableModel tableModel;
  final AtomicInteger selectedRow=new AtomicInteger(-1);
  final AtomicInteger selectedCol=new AtomicInteger(-1);

  public FolioPanel(String name,List<StockHolding> data){
    this.name=name;
    tableModel=new FolioPanelTableModel(data);

    table=new JTable(tableModel);

    /*
     This two action listeners will make sure selected row stays selected on refresh.
     */
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e)
      {
        selectedRow.set(table.getSelectedRow());
        selectedCol.set(table.getSelectedColumn());
      }
    });
    table.getModel().addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e)
      {
        TableCellEditor editor=table.getCellEditor();
        if (editor!=null) editor.cancelCellEditing();

        final int row=selectedRow.get();
        final int col=selectedCol.get();
        if (row<0||col<0) return;

        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            table.changeSelection(row,col, false, false);
          }
        });
      }
    });
    table.setAutoCreateRowSorter(true);

    table.setRowHeight(30);
    table.setPreferredScrollableViewportSize(new Dimension(900, 400));
   // table.setDefaultRenderer(StringIcon.class, new StringIconRenderer());
    //Create the scroll pane and add the table to it.
    JScrollPane scrollPane = new JScrollPane(table);

    //Add the scroll pane to this panel.

    addWatch.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    refresh.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    buttons.add(removeStock);
    buttons.add(addWatch);
    buttons.add(refresh);
    buttons.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    mainCard.add(scrollPane,BorderLayout.NORTH);
    mainCard.add(buttons, BorderLayout.SOUTH);
    add(mainCard);

  }

  public void addRemoveStockListener(ActionListener actionListener){removeStock.addActionListener(actionListener);}

  public void addAddWatchListener(ActionListener actionListener){
    addWatch.addActionListener(actionListener);
  }

  public void addRefreshListener(ActionListener actionListener){
    refresh.addActionListener(actionListener);
  }

  public String getName(){
    return this.name;
  }

  public JTable getTable(){return this.table;}




}
