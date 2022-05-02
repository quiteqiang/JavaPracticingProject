import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 200;
    static final int SCREEN_HEIGHT = 200;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100;
    int x[] = new int[GAME_UNITS];
    int y[] = new int[GAME_UNITS];
    int bodyParts = 2;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'D';
    boolean running = false;
    Timer timer;
    Random random;
    JFrame frame;

    GamePanel(JFrame frame){
        this.frame = frame;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdaptor());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running){//
            for (int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            for(int i=0; i<bodyParts; i++){
                if (i == 0){                    //Head
                    g.setColor(Color.YELLOW);
                    g.fillRect(x[i], y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else{                           //Body
                    g.setColor(new Color(45,180,0));
//                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("ZapfDingbats", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten))/2, g.getFont().getSize());
        }
        else{
//            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move(){
        for(int i=bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }

    public void AutoMode(){
        for(int i=bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //Auto strategy
        if (direction == 'D'){

        }
    }

    public void checkApple(){
        if ((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision(){
        //Head collides with body
        for(int i = bodyParts; i>0; i--){
            if((x[0] == x[i]) && (y[0]==y[i])){
                running = false;
            }
        }
        //Check if head touch border
        if (x[0] < 0 || x[0]>SCREEN_WIDTH || y[0]<0 || y[0]>SCREEN_HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        ((GameFrame) frame).gameOver(new GameOverPanel(applesEaten, SCREEN_WIDTH, SCREEN_HEIGHT, g, frame));
    }

    public void restart() {
        bodyParts = 6;
        direction = 'R';
        applesEaten = 0;
        for (int i = bodyParts; i >= 0; i--) {
            x[i] = 0;
            y[i] = 0;
        }
        startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }
//        else{
//            bodyParts = 6;
//            direction = 'D';
//            running = true;
//            x = new int[GAME_UNITS];
//            y = new int[GAME_UNITS];
//            timer.restart();
//        }
        repaint();
    }

    public class MyKeyAdaptor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                        break;
                    }
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                        break;
                    }
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                        break;
                    }
            }
        }
    }
}
