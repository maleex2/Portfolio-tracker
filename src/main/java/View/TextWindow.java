package View;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

public class TextWindow extends JFrame {
  private JTextArea textArea = new JTextArea();


  private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
  private static String imageFileName="testLogoSmall.png";
  private static File stockImage = new File(Objects.requireNonNull(classLoader.getResource(imageFileName)).getFile());
  public TextWindow(String windowName,String text) {
    super(windowName);
    this.setIconImage(new ImageIcon(stockImage.getAbsolutePath()).getImage());
    setSize(400, 400);
    setResizable(false);
    textArea.setEditable(false);
    textArea.setCursor(null);
    textArea.setOpaque(false);
    textArea.setFocusable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setText(text);
    this.add(textArea);
  }

}
