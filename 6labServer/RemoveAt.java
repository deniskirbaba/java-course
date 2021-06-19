import java.io.Serializable;
import java.util.InputMismatchException;

public class RemoveAt implements Serializable
{
    private int index;

    public boolean setIndex(String strIndex)
    {
        try
        {
            int index = Integer.parseInt(strIndex);
            if (index > 0)
            {
                this.index = index;
                return true;
            }
            else
            {
                System.out.println("Index must be > 0.");
                return false;
            }
        }
        catch (NumberFormatException | InputMismatchException inputMismatchException)
        {
            System.out.println("Index must be integer.");
        }
        return false;
    }

    public int getIndex()
    {
        return this.index;
    }
}