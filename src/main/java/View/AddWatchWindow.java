package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

public class AddWatchWindow extends JFrame {
  private JPanel panel;
  String[] labels = {"Ticker: ", "Amount: "};
  private JLabel loginLabel = new JLabel("Ticker:",JLabel.TRAILING);
  private JTextField loginField = new JTextField(5);
  private JLabel nameLabel = new JLabel("Name:",JLabel.TRAILING);
  private JTextField nameField = new JTextField(5);
  private JLabel amountLabel = new JLabel("Amount:",JLabel.TRAILING);
  private SpinnerModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
  private JSpinner amountSpinner = new JSpinner(model);
  private JButton saveButton=new JButton("Add");
  private JButton clearButton=new JButton("Clear");




  public AddWatchWindow() {
    super("Add new shares");
    //panel=new JPanel(new BoxLayout());

    panel=new JPanel(new SpringLayout());
    setSize(300, 200);
    setResizable(false);
    panel.add(loginLabel);
    panel.add(loginField);
    panel.add(nameLabel);
    panel.add(nameField);
    panel.add(amountLabel);
    panel.add(amountSpinner);

    panel.add(clearButton);
    panel.add(saveButton);






    SpringUtilities.makeCompactGrid(panel,
            4, 2, //rows, cols
            6, 6,        //initX, initY
            10, 10);


    this.add(panel);

  }

  public void addSaveActionListener(ActionListener actionListener){
    saveButton.addActionListener(actionListener);
  }

  public void addClearActionListener(ActionListener actionListener){
    clearButton.addActionListener(actionListener);
  }

  public void onEnter(){
    getRootPane().setDefaultButton(saveButton);
    saveButton.requestFocus();
  }
  public String getTicker(){
    return loginField.getText();
  }
  public String getName(){
    return nameField.getText();
  }
  public int getEnteredAmount(){
    return (int)amountSpinner.getValue();
  }

  public void clear(){
    loginField.setText("");
    amountSpinner.setValue(1);
    nameField.setText("");

  }


}
