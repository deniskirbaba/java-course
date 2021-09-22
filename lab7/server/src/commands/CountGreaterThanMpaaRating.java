package commands;

import java.io.Serializable;
import java.util.InputMismatchException;
import data.*;

/**
 * @author Denis Kirbaba
 * @version 2.2
 * Class representing the command for counting the number of elements whose MPAA rating is greater than the given one.
 */

public class CountGreaterThanMpaaRating implements Serializable
{
    private int counter = 0;
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

    public void printResult()
    {
        if (this.counter == 0)
            System.out.println("There are no movies whose rating is higher than " + this.mpaaRating + '.');
        else
            System.out.println("There are " + counter + " movies whose rating is higher than " + mpaaRating + '.');
    }

    public MpaaRating getMpaaRating()
    {
        return this.mpaaRating;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }
}