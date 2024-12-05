import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;

public class Play extends JPanel {


    final private Game game;
    RandomNumber randomNumber = new RandomNumber();
    ScoringSystem scoringSystem = new ScoringSystem();

    ScoreFiles scoreFiles = new ScoreFiles();

    public Play(Game game){
        this.game = game;

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);

        createGUI();
    }

    private void createGUI() {

        JLabel playScore, gameText, minMaxImage , mysteryNumber, statusImage, enterButton, continueButton, backButton;
        JTextField inputText;
        JPanel gridPanel;
        int random = randomNumber.generateNumber(); // generates a random number

        playScore = new JLabel("Score: " + scoreFiles.intScore("scores/current_score.txt") + "   Games: " + scoreFiles.intScore("scores/num_game.txt"));
        playScore.setFont(new Font("Arial", Font.BOLD, 18));
        playScore.setForeground(Color.WHITE);
        playScore.setBorder(new EmptyBorder(20,0,0,0));
        playScore.setAlignmentX(CENTER_ALIGNMENT);
        add(playScore);

        gameText = new JLabel(new ImageIcon("res/guess_number_label.png"));
        gameText.setBorder(new EmptyBorder(5,0,0,0));
        gameText.setAlignmentX(CENTER_ALIGNMENT);
        add(gameText);

        minMaxImage = new JLabel(new ImageIcon("res/min_max.png"));
        minMaxImage.setAlignmentX(CENTER_ALIGNMENT);
        add(minMaxImage);

        mysteryNumber = new JLabel("?");
        mysteryNumber.setIcon(new ImageIcon("res/mystery_square.png"));
        mysteryNumber.setHorizontalTextPosition(JLabel.CENTER);
        mysteryNumber.setFont(new Font("Arial", Font.BOLD, 120));
        mysteryNumber.setForeground(new Color(62,59,80));
        mysteryNumber.setAlignmentX(CENTER_ALIGNMENT);
        add(mysteryNumber);

        statusImage = new JLabel(new ImageIcon("res/input_number.png"));
        statusImage.setAlignmentX(CENTER_ALIGNMENT);
        statusImage.setBorder(BorderFactory.createEmptyBorder(-10, 0, 15, 0));
        add(statusImage);

        gridPanel = new JPanel();
        gridPanel.setMaximumSize(new Dimension(260, 50));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        gridPanel.setLayout(new GridLayout(0, 2));
        gridPanel.setOpaque(false);

        inputText = new JTextField();
        inputText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        inputText.setBackground(new Color(253,233,180));
        inputText.setForeground(new Color(62,59,80));
        inputText.setHorizontalAlignment(JTextField.CENTER);
        inputText.setFont(new Font("Arial", Font.BOLD, 28));
        gridPanel.add(inputText);

        enterButton = new JLabel(new ImageIcon("res/enter_button.png"));
        enterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gridPanel.add(enterButton);

        add(gridPanel);

        continueButton = new JLabel(new ImageIcon("res/continue_button.png"));
        continueButton.setAlignmentX(CENTER_ALIGNMENT);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.setVisible(false);
        continueGame(continueButton);
        add(continueButton);

        backButton = new JLabel(new ImageIcon("res/back_to_menu_button.png"));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        linkMenu(backButton);
        add(backButton);

        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeStatus(inputText, mysteryNumber, random, statusImage, gridPanel, continueButton, backButton);
            }
        });

        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus(inputText, mysteryNumber, random, statusImage, gridPanel, continueButton, backButton);
            }
        });
    }

    public void linkMenu(JLabel jLabel){
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Want to Stop the Game?", "Are you Sure", JOptionPane.YES_NO_OPTION);
                if(dialogResult == 0) {
                    scoreFiles.compareScore("scores/high_score.txt", "scores/current_score.txt", "scores/num_game.txt");
                    game.showView(new Menu(game));
                }
            }
        });
    }

    public void continueGame(JLabel jLabel){
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.showView(new Play(game));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon("res/background.jpg").getImage(), 0, 0, null);
    }

    private void changeStatus(JTextField input, JLabel mysterynum, int randnum, JLabel status, JPanel gridPanel, JLabel contButton, JLabel backButton){
        if (String.valueOf(randnum).equals(input.getText())) {
            status.setIcon(new ImageIcon("res/correct.png"));
            mysterynum.setText(input.getText());
            gridPanel.setVisible(false);
            contButton.setVisible(true);
            contButton.setBorder(new EmptyBorder(-15,0,0,0));
            backButton.setBorder(new EmptyBorder(-9,0,0,0));

            scoringSystem.scoreAttempt();
            scoreFiles.write("scores/current_score.txt", scoreFiles.intScore("scores/current_score.txt") + scoringSystem.getScore());
            scoreFiles.write("scores/num_game.txt", scoreFiles.intScore("scores/num_game.txt") + 1);

            sound_effect("res/correct.wav");
        } else {
            sound_effect("res/wrong.wav");
            try {
                int textToInt = Integer.parseInt(input.getText());
                if(textToInt > 50 || textToInt < 1) {
                    status.setIcon(new ImageIcon("res/out_of_range.png"));
                } else if (textToInt > randnum ){
                    status.setIcon(new ImageIcon("res/too_high.png"));
                } else if(textToInt < randnum){
                    status.setIcon(new ImageIcon("res/too_low.png"));
                }
            } catch (NumberFormatException ex) {

                status.setIcon(new ImageIcon("res/invalid_input.png"));
            }
        }
        input.setText("");
        scoringSystem.incrementAttempt();

    }

    //// Sound Effects Music
    public void sound_effect(String soundName) {
        try {
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(new File(soundName));
            Clip clip2 = AudioSystem.getClip();
            clip2.open(audioInputStream2);
            FloatControl gainControl =  (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            clip2.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
