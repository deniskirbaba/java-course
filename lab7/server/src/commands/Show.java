package commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;
import data.*;

public class Show implements Serializable
{
    private ArrayList<Movie> movies;

    public void initialize(ArrayList<Movie> movies)
    {
        this.movies = movies.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
    }

    public void print()
    {
        if (this.movies.isEmpty())
            System.out.println("The collection is empty.");
        else
            this.movies.forEach(System.out::println);
    }
}