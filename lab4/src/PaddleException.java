public class PaddleException extends Exception
{
    private int numberOfPaddles;
    public int GetNumberOfPaddles()
    {
        return numberOfPaddles;
    }
    public PaddleException(String message, int num)
    {
        super(message);
        numberOfPaddles = num;
    }
}
