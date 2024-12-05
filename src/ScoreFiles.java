import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScoreFiles {

    public String showScore(String filename){
        String score = "0";
        try{
            File text = new File(filename);
            Scanner scan = new Scanner(text);
            score = scan.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return score;
    }

    public String showGames(String filename){
        String attempt = "0";
        try{
            File text = new File(filename);
            Scanner scan = new Scanner(text);
            scan.nextLine();
            attempt = scan.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return attempt;
    }

    public int intScore(String filename){
        return Integer.parseInt(showScore(filename));
    }
    public int intGames(String filename){
        return Integer.parseInt(showGames(filename));
    }

    public void write(String filename, int score){
        try{
            FileWriter score_writer = new FileWriter(filename);
            score_writer.write(String.valueOf(score));
            score_writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeScoreAttempts(String filename, int score, int attemtps){
        try{
            FileWriter score_writer = new FileWriter(filename);
            score_writer.write(String.valueOf(score));
            score_writer.write("\n");
            score_writer.write(String.valueOf(attemtps));
            score_writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compareScore(String high_score, String current_score, String current_played_games){
        if (intScore(high_score) < intScore(current_score)){
            writeScoreAttempts(high_score, intScore(current_score), intScore(current_played_games));
        }
        else if(intScore(high_score) == intScore(current_score)){
            if (intGames(high_score) > intScore(current_played_games)){

                writeScoreAttempts(high_score, intScore(current_score), intScore(current_played_games));
            }
        }
    }
}
