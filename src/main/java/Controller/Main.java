package Controller;

import Model.Account;
import Model.AutoRefresh;
import Model.Portfolio;
import Model.StockHolding;

import javax.swing.*;
import java.sql.Timestamp;

public class Main {
  public static void main(String[] args) {
    try { //Set default theme to nimbus if installed
      /**
       * Test stuff
       *
       */

      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (Exception e) {
      // If Nimbus is not available, you can set the GUI to the system look and feel.
      try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }catch (Exception all){}
    }
    FolioTracker folioTracker=new FolioTracker();

  }
}
