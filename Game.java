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
  static JPanel panel = new JPanel(){
                 @Override
           protected void paintComponent(Graphics grphcs) {
               super.paintComponent(grphcs);
               Graphics2D g2d = (Graphics2D) grphcs;
               g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
               GradientPaint gp = new GradientPaint(
                  0, 0, new Color(4, 219, 0),
                  getWidth(), getHeight(), new Color(0, 154, 57));
               g2d.setPaint(gp);
               g2d.fillRect(0, 0, getWidth(), getHeight());

        }
      };
  static JLabel label = new JLabel();

  static int PanelXCoord = -300;
  static int PanelYCoord = -300;

  static ImageIcon pix;
  static int WalkOrRun = 0;

  static JLabel time;
  static int timerRemaining = 59;

  public static void main (String [] args){
    displayGame();
    timerStart(timerRemaining);
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
    panel.setBackground(new Color(141, 214, 116));

    JPanel character = new JPanel();
    character.setBounds(175,175,50,50);
    character.setBackground(new Color(255,255,255,0));

    JLabel characterImage = new JLabel();
    pix = new ImageIcon("Char-Walk-Right.png");
    characterImage.setIcon(pix);
    characterImage.setBounds(0,0,50,50);
    characterImage.setVisible(true);
    characterImage.setVerticalTextPosition(JLabel.BOTTOM);
    characterImage.setHorizontalTextPosition(JLabel.CENTER);

    character.add(characterImage);

    JPanel timer = new JPanel();
    timer.setBounds(320,0,80,30);

    time = new JLabel("Time:  "+timerRemaining);
    time.setBounds(0,-5,80,30);
    timer.add(time);

    frame.getContentPane().add(panel);

    panel.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {

        //Left
        if(e.getKeyCode() == 37 ){
         if( !(PanelXCoord+10>185)){
            PanelXCoord+=10;
            if(WalkOrRun == 0){
              pix = new ImageIcon("Char-Run-Left.png");
              WalkOrRun++;
            }
            else{
              pix = new ImageIcon("Char-Walk-Left.png");
              WalkOrRun--;
            }
            characterImage.setIcon(pix);
          }
        }
        //Right
        else if (e.getKeyCode() == 39 ){
         if( !(PanelXCoord-10<-985)){
            PanelXCoord-=10;
            if(WalkOrRun == 0){
              pix = new ImageIcon("Char-Run-Right.png");
              WalkOrRun++;
            }
            else{
              pix = new ImageIcon("Char-Walk-Right.png");
              WalkOrRun--;
            }
            characterImage.setIcon(pix);
          }
        }
        //Up
        else if (e.getKeyCode() == 38 ){
           if( !(PanelYCoord+10>175)){
              PanelYCoord+=10;
            if(WalkOrRun == 0){
              pix = new ImageIcon("Char-Run-Right.png");
              WalkOrRun++;
            }
            else{
              pix = new ImageIcon("Char-Walk-Right.png");
              WalkOrRun--;
            }
            characterImage.setIcon(pix);
          }
        }
        //Down
        else if (e.getKeyCode() == 40 ){
           if( !(PanelYCoord-10<-975)){
              PanelYCoord-=10;
            if(WalkOrRun == 0){
              pix = new ImageIcon("Char-Run-Left.png");
              WalkOrRun++;
            }
            else{
              pix = new ImageIcon("Char-Walk-Left.png");
              WalkOrRun--;
            }
            characterImage.setIcon(pix);
          }
        }


        //System.out.println(PanelXCoord+", "+PanelYCoord);
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
    frame.add(timer);
    frame.add(panel);
    frame.add(water);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    frame.setSize(400,400);
  }

  public static void timerStart(int t){
    try {
    while (true) {
        System.out.println(timerRemaining);
        timerRemaining--;
        time.setText("Time:  "+timerRemaining);
        Thread.sleep(1000);
    }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
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
