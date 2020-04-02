package Model;


import Controller.FolioTracker;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;


public class AutoRefresh {


    public AutoRefresh(FolioTracker folioTracker, Account account) {

        Timer timer = new Timer();
        timer.schedule(new RemindTask(folioTracker, account), 0, //initial delay
                20000); //subsequent rate
    }

    static class RemindTask extends TimerTask {
        boolean autoRefresh = true;
        FolioTracker folioTracker;
        Account account;

        RemindTask(FolioTracker folioTracker, Account account) {
            this.folioTracker = folioTracker;
            this.account = account;
        }

        public void run() {
            if (autoRefresh) {

                try {
                    if (!folioTracker.portfolioMap.isEmpty() && folioTracker.currentSelected!=null) {
                        if (folioTracker.portfolioMap.get(folioTracker.currentSelected.getName()).refreshStocks()) {
                            account.savePortfolio(folioTracker.currentSelected.getName());
                        }
                    }
                } catch (Exception ex) {
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
