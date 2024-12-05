import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JPanel {

    final private Game game;

    ScoreFiles scoreFiles = new ScoreFiles();

    public Menu(Game game){
        this.game = game;

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);

        createGUI();
    }

    public void createGUI(){

        JLabel logoImage, scoreLabel, scoreText, playButton, playText;

        logoImage = new JLabel(new ImageIcon("res/guess_the_number_logo.png"));
        logoImage.setBorder(new EmptyBorder(100, 0, 0, 0));
        logoImage.setAlignmentX(CENTER_ALIGNMENT);
        add(logoImage);

        scoreLabel = new JLabel(new ImageIcon("res/high_score_label.png"));
        scoreLabel.setBorder(new EmptyBorder(35, 0, 0, 0));
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(scoreLabel);

        scoreText = new JLabel(scoreFiles.showScore("scores/high_score.txt") + " points for " + scoreFiles.showGames("scores/high_score.txt") + " games");
        scoreText.setFont(new Font("Arial", Font.BOLD, 18));
        scoreText.setForeground(Color.WHITE);
        scoreText.setBorder(new EmptyBorder(10,0,0,0));
        scoreText.setAlignmentX(CENTER_ALIGNMENT);
        add(scoreText);

        playButton = new JLabel(new ImageIcon("res/play_button.png"));
        playButton.setBorder(new EmptyBorder(30, 0, 0, 0));
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        linkPlay(playButton);
        add(playButton);

        playText = new JLabel(new ImageIcon("res/click_play.png"));
        playText.setBorder(new EmptyBorder(20,0,0,0));
        playText.setAlignmentX(CENTER_ALIGNMENT);
        add(playText);
    }

    public void linkPlay(JLabel jLabel){
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Set the data inside to zero
                scoreFiles.write("scores/current_score.txt", 0);
                scoreFiles.write("scores/num_game.txt", 0);
                game.showView(new Play(game));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon("res/background.jpg").getImage(), 0, 0, null);
    }
}
