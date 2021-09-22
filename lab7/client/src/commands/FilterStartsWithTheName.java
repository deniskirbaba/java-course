package commands;

import java.io.Serializable;
import java.util.ArrayList;
import data.*;

/**
 * @author Denis Kirbaba
 * @version 2.2
 * Class representing the command output of all objects whose name begins with some substring.
 */

public class FilterStartsWithTheName implements Serializable
{
    private String startingString;
    private ArrayList<Movie> movieArrayList;

    public void setStartingString(String startingString)
    {
        this.startingString = startingString;
    }

    public String getStartingString()
    {
        return this.startingString;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList)
    {
        this.movieArrayList = movieArrayList;
    }

    public void print()
    {
        if (this.movieArrayList.isEmpty())
            System.out.println("There are no movies whose name's starts with " + this.startingString + '.');
        else
            this.movieArrayList.forEach(System.out::println);
    }
}