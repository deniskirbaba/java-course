import java.io.Serializable;
import java.util.HashMap;
import java.util.InputMismatchException;

public class CountGreaterThanMpaaRating implements Serializable
{
    private int counter;
    MpaaRating mpaaRating;

    public boolean setMpaaRating(String strMpaaRating)
    {
        try {
            MpaaRating mpaaRating = null;

            switch (strMpaaRating)
            {
                case "G":
                    this.mpaaRating = MpaaRating.G;
                    return true;
                case "R":
                    this.mpaaRating = MpaaRating.R;
                    return true;
                case "PG":
                    this.mpaaRating = MpaaRating.PG;
                    return true;
                case "PG_13":
                    this.mpaaRating = MpaaRating.PG_13;
                    return true;
                default:
                    throw new InputMismatchException();
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("MPAA rating should be G, PG, PG_13 or R.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("MPAA rating should be G, PG, PG_13 or R.");
        }
        return false;
    }

    public MpaaRating getMpaaRating()
    {
        return this.mpaaRating;
    }

    public int getCounter()
    {
        return this.counter;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }
}
