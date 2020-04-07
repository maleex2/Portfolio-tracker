package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

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
  private JPanel mainCard = new JPanel(new BorderLayout(0, 50));
  private JPanel buttons = new JPanel();

  private JButton loginBtn = new JButton("Login");
  private JButton registerBtn = new JButton("Register");

  private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
  private static String imageFileName="testLogo.png";
  private static File stockImage = new File(Objects.requireNonNull(classLoader.getResource(imageFileName)).getFile());

  public WelcomePanel()
  {
    buttons.add(loginBtn);
    buttons.add(registerBtn);
    JLabel image = new JLabel(new ImageIcon(stockImage.getAbsolutePath()));
    image.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
    mainCard.add(buttons, BorderLayout.SOUTH);
    mainCard.add(image,BorderLayout.NORTH);
    add(mainCard);

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
