package Model;


import Controller.FolioTracker;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;


public class AutoRefresh {


    public AutoRefresh(FolioTracker folioTracker) {

        Timer timer = new Timer();
        timer.schedule(new RemindTask(folioTracker), 0, //initial delay
                20000); //subsequent rate
    }

    static class RemindTask extends TimerTask {
        boolean autoRefresh = true;
        FolioTracker folioTracker;
        RemindTask(FolioTracker folioTracker) {
            this.folioTracker = folioTracker;
        }
        public void run() {
            if ( autoRefresh ) {

                try {
                    folioTracker.portfolioMap.get(folioTracker.currentSelected.getName()).refreshStocks();
                    folioTracker.currentSelected.tableModel.fireTableChangeOnAddRow();
                } catch (WebsiteDataException | NoSuchTickerException ex) {
                    ex.printStackTrace();
                }

            } else {

                System.out.println("Auto refresh was stopped.");
                //timer.cancel(); //Not necessary because we call System.exit
                System.exit(0); //Stops the AWT thread (and everything else)
            }
        }
    }
}
