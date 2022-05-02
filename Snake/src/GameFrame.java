import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    GamePanel gamePanel;
    private GOSingleAIPanel GOSAI;
    private SingleAIPanel singleAIPanel;
    private JPanel contentPane;
    CardLayout cardLayout = new CardLayout();
    private GameOverPanel GOPanel;
    private AStarAIPanel aStarAIPanel;
    private GOAStarPanel GOAS;

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    GameFrame(){
        gamePanel = new GamePanel(this);
        singleAIPanel = new SingleAIPanel(this, 200, 600);
        aStarAIPanel = new AStarAIPanel(this, 600, 600);
//        this.setLayout(cardLayout);
//        this.add(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setLayout(cardLayout);
        contentPane.add(gamePanel, "Top Panel");
//        contentPane.add(aStarAIPanel, "Top A* Game Panel");
        this.setResizable(false);
        this.setContentPane(contentPane);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void playAgain() {
        cardLayout.next(contentPane);
        gamePanel.restart();
        contentPane.remove(GOPanel);
        gamePanel.requestFocusInWindow();
    }

    public void gameOver(GameOverPanel GOPanel) {
        this.GOPanel = GOPanel;
        contentPane.add(GOPanel, "GameOver Panel");
        cardLayout.next(contentPane);
    }

    public void gameOverSingleAI(GOSingleAIPanel GOSAI) {
        this.GOSAI = GOSAI;
        contentPane.add(GOSAI, "GameOver Single AI Panel");
        cardLayout.next(contentPane);
    }

    public void playAgainSingleAI() {
        cardLayout.next(contentPane);
        singleAIPanel.restart();
        contentPane.remove(GOSAI);
        singleAIPanel.requestFocusInWindow();
    }

    public void aStarAI() {
        aStarAIPanel = new AStarAIPanel(this, SCREEN_WIDTH, SCREEN_HEIGHT);
        contentPane.add(aStarAIPanel, "A* AI Panel");
        cardLayout.next(contentPane);
//        contentPane.remove(contentPane);
        aStarAIPanel.requestFocusInWindow();
    }

    public void gameOverAStar(GOAStarPanel GOAS) {
        this.GOAS = GOAS;
        contentPane.add(GOAS, "GameOver A* Panel");
        cardLayout.next(contentPane);
    }

    public void playAgainAStar() {
        cardLayout.next(contentPane);
        aStarAIPanel.restart();
        contentPane.remove(GOAS);
        aStarAIPanel.requestFocusInWindow();
    }
}
