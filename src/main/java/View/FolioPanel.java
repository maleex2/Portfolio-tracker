package View;

import Model.FolioPanelTableModel;
import Model.StockHolding;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;


public class FolioPanel extends JPanel {

  private JPanel mainCard = new JPanel(new BorderLayout(0, 40));
  JPanel buttons=new JPanel();
  JTable table;
  JButton addWatch=new JButton("Add Watch");
  JButton refresh=new JButton("Update");

  public FolioPanel(List<StockHolding> data){
    table=new JTable(new FolioPanelTableModel(data));

    table.setRowHeight(30);
    table.setPreferredScrollableViewportSize(new Dimension(900, 400));
   // table.setDefaultRenderer(StringIcon.class, new StringIconRenderer());
    //Create the scroll pane and add the table to it.
    JScrollPane scrollPane = new JScrollPane(table);

    //Add the scroll pane to this panel.

    addWatch.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    refresh.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    buttons.add(addWatch);
    buttons.add(refresh);
    buttons.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    mainCard.add(scrollPane,BorderLayout.NORTH);
    mainCard.add(buttons, BorderLayout.SOUTH);
    add(mainCard);

  }

  public void addAddWatchListener(ActionListener actionListener){
    addWatch.addActionListener(actionListener);
  }

  public void addRefreshListener(ActionListener actionListener){
    refresh.addActionListener(actionListener);
  }





}