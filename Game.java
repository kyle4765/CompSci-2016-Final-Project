import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game{

  static JFrame frame;
  static JPanel panel;

  public static void main (String [] g){

    displayGame();

  }

  public static void displayGame()
  {
    frame = new JFrame(".: Game :.");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    frame.setSize(620,330);
  }



}
