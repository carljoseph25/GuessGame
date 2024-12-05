public class RandomNumber {
    private final int min = 1, max = 50;

    // 2
    public int generateNumber(){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}
