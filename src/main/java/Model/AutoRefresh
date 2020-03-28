package Model;


import Controller.FolioTracker;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;


public class AutoRefresh {
    Toolkit toolkit;

    Timer timer;


    public AutoRefresh(FolioTracker folioTracker) {
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.schedule(new RemindTask(folioTracker), 0, //initial delay
                20000); //subsequent rate
    }

    class RemindTask extends TimerTask {
        public boolean autoRefresh = true;
        FolioTracker folioTracker;
        public RemindTask(FolioTracker folioTracker) {
            this.folioTracker = folioTracker;
        }

        public void run() {
            if ( autoRefresh ) {
                toolkit.beep();
                try {
                    folioTracker.portfolioMap.get(folioTracker.currentSelected.getName()).refreshStocks();
                    folioTracker.currentSelected.tableModel.fireTableChangeOnAddRow();
                } catch (WebsiteDataException ex) {
                    ex.printStackTrace();
                } catch (NoSuchTickerException ex) {
                    ex.printStackTrace();
                }

            } else {
                toolkit.beep();
                System.out.println("Auto refresh was stopped.");
                //timer.cancel(); //Not necessary because we call System.exit
                System.exit(0); //Stops the AWT thread (and everything else)
            }
        }
    }
}
