package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class RegisterPanel extends JPanel {

  private JPanel mainCard = new JPanel(new BorderLayout(0, 50));
  private JPanel buttons = new JPanel();
  private JButton backBtn= new JButton("Back");
  private JLabel registerLabel = new JLabel("Username:");
  private JTextField promptField = new JTextField(10);
  private JButton registerBtn = new JButton("Register");


  private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
  private static String imageFileName="testLogo.png";
  private static File stockImage = new File(Objects.requireNonNull(classLoader.getResource(imageFileName)).getFile());


  public RegisterPanel()
  {
    buttons.add(backBtn);
    buttons.add(registerLabel);
    buttons.add(promptField);
    buttons.add(registerBtn);
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
    registerBtn.addActionListener(loginListener);
    promptField.addActionListener(loginListener);
  }
}
