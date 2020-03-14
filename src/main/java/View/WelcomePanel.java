package View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {
  /***************************************************************
   * Displays the login screen
   *
   * Controlled by the FolioTracker
   *
   * Creates components
   * Creates method which returns the usersname
   * Creates methods which allows action listeners to be set up
   **************************************************************/

  private JButton loginBtn = new JButton("Login");
  private JButton registerBtn = new JButton("Register");

  public WelcomePanel()
  {

    add(loginBtn);
    add(registerBtn);
  }


  public void addLoginListener(ActionListener loginListener)
  {
    loginBtn.addActionListener(loginListener);
  }
  public void addRegisterListener(ActionListener loginListener)
  {
    registerBtn.addActionListener(loginListener);
  }
}
