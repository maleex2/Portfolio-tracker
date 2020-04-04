package View;

import Model.Portfolio;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class HomePanel extends JTabbedPane {


  public HomePanel(MouseListener mouseListener) {
    this.addMouseListener(mouseListener);
  }


  public FolioPanel createPanel(Portfolio p, ActionListener removeStockActionListener, ActionListener addSaveActionListener, ActionListener addClearActionListener, ActionListener refreshActionListener, CellEditorListener cellEditorListener) {
    FolioPanel fp = new FolioPanel(p.getName(), p.getStockList(), cellEditorListener);
    fp.addRemoveStockListener(removeStockActionListener);
    fp.addSaveActionListener(addSaveActionListener);
    fp.addClearActionListener(addClearActionListener);
    fp.addRefreshListener(refreshActionListener);
    add(p.getName(), fp);
    return fp;
  }

  public void removeAll() {
    int pointer = getTabCount() - 1;
    while (pointer > -1) {
      System.out.println("removing" + this.getComponentAt(pointer));
      this.remove(this.getComponentAt(pointer));
      pointer--;
    }
  }
}




