package data;

import java.io.Serializable;

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
}