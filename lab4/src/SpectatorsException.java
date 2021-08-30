public class SpectatorsException extends Exception
{
    private int numberOfSpectators;
    public int GetNumberOfSpectators()
    {
        return numberOfSpectators;
    }
    public SpectatorsException(String message, int num)
    {
        super(message);
        numberOfSpectators = num;
    }
}
