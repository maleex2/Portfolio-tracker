package View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {

  private JButton backBtn= new JButton("Back");
  private JLabel registerLabel = new JLabel("Username:");
  private JTextField promptField = new JTextField(10);
  private JButton registerBtn = new JButton("Login");

  public RegisterPanel()
  {
    add(backBtn);
    add(registerLabel);
    add(promptField);
    add(registerBtn);

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
