package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class LoginPanel extends JPanel
{
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
  private JButton backBtn= new JButton("Back");
  private JLabel loginLabel = new JLabel("Username:");
  private JTextField promptField = new JTextField(10);
  private JButton loginBtn = new JButton("Login");

  private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
  private static String imageFileName="testLogo.png";
  private static File stockImage = new File(Objects.requireNonNull(classLoader.getResource(imageFileName)).getFile());

  public LoginPanel()
  {
    buttons.add(backBtn);
    buttons.add(loginLabel);
    buttons.add(promptField);
    buttons.add(loginBtn);
    JLabel image = new JLabel(new ImageIcon(stockImage.getAbsolutePath()));
    image.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
    mainCard.add(buttons, BorderLayout.SOUTH);
    mainCard.add(image,BorderLayout.NORTH);
    add(mainCard);

  }

  public String getText()
  {
    return promptField.getText();
  }
  public void clearText(){
    promptField.setText("");
  }

  public void addGoToWelcomePanelListener(ActionListener goToWelcomePanelListener){
    backBtn.addActionListener(goToWelcomePanelListener);
  }

  public void addActionListener(ActionListener loginListener)

  {
    loginBtn.addActionListener(loginListener);
    promptField.addActionListener(loginListener);
  }

}
