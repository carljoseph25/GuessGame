import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class  Game extends JFrame {

    private JPanel viewPanel;

    public Game() {

        viewPanel = new JPanel(new BorderLayout());

        this.setTitle("Guess the Number"); // Title
        this.setPreferredSize(new Dimension(350, 660));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(viewPanel, BorderLayout.CENTER);
        showView(new Menu(this));
        this.setVisible(true);
        this.pack();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        background_music();
    }


    public void showView(JPanel jPanel){
        viewPanel.removeAll();
        viewPanel.add(jPanel, BorderLayout.CENTER);
        viewPanel.revalidate();
        viewPanel.repaint();
    }


    public void background_music() {
        try {

            String soundName = "res/background_music.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl =  (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
