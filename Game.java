import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Game extends JFrame implements KeyListener{

  static JFrame frame = new JFrame(".: Game :.");
  static JPanel panel = new JPanel();
  static JLabel label = new JLabel();

  static int PanelXCoord = -300;
  static int PanelYCoord = -300;

  public static void main (String [] args){
    displayGame();
  }

  public void init()
  {
    panel.setFocusable(true);
    panel.requestFocusInWindow();
  }

  public static void displayGame()
  {
    JPanel water = new JPanel();
    water.setBounds(-450,-450,1300,1300);
    water.setBackground(Color.blue);

    panel.setBounds(PanelXCoord,PanelYCoord,1200,1200);
    panel.setLayout(null);
    panel.setBackground(Color.green);

    JPanel character = new JPanel();
    character.setBounds(185,185,30,30);
    //character.setBackground(new Color(255,255,255,0));

      JLabel boi = new JLabel("Character");
      ImageIcon picture = new ImageIcon("4nX44_A7copy.png");
      boi.setIcon(picture);
      boi.setBounds(0,0,30,30);
      boi.setVisible(false);
      boi.setVerticalTextPosition(JLabel.BOTTOM);
      boi.setHorizontalTextPosition(JLabel.CENTER);

    character.add(boi);

    frame.getContentPane().add(panel);

    panel.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {

        //Left
        if(e.getKeyCode() == 37 ){
         if( !(PanelXCoord+5>185)){
            PanelXCoord+=5;
         }
        }
        //Right
        else if (e.getKeyCode() == 39 ){
         if( !(PanelXCoord-5<-985)){
            PanelXCoord-=5;
         }
        }
        //Up
        else if (e.getKeyCode() == 38 ){
         if( !(PanelYCoord+5>185)){
            PanelYCoord+=5;
         }
        }
        //Down
        else if (e.getKeyCode() == 40 ){
         if( !(PanelYCoord-5<-985)){
            PanelYCoord-=5;
         }
        }

        System.out.println(PanelXCoord+", "+PanelYCoord);
        panel.setBounds(PanelXCoord,PanelYCoord,1200,1200);
        panel.repaint();
        frame.repaint();
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }
    });

    panel.setFocusable(true);
    panel.requestFocusInWindow();

    frame = new JFrame(".: Game :.");
    //frame.setResizable(false);
    frame.add(character);
    frame.add(panel);
    frame.add(water);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    frame.setSize(400,400);
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }


}
