package View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
  private JMenuBar mb = new JMenuBar();
  private JMenu file, more;
  private JMenuItem save, saveAs, exit, help,about;

  /**********************************************************************************
   * Sets up the main window which will contain the layout which displays each panel
   **********************************************************************************/

  public MainWindow() {
    super("FolioTracker");

    file = new JMenu("File");
    save = new JMenuItem("Save");
    saveAs = new JMenuItem("Save as..");
    exit = new JMenuItem("Logout");

    more = new JMenu("More..");
    help = new JMenuItem("Help");
    about = new JMenuItem("About");

    file.add(save);
    file.add(saveAs);
    file.add(exit);
    more.add(help);
    more.add(about);
    mb.add(file);
    mb.add(more);
    mb.setVisible(false);
    setJMenuBar(mb);
    setSize(1080, 720);
    setResizable(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

  }

  public void ShowMenu() {
    mb.setVisible(true);
  }

  public void HideMenu() {
    mb.setVisible(false);
  }
  public void addNewFolioListener(ActionListener actionListener){
    save.addActionListener(actionListener);
  }


}






