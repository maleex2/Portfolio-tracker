package Controller;

import Model.AutoRefresh;

public class Main {
  public static void main(String[] args) {
    FolioTracker folioTracker=new FolioTracker();
    new AutoRefresh(folioTracker);
  }
}
