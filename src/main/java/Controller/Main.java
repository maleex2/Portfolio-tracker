package Controller;

public class Main {
  public static void main(String[] args) {
    FolioTracker folioTracker=new FolioTracker();
    new AutoRefresh(folioTracker);
  }
}
