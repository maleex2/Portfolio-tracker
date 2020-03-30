package View;

import Controller.FolioTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class RemoveStockWindow extends JFrame {
    private JPanel panel;
    private JLabel tickerLabel = new JLabel("Ticker Name:",JLabel.TRAILING);
    private JTextField tickerField = new JTextField(5);
    private JButton removeButton=new JButton("Remove");
    private JButton clearButton=new JButton("Clear");




    public RemoveStockWindow() {
        super("Remove a share");
        //panel=new JPanel(new BoxLayout());

        panel=new JPanel(new SpringLayout());
        setSize(300, 130);
        setResizable(false);
        panel.add(tickerLabel);
        panel.add(tickerField);
        panel.add(clearButton);
        panel.add(removeButton);




        SpringUtilities.makeCompactGrid(panel,
                2, 2, //rows, cols
                6, 6,        //initX, initY
                10, 30);


        this.add(panel);

    }

    public void addRemoveActionListener(ActionListener actionListener){
        removeButton.addActionListener(actionListener);
    }

    public void addClearActionListener(ActionListener actionListener){
        clearButton.addActionListener(actionListener);
    }
    public String getTickerName(){
        return tickerField.getText();
    }
    public void clear(){
        tickerField.setText("");
    }


}
