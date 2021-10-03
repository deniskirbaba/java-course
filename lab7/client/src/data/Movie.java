package data;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Denis Kirbaba
 * @version 1.6
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

    public static boolean checkArguments(String sid, String name, String scoordinateX, String scoordinateY, String creationDate,
                                         String soscarsCount, String sgoldenPalmCount, String genre, String mpaaRating,
                                         String screenwriterName, String sscreenwriterHeight, String sscreenwriterWeight,
                                         String user)
    {
        try
        {
            int id = Integer.parseInt(sid);
            if (id < 1)
                return false;

            if (name.equals(""))
                return false;

            long coordinateX = Long.parseLong(scoordinateX);
            if (coordinateX > 414 || coordinateX < 1)
                return false;

            long coordinateY = Long.parseLong(scoordinateY);
            if (coordinateY > 211 || coordinateY < 1)
                return false;

            String[] time = creationDate.trim().split("\\s+");
            String hourMinute = time[0];
            String dayMonthYear = time[1];
            int hour = Integer.parseInt(hourMinute.split(":")[0]);
            int minute = Integer.parseInt(hourMinute.split(":")[1]);
            int day = Integer.parseInt(dayMonthYear.split("\\.")[0]);
            int month = Integer.parseInt(dayMonthYear.split("\\.")[1]);
            int year = Integer.parseInt(dayMonthYear.split("\\.")[2]);
            if (!(hour > -1 && hour < 24 && minute > -1 && minute < 60 && day > 0 && day < 32 && month > 0
                    && month < 13 && year > -1 && year < 2022))
                return false;

            long oscarsCount = Long.parseLong(soscarsCount);
            if (oscarsCount < 1)
                return false;

            int goldenPalmCount = Integer.parseInt(sgoldenPalmCount);
            if (goldenPalmCount < 1)
                return false;

            if (genre.equals("") || MovieGenre.stringToGenre(genre) == null)
                return false;

            if (mpaaRating.equals("") || MpaaRating.stringToMpaaRating(mpaaRating) == null)
                return false;

            if (screenwriterName.equals(""))
                return false;

            int screenwriterHeight = Integer.parseInt(sscreenwriterHeight);
            if (screenwriterHeight < 1)
                return false;

            float screenwriterWeight = Float.parseFloat(sscreenwriterWeight);
            if (screenwriterWeight <= 0)
                return false;

            Pattern pattern = Pattern.compile("^[A-Za-z]([A-Za-z0-9-_]*)");
            if (user.equals(""))
                return false;
            else
            {
                Matcher matcher = pattern.matcher(user);
                if (!(matcher.matches() && user.length() > 2))
                    return false;
            }

            return true;
        }
        catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
    }
}