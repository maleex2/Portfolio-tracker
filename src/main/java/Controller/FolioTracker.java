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


  private HashMap<String, Portfolio> portfolioMap=new HashMap<>();

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




    String stockEntry[] = new String[4];
    stockEntry[0] = "test";
    stockEntry[1] = "1";
    stockEntry[2] = "1";
    stockEntry[3] = "0";

   port1.addStock(stockEntry);
    //port2.addStock("test");


    for(Portfolio p:portfolioMap.values()){
      homePanel.createPanel(p,new AddWatchListener(),new RefreshListener());

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


      currentSelected=(FolioPanel) homePanel.getSelectedComponent();
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


  public class AddStockListener implements  ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {

      String ticker = addWatchWindow.getTicker();
      String amount = String.valueOf(addWatchWindow.getEnteredAmount());
      //TODO input validation
      //TODO add logic to retrieve a stock for a given ticker from server, add it to portfolio and update our local file

      String cost = "";

      try {
        cost = StrathQuoteServer.getLastValue(ticker);

      } catch (WebsiteDataException e1) {
        System.out.println("Ticker doesn't exist");
      } catch (NoSuchTickerException e1) {
        System.out.println("Ticker doesn't exist");
      }
      addWatchWindow.dispose();

      String stockEntry[] = new String[4];

      stockEntry[0] = ticker;
      stockEntry[1] = amount;
      stockEntry[2] = cost;
      stockEntry[3] = String.valueOf(homePanel.getSelectedIndex());

      //TODO we need to store somewhere as a localvariable

      System.out.println(currentSelected);
      portfolioMap.get(currentSelected.getName()).addStock(stockEntry);
      currentSelected.tableModel.fireTableChangeOnAddRow();


    }
  }

  public class AddWatchListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      addWatchWindow.setVisible(true);
    }
  }

  public class RefreshListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){

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
