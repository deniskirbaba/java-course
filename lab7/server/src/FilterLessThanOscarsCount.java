import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class FilterLessThanOscarsCount implements Serializable
{
    private long oscarsCount;
    private ArrayList<Movie> movieArrayList;

    public long getOscarsCount()
    {
        return oscarsCount;
    }

    public boolean setOscarsCount(String strOscarsCount)
    {
        try
        {
            this.oscarsCount = Long.parseLong(strOscarsCount);
            return true;
        }
        catch (NumberFormatException | InputMismatchException numberFormatException)
        {
            System.out.println("Number of oscars must be int.");
            return false;
        }
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList)
    {
        this.movieArrayList = movieArrayList;
    }

    public void print()
    {
        if (this.movieArrayList.isEmpty())
            System.out.println("There are no movies whose number's of Oscars is lower than " + this.oscarsCount + '.');
        else
            this.movieArrayList.forEach(System.out::println);
    }
}