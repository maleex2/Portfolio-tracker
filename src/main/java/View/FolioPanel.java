package View;

import Model.FolioPanelTableModel;
import Model.StockHolding;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class FolioPanel extends JPanel {

  private String name;
  private JPanel mainCard = new JPanel(new BorderLayout(0, 40));
  private JPanel topControl=new JPanel();
  private JLabel loginLabel = new JLabel("Ticker:",JLabel.TRAILING);
  private JTextField loginField = new JTextField(5);
  private JLabel nameLabel = new JLabel("Name:",JLabel.TRAILING);
  private JTextField nameField = new JTextField(10);
  private JLabel amountLabel = new JLabel("Amount:",JLabel.TRAILING);
  private SpinnerModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
  private JSpinner amountSpinner = new JSpinner(model);


  private JButton saveButton=new JButton("Add");
  private JButton clearButton=new JButton("Clear");
  private JPanel buttons=new JPanel();
  private JTable table;
  private JButton removeStock = new JButton("Remove Stock");
  private JButton refresh=new JButton("Get Latest PPS");
  private JLabel totalLabel=new JLabel("Total Folio Value:",JLabel.TRAILING);
  private JLabel totalValue=new JLabel("");
  private FolioPanelTableModel tableModel;
  final AtomicInteger selectedRow=new AtomicInteger(-1);
  final AtomicInteger selectedCol=new AtomicInteger(-1);
  public FolioPanel(String name,List<StockHolding> data, CellEditorListener cellEditorListener){
    Component mySpinnerEditor = amountSpinner.getEditor();
    JFormattedTextField jftf = ((JSpinner.DefaultEditor) mySpinnerEditor).getTextField();
    jftf.setColumns(3);
    this.name=name;
    tableModel=new FolioPanelTableModel(data);


    table=new JTable(tableModel);
    table.getDefaultEditor(Integer.class).addCellEditorListener(cellEditorListener);

    /*
     This two action listeners will make sure selected row stays selected on refresh.
     */
    table.getSelectionModel().addListSelectionListener(e -> {
      selectedRow.set(table.getSelectedRow());
      selectedCol.set(table.getSelectedColumn());
    });
    table.getModel().addTableModelListener(e -> {
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
    });
    table.setAutoCreateRowSorter(true);

    table.setRowHeight(30);
    table.setPreferredScrollableViewportSize(new Dimension(900, 400));
    JScrollPane scrollPane = new JScrollPane(table);


    refresh.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    totalLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    totalValue.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    topControl.add(loginLabel);
    topControl.add(loginField);
    topControl.add(nameLabel);
    topControl.add(nameField);
    topControl.add(amountLabel);
    topControl.add(amountSpinner);
    topControl.add(clearButton);
    topControl.add(saveButton);
    buttons.add(removeStock);
    buttons.add(refresh);
    buttons.add(totalLabel);
    buttons.add(totalValue);
    buttons.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    mainCard.add(scrollPane,BorderLayout.CENTER);
    mainCard.add(topControl, BorderLayout.NORTH);
    mainCard.add(buttons, BorderLayout.SOUTH);
    add(mainCard);

  }

  public void addRemoveStockListener(ActionListener actionListener){removeStock.addActionListener(actionListener);}


  public void addRefreshListener(ActionListener actionListener){
    refresh.addActionListener(actionListener);
  }

  public String getName(){
    return this.name;
  }

  public JTable getTable(){return this.table;}

  public void addSaveActionListener(ActionListener actionListener){
    saveButton.addActionListener(actionListener);
  }

  public void addClearActionListener(ActionListener actionListener){
    clearButton.addActionListener(actionListener);
  }

  public String getEnteredTicker(){
    return loginField.getText();
  }
  public String getEnteredName(){
    return nameField.getText();
  }
  public int getEnteredAmount(){
    return (int)amountSpinner.getValue();
  }

  public FolioPanelTableModel getTableModel(){
    return this.tableModel;
  }
  public void clear(){
    loginField.setText("");
    amountSpinner.setValue(1);
    nameField.setText("");

  }

  public void setTotalValue(Double total){
    this.totalValue.setText(String.format("%.2f", total));
  }



}
