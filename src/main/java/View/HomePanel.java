package View;

import Model.Portfolio;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class HomePanel extends JTabbedPane {


  public HomePanel(MouseListener mouseListener){
    this.addMouseListener(mouseListener);
  }



  public void createPanel(Portfolio p,ActionListener addWatchActionListener, ActionListener refreshActionListener) {
    FolioPanel fp=new FolioPanel(p.getName(),p.getStockList());
    fp.addAddWatchListener(addWatchActionListener);
    fp.addRefreshListener(refreshActionListener);
    add(p.getName(),fp);

    }
  }




