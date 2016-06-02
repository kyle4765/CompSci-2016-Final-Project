import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Game extends JFrame {

  public void init() {
    panel.setFocusable(true);
    panel.requestFocusInWindow();
  }

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
  static JPanel character = new JPanel();
  static int PanelXCoord = -300;
  static int PanelYCoord = -300;
  static JLabel characterImage = new JLabel();
  static ImageIcon pix;
  static int WalkOrRun = 0;
  static JLabel time;
  static ArrayList<JLabel> enemiesList = new ArrayList<JLabel>();
  static ArrayList<Boolean> explodeList = new ArrayList<Boolean>();
  static ArrayList<JLabel> coinList = new ArrayList<JLabel>();
  static int movementCount = 0;
  static boolean moveDirection = true;
  static boolean gameIsPlaying = true;
  static int coinCount = 0;
  static int sleepTime = 20;
  static int enemyAmount = 10;
  static int coinGenerate = 5;
  static int timerRemaining = 59;
  static ImageIcon explosion = new ImageIcon("Explosion.png");
  static ImageIcon healthIcon = new ImageIcon("Armor.png");
  static JPanel healthPanel = new JPanel();
  static boolean coinDirection = true;
  static int coinMove = 0;
  static JPanel timer = new JPanel();
  static int ArmorCount = 3;
  static ArrayList<JLabel> healthList = new ArrayList<JLabel>();
  static JPanel gamePlayInfo = new JPanel();

  //Threads
  static Thread thread1 = new Thread() {
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          timerStart(timerRemaining);
        }
      }
  };
  static Thread thread2 = new Thread() {
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          moveCounter();
        }
      }
  };
  static Thread thread3 = new Thread() {
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          moveCoinCounter();
        }
      }
  };
  static Thread enemyMoveSide = new Thread(){
      public void run() {
        for (int k=1; k<=enemyAmount; k++) {
          JLabel en = makeEnemy();
          boolean hasExploded = false;
          explodeList.add(Boolean.valueOf(hasExploded));
          enemiesList.add(en);
          int num = k;
            Thread thread = new Thread() {
              public void run() {
                moveEnemySide(en, num);
              }
          };
          thread.start();
        }
      }
  };
  static Thread enemyMoveUp = new Thread(){
      public void run() {
        for (int k=1; k<=enemyAmount; k++) {
          JLabel en = makeEnemy();
          boolean hasExploded = false;
          explodeList.add(Boolean.valueOf(hasExploded));

          enemiesList.add(en);
            Thread thread = new Thread() {
              public void run() {
                moveEnemyUp(en);
              }
          };
          thread.start();
        }
      }
  };
  static Thread makeCoins = new Thread(){
      public void run() {
        for (int k=1; k<=coinGenerate; k++) {
          JLabel co = makeCoin();
          coinList.add(co);
            Thread thread = new Thread() {
              public void run() {
                motionCoin(co);
              }
          };
          thread.start();
        }
      }
  };
  static Thread hitCheck = new Thread() {
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          hitChecker();
        }
      }
  };
  static Thread coinCheck = new Thread() {
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          coinChecker();
        }
      }
  };
  static Thread stopFlashing = new Thread() {
      public void run() {
        while(true){
          frame.repaint();
        }
      }
  };

  public static void main (String [] args){
    for(int k=0; k<ArmorCount; k++)
    {
      JLabel health1 = new JLabel(healthIcon);
      healthList.add(health1);
      health1.setBounds(0,0,30,30);
      healthPanel.add(health1);
    }

    enemyMoveUp.start();
    enemyMoveSide.start();
    makeCoins.start();

    displayGame();

    thread1.start();
    thread2.start();
    thread3.start();
    hitCheck.start();
    coinCheck.start();
    stopFlashing.start();

    //gameOver();
  }

  public void run(){}

  public static void displayGame(){
    JPanel water = new JPanel();
    water.setBounds(-450,-450,1300,1300);
    water.setBackground(Color.blue);

    panel.setBounds(PanelXCoord,PanelYCoord,1200,1200);
    panel.setLayout(null);
    panel.setBackground(new Color(141, 214, 116));

    character.setBounds(175,175,50,50);
    character.setBackground(new Color(255,255,255,0));

    pix = new ImageIcon("Char-Walk-Right.png");
    characterImage.setIcon(pix);
    characterImage.setBounds(0,0,50,50);
    characterImage.setVisible(true);
    characterImage.setVerticalTextPosition(JLabel.BOTTOM);
    characterImage.setHorizontalTextPosition(JLabel.CENTER);

    character.add(characterImage);

    timer.setBounds(320,0,80,30);
    timer.setBackground(new Color(255,255,255,0));

    time = new JLabel("Time:  "+timerRemaining);
    time.setBounds(0,-5,80,30);
    timer.add(time);

    healthPanel.setBounds(210,-5,110,30);
    healthPanel.setBackground(new Color(255,255,255,0));

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
    frame.add(healthPanel);
    frame.add(panel);
    frame.add(water);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    frame.setSize(400,400);
  }

  public static void gameOver(){
    try{
      thread1.sleep(100);
      thread2.sleep(100);
      thread3.sleep(100);
    } catch (InterruptedException e) {
          e.printStackTrace();
    };
    enemyMoveUp.interrupt();
    enemyMoveSide.interrupt();
    makeCoins.interrupt();
    thread1.interrupt();
    thread2.interrupt();
    thread3.interrupt();
    hitCheck.interrupt();
    coinCheck.interrupt();

    timer.setVisible(false);
    healthPanel.setVisible(false);
    panel.setVisible(false);
    character.setVisible(false);

    gamePlayInfo.setBounds(0,0,400,400);
    gamePlayInfo.setLayout(null);
    gamePlayInfo.setBackground(new Color(254, 174, 53));

    JLabel WinLose = new JLabel();
      if(coinCount==coinGenerate){
        WinLose.setText("You Win!");
      }
      else{
        WinLose.setText("You Lose");
      }
    WinLose.setFont(new Font("BlockArt", Font.PLAIN, 40));
    WinLose.setBounds(90,75,200,50);

    JLabel CoinData = new JLabel();
      CoinData.setText("Coins:              \t"+coinCount);
    JLabel EnemiesHit = new JLabel();
    EnemiesHit.setText("Enemies Hit:     \t"+(3-ArmorCount));

    CoinData.setFont(new Font("BlockArt", Font.PLAIN, 20));
    EnemiesHit.setFont(new Font("BlockArt", Font.PLAIN, 20));

    CoinData.setBounds(90,125,300,50);
    EnemiesHit.setBounds(90,160,300,50);

    gamePlayInfo.add(CoinData);
    gamePlayInfo.add(EnemiesHit);
    gamePlayInfo.add(WinLose);

    frame.add(gamePlayInfo);
  }

  public static void moveCounter(){
    try {
      while (gameIsPlaying==true) {
        if(moveDirection == true){
          movementCount++;
        }
        else{
          movementCount--;
        }

        if(movementCount==200){
          moveDirection = false;
        }
        else if(movementCount==0){
          moveDirection = true;
        }
        Thread.sleep(sleepTime);
      }
    } catch (InterruptedException e) {
        e.printStackTrace();
        thread2.currentThread().interrupt();
    }
  }

  public static void moveCoinCounter(){
    try {
      while (gameIsPlaying==true) {
        if(coinDirection == true){
          coinMove++;
        }
        else{
          coinMove--;
        }

        if(coinMove==10){
          coinDirection = false;
        }
        else if(coinMove==0){
          coinDirection = true;
        }
        Thread.sleep(sleepTime);
      }
    } catch (InterruptedException e) {
        e.printStackTrace();
        thread3.currentThread().interrupt();
    }
  }

  public static void timerStart(int t){
    try {
      while (timerRemaining>0 && gameIsPlaying==true) {
          timerRemaining--;
          Thread.sleep(1000);
          time.setText("Time:  "+timerRemaining);
      }
      if(timerRemaining==0)
      {
        gameOver();
      }
    } catch (InterruptedException e) {
        e.printStackTrace();
        thread1.currentThread().interrupt();
    }
  }

  public static JLabel makeEnemy(){
    JLabel enemy = new JLabel();

    ImageIcon enemyPic = new ImageIcon("Enemy.png");
    enemy.setIcon(enemyPic);
    int x = 600;
    int y = 600;
    while (Math.abs(600-x) < 150 || Math.abs(600-y) < 150)
    {
    	x = (int)(Math.random()*1100) + 50;
    	y = (int)(Math.random()*1100) + 50;
    }
    enemy.setBounds(x,y,50,50);
    enemy.setVisible(true);
    enemy.setVerticalTextPosition(JLabel.BOTTOM);
    enemy.setHorizontalTextPosition(JLabel.CENTER);
    enemy.setBackground(new Color(0, 194, 255));
    panel.add(enemy);
    return enemy;
  }

  public static JLabel makeCoin(){
    JLabel coin = new JLabel();
    ImageIcon coinPic = new ImageIcon("Coin.png");
    coin.setIcon(coinPic);
    int x = 600;
    int y = 600;
    while (Math.abs(600-x) < 150 || Math.abs(600-y) < 150)
    {
      x = (int)(Math.random()*1100) + 50;
      y = (int)(Math.random()*1100) + 50;
    }
    coin.setBounds(x,y,50,50);
    coin.setVisible(true);
    coin.setVerticalTextPosition(JLabel.BOTTOM);
    coin.setHorizontalTextPosition(JLabel.CENTER);
    coin.setBackground(new Color(0, 194, 255));
    panel.add(coin);
    return coin;
  }

  public static void moveEnemySide(JLabel enemy, int k){
      try {
        while (true) {
        panel.repaint();
        frame.repaint();
          if(moveDirection == true){
            if( !(enemy.getX()+1>1150) )
            {
              enemy.setBounds(enemy.getX()+1,enemy.getY(),50,50);
            }
            else{}
            Thread.sleep(sleepTime);
          }
          else {
            if( !(enemy.getX()-1<50) )
            {
              enemy.setBounds(enemy.getX()-1,enemy.getY(),50,50);
            }
            else{}
            Thread.sleep(sleepTime);
          }
         }
      } catch (InterruptedException e) {
            e.printStackTrace();
        };
  }

  public static void moveEnemyUp(JLabel enemy){
    try {
      while (true) {
      panel.repaint();
      frame.repaint();
        if(moveDirection == true){
          if( !(enemy.getY()+1>1150) )
          {
            enemy.setBounds(enemy.getX(),enemy.getY()+1,50,50);
          }
          else{}
          Thread.sleep(sleepTime);
        }
        else {
          if( !(enemy.getY()-1<50) )
          {
            enemy.setBounds(enemy.getX(),enemy.getY()-1,50,50);
          }
          else{}
          Thread.sleep(sleepTime);
        }
       }
    } catch (InterruptedException e) {
          e.printStackTrace();
      };
  }

  public static void hitChecker() {
    while(gameIsPlaying == true && enemiesList.size()>1)
    {
      for(int k=0; k<enemiesList.size()-2; k++)
      {
        JLabel enes = enemiesList.get(k);
        int CharacterX = (int)characterImage.getLocationOnScreen().getX();
        int CharacterY = (int)characterImage.getLocationOnScreen().getY();
        int EnemyX = (int)enes.getLocationOnScreen().getX();
        int EnemyY = (int)enes.getLocationOnScreen().getY();

        if( (CharacterX > EnemyX-40 && CharacterX < EnemyX+40) && (CharacterY > EnemyY-40 && CharacterY < EnemyY+40) && (explodeList.get(k).booleanValue() == false)){
          explodeList.set(k, new Boolean(true));
          ImageIcon blank = new ImageIcon("thisisnothing");
          healthList.get(ArmorCount-1).setIcon(blank);
          ArmorCount--;

          enes.setIcon(explosion);
            try{
              Thread.sleep(200);
            } catch (InterruptedException e) {
            e.printStackTrace();
            };
          System.out.println("Hit -- "+ArmorCount+" Lives Remaining");
          MakeSound.playSound("myLeg.wav");
        }
        if(ArmorCount==0)
        {
          System.out.println("Hit!\tGame Over");
          enemyMoveUp.interrupt();
          enemyMoveSide.interrupt();
          makeCoins.interrupt();
          thread1.interrupt();
          thread2.interrupt();
          thread3.interrupt();
          hitCheck.interrupt();
          coinCheck.interrupt();
          gameIsPlaying = false;
          gameOver();
          break;
        }
      }
    }
  }

  public static void hit() {
    ImageIcon blank = new ImageIcon("thisisnothing");
    healthList.get(ArmorCount-1).setIcon(blank);
    ArmorCount--;
    System.out.println("Hit -- " + ArmorCount + " Lives Remaining");
  }

  public static void coinChecker() {
    while(gameIsPlaying == true && coinList.size()>1)
    {
      for(int k=0; k<coinList.size(); k++)
      {
        JLabel cos = coinList.get(k);
        int CharacterX = (int)characterImage.getLocationOnScreen().getX();
        int CharacterY = (int)characterImage.getLocationOnScreen().getY();
        int CoinX = (int)cos.getLocationOnScreen().getX();
        int CoinY = (int)cos.getLocationOnScreen().getY();

        if( (CharacterX > CoinX-40 && CharacterX < CoinX+40) && (CharacterY > CoinY-40 && CharacterY < CoinY+40) ){
          ImageIcon blank = new ImageIcon("thisisnothing");
          cos.setLocation(-50,-50);
          //cos.setIcon(blank);
          coinCount++;
          //coinList.remove(cos);
          System.out.println( (coinGenerate-coinCount)+" Coins Remaing");
        }

        if(coinCount == coinGenerate)
        {
          gameIsPlaying=false;
          System.out.println("You Win!");
          gameOver();
        }

      }
    }

  }

  public static void motionCoin(JLabel coin){

    try {
      while (true) {
        panel.repaint();
        frame.repaint();
        if(coinDirection == true){
          if( !(coin.getY()+1>1150) )
          {
            coin.setBounds(coin.getX(),coin.getY()+1,50,50);
          }
          Thread.sleep(150);
        }
        else {
          if( !(coin.getY()-1<50) )
          {
            coin.setBounds(coin.getX(),coin.getY()-1,50,50);
          }
          Thread.sleep(150);
        }
       }
    } catch (InterruptedException e) {
          e.printStackTrace();
      };

  }

}
