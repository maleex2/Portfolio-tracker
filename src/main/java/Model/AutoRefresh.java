package Model;


import Controller.FolioTracker;
import View.FolioPanel;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;


public class AutoRefresh {
    private Timer timer;

    public AutoRefresh(FolioTracker folioTracker, Account account) {

        timer = new Timer();
        timer.schedule(new RemindTask(folioTracker, account), 0, //initial delay 20000);
                20000); //subsequent rate
    }

    public void Stop(){
        timer.cancel();
        timer.purge();
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
                        Portfolio portfolio=folioTracker.portfolioMap.get(folioTracker.currentSelected.getName());
                        folioTracker.currentSelected.setTotalValue(portfolio.getTotalValue());
                        if (portfolio.refreshStocks()) {
                            if(account.isPortfolioSaved(portfolio)) {
                                account.savePortfolio(folioTracker.currentSelected.getName());
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                //timer.cancel(); //Not necessary because we call System.exit
                System.exit(0); //Stops the AWT thread (and everything else)
            }
        }
    }
}
