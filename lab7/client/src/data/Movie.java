package data;

import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 1.4
 * A class that represents a movie.
 */

public class Movie implements Serializable, Comparable<Movie>
{
    private int id;
    private String name;
    private Coordinates coordinates;
    private String creationDate;
    private long oscarsCount;
    private Integer goldenPalmCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person screenwriter;
    private String user;

    public Movie(int id, String name, Coordinates coordinates, String creationDate, long oscarsCount, Integer goldenPalmCount,
                 MovieGenre genre, MpaaRating mpaaRating, Person screenwriter, String user)
    {
        this.id =id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
        this.user = user;
    }

    public int getGoldenPalmCount()
    {
        return this.goldenPalmCount;
    }

    public String getName()
    {
        return this.name;
    }

    public long getOscarsCount()
    {
        return this.oscarsCount;
    }

    public MpaaRating getMpaaRating()
    {
        return this.mpaaRating;
    }

    public int getId()
    {
        return this.id;
    }

    public String getUser()
    {
        return this.user;
    }

    public int compareTo(Movie movie)
    {
        if ((this.getGoldenPalmCount() + this.getOscarsCount()) < (movie.getOscarsCount() + movie.getGoldenPalmCount()))
            return -1;
        else
            return 1;
    }

    @Override
    public String toString()
    {
        return "id:" + id + ", name:" + name + ", coordinates:" + coordinates.toString() + ", date:" + creationDate +
                ", Oscars:" + oscarsCount + ", Golden Palms:" + goldenPalmCount + ", genre:" + genre + ", MPAA rating:"
                + mpaaRating + ", screenwriter:" + screenwriter.toString() + ", user:" + this.user;
    }

    public static boolean checkArguments(String[] args)
    {
        try
        {
            if (args[0].equals(""))
                return false;

            long x = Long.parseLong(args[1]);
            if (x > 414 || x < 0)
                return false;

            long y = Long.parseLong(args[2]);
            if (y > 211 || y < 0)
                return false;

            long oscarsCount = Long.parseLong(args[3]);
            if (oscarsCount < 1)
                return false;

            int goldenPalmCount = Integer.parseInt(args[4]);
            if (goldenPalmCount < 1)
                return false;

            String genre = args[5];
            if (MovieGenre.stringToGenre(genre) == null)
                return false;

            String mpaaRating = args[6];
            if (MpaaRating.stringToMpaaRating(mpaaRating) == null)
                return false;

            if (args[7].equals(""))
                return false;

            int height = Integer.parseInt(args[8]);
            if (height <= 0)
                return false;

            float weight = Float.parseFloat(args[9]);
            if (weight <= 0)
                return false;

            return true;
        }
        catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
    }
}