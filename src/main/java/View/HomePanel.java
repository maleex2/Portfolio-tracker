package View;

import Model.Portfolio;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class HomePanel extends JTabbedPane {
  HashMap<String, FolioPanel> map=new HashMap<>();

  public HomePanel(MouseListener mouseListener){
    this.addMouseListener(mouseListener);
  }



  public void createPanel(Portfolio p,ActionListener addWatchActionListener, ActionListener refreshActionListener) {
    FolioPanel fp=new FolioPanel(p.getStockList());
    fp.addAddWatchListener(addWatchActionListener);
    fp.addRefreshListener(refreshActionListener);
    map.put(p.getName(),fp);
    add(p.getName(),fp);
    }
  }




