package Controller;

import Model.*;
import View.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

public class FolioTracker {
  // Create instances of the model and other controllers
  private File file;
  //private CustomFileParser parser;
  //private Player player;

  // Create instances for each of GUI panels
  private MainWindow view;
  private AddWatchWindow addWatchWindow;
  private JPanel card;
  private CardLayout cardLayout;
  private WelcomePanel welcomePanel;
  private LoginPanel loginPanel;
  private RegisterPanel registerPanel;
  public HomePanel homePanel;



  public HashMap<String, Portfolio> portfolioMap=new HashMap<>();

  public FolioPanel currentSelected = null;





  //private HomePanel menu;
  //private GamePanel game;
  //private GameChoice gameChoice;

  /*************************************
   * Construct display and set up login
   *************************************/

  public FolioTracker(){
    this.view=new MainWindow();
    view.addNewFolioListener(new FolioListener());
    this.cardLayout=new CardLayout();
    this.card = new JPanel(cardLayout);

    this.welcomePanel=new WelcomePanel();
    this.welcomePanel.addLoginListener(new WelcomeLoginListener());
    this.welcomePanel.addRegisterListener(new WelcomeRegisterListener());

    this.loginPanel = new LoginPanel(); // The panel for the login screen
    this.loginPanel.addActionListener(new LoginListener());
    this.loginPanel.addGoToWelcomePanelListener(new GoToWelcomePanelListener());

    this.registerPanel= new RegisterPanel();
    this.registerPanel.addActionListener(new RegisterListener());
    this.registerPanel.addGoToWelcomePanelListener(new GoToWelcomePanelListener());

    this.homePanel=new HomePanel( new TabMouseListener());

    //TODO add actionlisteners

    this.card.add("welcomePanel", welcomePanel);
    this.card.add("loginPanel", loginPanel);
    this.card.add("registerPanel", registerPanel);
    this.card.add("homePanel", homePanel);

    this.view.add(card);
    this.cardLayout.show(card, "welcomePanel");

    this.view.setVisible(true);


    this.addWatchWindow=new AddWatchWindow();
    this.addWatchWindow.addSaveActionListener(new AddStockListener());
    this.addWatchWindow.addClearActionListener(new ClearListener());





    /**
     * Test Code
     */
    Portfolio port1=new Portfolio("First");
    Portfolio port2=new Portfolio("Second");

    portfolioMap.put(port1.getName(),port1);
    portfolioMap.put(port2.getName(),port2);


    for(Portfolio p:portfolioMap.values()){
      homePanel.createPanel(p,new RemoveStocksListener() ,new AddWatchListener(),new RefreshListener());

    }


    currentSelected=(FolioPanel) homePanel.getSelectedComponent();


  }


  /*****************************************************************
   * Displaying panels
   *
   * Used for switching between windows, setting up what needs to be
   *****************************************************************/
  public void displayHomePanel(){
    cardLayout.show(card,"homePanel");
  }

  public void displayLogin(){
    cardLayout.show(card,"loginPanel");
  }

  public void displayRegister(){
    cardLayout.show(card,"registerPanel");
  }
  public void displayWelcome(){
    cardLayout.show(card,"welcomePanel");
  }



  /**************************************************************
   * ACTION LISTENERS
   *
   * Passed to the panels when the action listeners are created,
   * store variables and call above functions
   **************************************************************/
  public class TabMouseListener implements MouseListener{
    @Override
    public void mouseClicked(MouseEvent e) {
      // TODO using selected index set the currentSelected to right portfolio, to correctly add stock later.

      currentSelected=(FolioPanel)homePanel.getSelectedComponent();
      System.out.println("the selected name" + currentSelected.getName());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

  }

  public class ClearListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
      addWatchWindow.clear();
    }
  }


  public class AddStockListener implements  ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      String ticker = addWatchWindow.getTicker();
      String amount = String.valueOf(addWatchWindow.getEnteredAmount());



      try {
        String cost = StrathQuoteServer.getLastValue(ticker);
        StockHolding stock = new StockHolding(ticker, ticker, Integer.parseInt(amount), Double.parseDouble(cost));

        //TODO we need to store somewhere as a localvariable

        if (!ticker.equals("")) {

          Portfolio portfolio = portfolioMap.get(currentSelected.getName());
          if (portfolio.getStockList().contains(stock)) {
            int reply = JOptionPane.showConfirmDialog(null,
                    "This portfolio already tracking this stock. Would you like to update it?",
                    "Already exist", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
              portfolio.addStock(stock);
              currentSelected.tableModel.fireTableChangeOnAddRow();
              addWatchWindow.dispose();
            }
          } else {
            portfolio.addStock(stock);
            currentSelected.tableModel.fireTableChangeOnAddRow();
            addWatchWindow.dispose();
          }
        }

      } catch (WebsiteDataException | NoSuchTickerException e1) {
        JOptionPane.showMessageDialog(null, "This isn't a valid ticker!!");
      }
    }
  }




  public class RemoveStocksListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int selectedRow = currentSelected.getTable().getSelectedRow();
      if(selectedRow>-1) {
        int stockIndex = currentSelected.getTable().convertRowIndexToModel(selectedRow);
        StockHolding stock = currentSelected.tableModel.getTableModelStockList().get(stockIndex);

        int reply = JOptionPane.showConfirmDialog(null,
                "Are you sure you want  portfolio <" + currentSelected.getName() + "> to stop tracking <" + stock.getTicker() + ">?",
                "Remove stock", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          Portfolio portfolio = portfolioMap.get(currentSelected.getName());
          portfolio.removeStock(stock.getName());
          currentSelected.tableModel.fireTableChangeOnAddRow();
        }
      }else{
        JOptionPane.showMessageDialog(null, "No stock was selected!");
      }


    }
  }


  public class AddWatchListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      addWatchWindow.setVisible(true);
      addWatchWindow.onEnter();
      addWatchWindow.clear();
    }
  }



  public class RefreshListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      try {
        portfolioMap.get(currentSelected.getName()).refreshStocks();
        currentSelected.tableModel.fireTableChangeOnAddRow();
      } catch (WebsiteDataException | NoSuchTickerException ex) {
        ex.printStackTrace();
      }
    }
  }



public class FolioListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

    }
  }


  public class RegisterListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
      view.ShowMenu();
      displayHomePanel();
    }
  }


  public class LoginListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      view.ShowMenu();
      displayHomePanel();
    }
  }

  public class GoToWelcomePanelListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
      System.out.println("Go to Welcome Panel");
      view.HideMenu();
      displayWelcome();
    }
  }

  public class WelcomeRegisterListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      System.out.println("register");
      displayRegister();
    }
  }

  public class WelcomeLoginListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      System.out.println("login");
      displayLogin();
    }
  }

}
