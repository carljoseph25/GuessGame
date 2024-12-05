public class ScoringSystem {
    private int score, attempts;

    public ScoringSystem() {
        this.attempts = 1;
    }
    public int getScore() {
        return score;
    }
    public int getAttempts() {
        return attempts;
    }


    public void scoreAttempt(){
        if(this.attempts == 1)
            this.score += 10;
        else if(this.attempts == 2)
            this.score += 9;
        else if (this.attempts == 3)
            this.score += 5;
        else if (this.attempts == 4)
            this.score += 3;
        else
            this.score++;
    }

    public void incrementAttempt(){
        this.attempts++;
    }
}
