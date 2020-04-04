package Controller;

import Model.*;
import View.*;


import javax.sound.sampled.Port;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

public class FolioTracker {
  // Create instances of the model and other controllers
  private AutoRefresh autoRefresh;
  private File file;
  private AccountManager accountManager;
  private Account account;
  private FolioTracker instance;
  //private CustomFileParser parser;
  //private Player player;

  // Create instances for each of GUI panels
  private MainWindow view;
  //private AddWatchWindow addWatchWindow;
  private JPanel card;
  private CardLayout cardLayout;
  private WelcomePanel welcomePanel;
  private LoginPanel loginPanel;
  private RegisterPanel registerPanel;
  private HomePanel homePanel;


  public HashMap<String, Portfolio> portfolioMap = new HashMap<>();

  public FolioPanel currentSelected;


  //private HomePanel menu;
  //private GamePanel game;
  //private GameChoice gameChoice;

  /*************************************
   * Construct display and set up login
   *************************************/

  public FolioTracker() {
    accountManager=new AccountManager();
    this.instance = this;
    this.view = new MainWindow();
    this.view.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {

        if (account != null) {
          int answer = JOptionPane.showConfirmDialog(view, "Would you like to save any changes or any unsaved portfolios?");
          if (answer == JOptionPane.OK_OPTION) {
            for (Portfolio p : account.getPortfolioList()) {
              try {
                account.savePortfolio(p.getName());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
            view.setVisible(false);
            view.dispose();
            System.exit(0);
          } else if (answer == JOptionPane.NO_OPTION) {
            view.setVisible(false);
            view.dispose();
            System.exit(0);
          }
        } else {
          view.setVisible(false);
          view.dispose();
          System.exit(0);
        }
      }
    });
    view.addSavePortfolioListener(new SaveFolioListener());
    view.addCreateNewPortfolioListener(new CreateNewPortfolioListener());
    view.addRemovePortfolioListener(new RemovePortfolioListener());
    view.addLogOutListener(new LogOutListener());
    this.cardLayout = new CardLayout();
    this.card = new JPanel(cardLayout);

    this.welcomePanel = new WelcomePanel();
    this.welcomePanel.addLoginListener(new WelcomeLoginListener());
    this.welcomePanel.addRegisterListener(new WelcomeRegisterListener());

    this.loginPanel = new LoginPanel(); // The panel for the login screen
    this.loginPanel.addActionListener(new LoginListener());
    this.loginPanel.addGoToWelcomePanelListener(new GoToWelcomePanelListener());

    this.registerPanel = new RegisterPanel();
    this.registerPanel.addActionListener(new RegisterListener());
    this.registerPanel.addGoToWelcomePanelListener(new GoToWelcomePanelListener());
    this.homePanel = new HomePanel(new TabMouseListener());


    //TODO add actionlisteners

    this.card.add("welcomePanel", welcomePanel);
    this.card.add("loginPanel", loginPanel);
    this.card.add("registerPanel", registerPanel);
    this.card.add("homePanel", homePanel);

    this.view.add(card);
    this.cardLayout.show(card, "welcomePanel");

    this.view.setVisible(true);


    //this.addWatchWindow = new AddWatchWindow();
    //this.addWatchWindow.addSaveActionListener(new AddStockListener());
    //this.addWatchWindow.addClearActionListener(new ClearListener());


  }



  /*****************************************************************
   * Displaying panels
   *
   * Used for switching between windows, setting up what needs to be
   *****************************************************************/
  public void displayHomePanel() {
    cardLayout.show(card, "homePanel");
  }

  public void displayLogin() {
    cardLayout.show(card, "loginPanel");
  }

  public void displayRegister() {
    cardLayout.show(card, "registerPanel");
  }

  public void displayWelcome() {
    cardLayout.show(card, "welcomePanel");
  }


  /**************************************************************
   * ACTION LISTENERS
   *
   * Passed to the panels when the action listeners are created,
   * store variables and call above functions
   **************************************************************/
  public class TabMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
      // TODO using selected index set the currentSelected to right portfolio, to correctly add stock later.

      currentSelected = (FolioPanel) homePanel.getSelectedComponent();

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

  public class ClearListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      currentSelected.clear();
    }
  }


  public class AddStockListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      String ticker = currentSelected.getEnteredTicker();
      String name= currentSelected.getEnteredName();
      String amount = String.valueOf(currentSelected.getEnteredAmount());


      try {
        String cost = StrathQuoteServer.getLastValue(ticker);
        if(name.equals("")){
          name=ticker+"_stock";
        }
        StockHolding stock = new StockHolding(ticker, name, Integer.parseInt(amount), Double.parseDouble(cost));

        if (!ticker.equals("")) {
          Portfolio portfolio = portfolioMap.get(currentSelected.getName());
          if (portfolio.getStockList().contains(stock)) {
            int reply = JOptionPane.showConfirmDialog(null,
                    "This portfolio already tracking this stock. Would you like to update it?",
                    "Already exist", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
              portfolio.addStock(stock);
              currentSelected.tableModel.fireTableChangeOnAddRow();
              System.out.println("setting to "+portfolio.getTotalValue());
              currentSelected.setTotalValue(portfolio.getTotalValue());
            }
          } else {
            portfolio.addStock(stock);
            System.out.println("setting to "+portfolio.getTotalValue());
            currentSelected.setTotalValue(portfolio.getTotalValue());
            currentSelected.tableModel.fireTableChangeOnAddRow();
          }
          currentSelected.clear();
        }

      } catch ( NoSuchTickerException | WebsiteDataException e1) {
        JOptionPane.showMessageDialog(null, "This isn't a valid ticker!!");
      }catch(WebsiteConnectionException e1){
        JOptionPane.showMessageDialog(null, "Problems connecting to the server!!");
      }
    }
  }


  public class RemoveStocksListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int selectedRow = currentSelected.getTable().getSelectedRow();
      if (selectedRow > -1) {
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
      } else {
        JOptionPane.showMessageDialog(null, "No stock was selected!");
      }


    }
  }





  public class RefreshListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        portfolioMap.get(currentSelected.getName()).refreshStocks();
        currentSelected.tableModel.fireTableChangeOnAddRow();
      } catch (NoSuchTickerException  | WebsiteDataException ex) {
        ex.printStackTrace();
      }catch (WebsiteConnectionException ex){
        JOptionPane.showMessageDialog(null, "Problems connecting to the server!!");
      }
    }
  }


  public class SaveFolioListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        if(currentSelected!=null) {
          account.savePortfolio(currentSelected.getName());

        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }


  public class CreateNewPortfolioListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String name = JOptionPane.showInputDialog("Please choose a name:");

      if (name != null && !name.equals("")) {
        int reply = JOptionPane.showConfirmDialog(null,
                "Do you want to create new portfolio <" + name + ">?",
                "Create new...", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          Portfolio portfolio = new Portfolio(name);
          if(account.addPortfolio(portfolio)) {
            portfolioMap.put(portfolio.getName(), portfolio);
            //public FolioPanel createPanel(Portfolio p, ActionListener removeStockActionListener, ActionListener addSaveActionListener, ActionListener addClearActionListener, ActionListener refreshActionListener) {
            currentSelected = homePanel.createPanel(portfolio, new RemoveStocksListener(), new AddStockListener(), new ClearListener(), new RefreshListener(), new UpdateTotalOnEdit());
            homePanel.setSelectedComponent(currentSelected);
            JOptionPane.showMessageDialog(null, "New portfolio was created.");
          }else{
            JOptionPane.showMessageDialog(null, "Portfolio with given name already exist");
          }
        }
      }else{
        JOptionPane.showMessageDialog(null, "Please enter the name for portfolio");
      }
    }
  }

  public class RemovePortfolioListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if(currentSelected!=null) {
        int reply = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete <" + currentSelected.getName() + ">?",
                "Delete " + currentSelected.getName() + "...", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          homePanel.remove(currentSelected);
          String result = account.removePortfolio(portfolioMap.get(currentSelected.getName())) ? "Portfolio file was deleted" : "Couldn't delete portfolios save file";
          portfolioMap.remove(portfolioMap.get(currentSelected.getName()));
          JOptionPane.showMessageDialog(null, result);
          currentSelected = (FolioPanel) homePanel.getSelectedComponent();
        }
      }
    }
  }


  public class RegisterListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String name=registerPanel.getText();
      if(name.length()<3){
        JOptionPane.showMessageDialog(null, "Name should be at least 3 char long");
      }else {
        account=accountManager.registerAccount(name);

        if(account==null){
          JOptionPane.showMessageDialog(null, "This username has already been taken.");
        }else {
          JOptionPane.showMessageDialog(null, "Successfully registered!");
          autoRefresh= new AutoRefresh(instance, account);
          view.ShowMenu();
          displayHomePanel();
          registerPanel.clearText();
        }
      }
    }
  }


  public class LoginListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String name=loginPanel.getText();
      account=accountManager.loginAccount(name);
      if(account==null) {
        JOptionPane.showMessageDialog(null, "Account was not found!");
      }else {

        account.loadPortfolios();
        for (Portfolio p : account.getPortfolioList()) {
          portfolioMap.put(p.getName(), p);
        }
        for (Portfolio p : portfolioMap.values()) {
          homePanel.createPanel(p, new RemoveStocksListener(), new AddStockListener(), new ClearListener(), new RefreshListener(), new UpdateTotalOnEdit());
        }
        currentSelected = (FolioPanel) homePanel.getSelectedComponent();
        System.out.println("Setting__to "+portfolioMap.get(currentSelected.getName()).getTotalValue());
        currentSelected.setTotalValue(portfolioMap.get(currentSelected.getName()).getTotalValue());
        autoRefresh= new AutoRefresh(instance, account);
        displayHomePanel();
        view.ShowMenu();
        loginPanel.clearText();
      }
    }
  }

  public class LogOutListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int answer = JOptionPane.showConfirmDialog(view, "Would you like to save any changes or any unsaved portfolios?");
      if (answer == JOptionPane.OK_OPTION) {
        for (Portfolio p : account.getPortfolioList()) {
          try {
            account.savePortfolio(p.getName());
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
        homePanel.removeAll();
        autoRefresh.Stop();
        account = null;
        portfolioMap.clear();
        view.HideMenu();
        displayWelcome();
      } else if (answer == JOptionPane.NO_OPTION) {
        homePanel.removeAll();
        autoRefresh.Stop();
        account = null;
        portfolioMap.clear();
        view.HideMenu();
        displayWelcome();
      }
    }
  }

  public class UpdateTotalOnEdit implements CellEditorListener{

    @Override
    public void editingStopped(ChangeEvent e) {
      currentSelected.setTotalValue(portfolioMap.get(currentSelected.getName()).getTotalValue());
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
      currentSelected.setTotalValue(portfolioMap.get(currentSelected.getName()).getTotalValue());
    }
  }

  public class GoToWelcomePanelListener implements ActionListener {
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




