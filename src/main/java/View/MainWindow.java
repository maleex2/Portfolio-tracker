package View;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
  private JMenuBar mb = new JMenuBar();
  private JMenu account, portfolio, more;
  private JMenuItem save, createNew, delete,exit, help,about;

  /**********************************************************************************
   * Sets up the main window which will contain the layout which displays each panel
   **********************************************************************************/

  public MainWindow() {
    super("FolioTracker");
    JFrame instance=this;
    //Account
    account = new JMenu("Account");
    exit = new JMenuItem("Logout");
    account.add(exit);

    portfolio= new JMenu("Portfolio");
    save = new JMenuItem("Save...");
    createNew = new JMenuItem("Create new...");
    delete= new JMenuItem("Delete...");
    portfolio.add(save);
    portfolio.add(createNew);
    portfolio.add(delete);

    more = new JMenu("More..");
    help = new JMenuItem("Help");
    about = new JMenuItem("About");
    more.add(help);
    more.add(about);



    mb.add(account);
    mb.add(portfolio);
    mb.add(more);
    mb.setVisible(false);
    setJMenuBar(mb);
    setSize(1080, 720);
    setResizable(true);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }



  public void ShowMenu() {
    mb.setVisible(true);
  }

  public void HideMenu() {
    mb.setVisible(false);
  }
  public void addSavePortfolioListener(ActionListener actionListener){
    save.addActionListener(actionListener);
  }
  public void addCreateNewPortfolioListener(ActionListener actionListener){
    createNew.addActionListener(actionListener);

  }
  public void addRemovePortfolioListener(ActionListener actionListener){
    delete.addActionListener(actionListener);
  }


}






