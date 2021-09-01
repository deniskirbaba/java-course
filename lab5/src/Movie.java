import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, describing the objects stored in the collection
 */

public class Movie
{
    /** Field for storage an ID */
    private int id; // should be > 0, unique, generated automatically
    /** Field for storage ID values */
    private static ArrayList<Integer> idList = new ArrayList<Integer>();
    /** Field for name */
    private String name; // can't be null or empty
    /** Field for storage the coordinates */
    private Coordinates coordinates; // can't be null
    /** Field for storage the creation date */
    private String creationDate; // can't be null, should be generated automatically
    /** Field for storage the number of Oscars */
    private long oscarsCount; // must be > 0
    /** Field for storage the number of Golden Palms */
    private Integer goldenPalmCount; // can't be null, must be > 0
    /** Field for storage movie genre */
    private MovieGenre genre; // can't be null
    /** Field for storage the movie MPAA rating */
    private MpaaRating mpaaRating; // can't be null
    /** Field for storage the screenwriter's info */
    private Person screenwriter;

    /**
     * Constructor for making a movie object
     */
    public Movie(String name, Coordinates coordinates, long oscarsCount, Integer goldenPalmCount, MovieGenre genre, MpaaRating mpaaRating, Person screenwriter)
    {
        this.id = this.setID();
        LocalDateTime time = LocalDateTime.now();
        this.creationDate = String.valueOf(time.getHour()) + ':' + String.valueOf(time.getHour()) + ' ' + String.valueOf(time.getDayOfMonth()) + '.' + String.valueOf(time.getMonthValue()) + '.' + String.valueOf(time.getYear());
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
    }

    /**
     * Constructor for making a movie object
     */
    public Movie(int id, String name, Coordinates coordinates, String creationDate, long oscarsCount, Integer goldenPalmCount, MovieGenre genre, MpaaRating mpaaRating, Person screenwriter)
    {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
    }

    /**
     * Method to check the object for correct field values
     */
    public static boolean checkMovie(String[] fields) throws ArrayIndexOutOfBoundsException
    {
        try
        {
            Movie movie = new Movie(Integer.parseInt(fields[0]), fields[1], new Coordinates(Long.valueOf(fields[2]),
                    Long.parseLong(fields[3])), fields[4], Long.parseLong(fields[5]), Integer.valueOf(fields[6]),
                    Movie.getGenre(fields[7]), Movie.getMpaaRating(fields[8]), new Person(fields[9], Integer.parseInt(fields[10]),
                    Float.parseFloat(fields[11])));
            if ((movie.coordinates.getX() < 415) && (movie.coordinates.getY() < 212) && (movie.oscarsCount > 0) && (movie.id > 0) &&
                    (movie.name != null) && (movie.goldenPalmCount > 0) && (movie.genre != null) && (movie.mpaaRating != null) &&
                    (movie.screenwriter.getName() != null) && (movie.screenwriter.getHeight() > 0) && (movie.screenwriter.getWeight() > 0) &&
                    (!movie.idList.contains(movie.id)))
            {
                String[] time = movie.creationDate.trim().split("\\s+");
                String hourMinute = time[0];
                String dayMonthYear = time[1];
                int hour = Integer.parseInt(hourMinute.split(":")[0]);
                int minute = Integer.parseInt(hourMinute.split(":")[1]);
                int day = Integer.parseInt(dayMonthYear.split("\\.")[0]);
                int month = Integer.parseInt(dayMonthYear.split("\\.")[1]);
                int year = Integer.parseInt(dayMonthYear.split("\\.")[2]);
                if (hour > -1 && hour < 24 && minute > -1 && minute < 60 && day > 0 && day < 32 && month > 0
                        && month < 13 && year > -1 && year < 2022)
                {
                    Movie.idList.add(movie.id);
                    return true;
                }
                else
                    return false;
            }
            else
                return false;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException)
        {
            return false;
        }
        catch (NumberFormatException numberFormatException)
        {
            return false;
        }
    }

    /**
     * Method toString
     */
    @Override
    public String toString()
    {
        return "id:" + id + ", name:" + name + ", coordinates:" + coordinates.toString() + ", date:" + creationDate + ", Oscars:" + oscarsCount + ", Golden Palms:" + goldenPalmCount + ", genre:" + genre + ", MPAA rating:" + mpaaRating + ", screenwriter:" + screenwriter.toString();
    }

    /**
     * Method to convert the object into the CSV format
     */
    public String getCSVString()
    {
        String result = "";
        result += Integer.toString(this.id) + ",\"" + name + "\"," + coordinates.toCSV() + "," + creationDate + "," + oscarsCount + "," + goldenPalmCount + "," + genre + "," + mpaaRating + "," + screenwriter.toCSV();

        return result;
    }

    /**
     * Getters
     */
    public int getId()
    {
        return this.id;
    }
    public String getName()
    {
        return this.name;
    }
    public String getCoordinates()
    {
        return this.coordinates.toString();
    }
    public long getOscars()
    {
        return this.oscarsCount;
    }
    public Integer getGoldenPalms()
    {
        return this.goldenPalmCount;
    }
    public MovieGenre getGenre()
    {
        return this.genre;
    }
    public MpaaRating getMpaaRating()
    {
        return this.mpaaRating;
    }
    public Person getScreenwriter()
    {
        return this.screenwriter;
    }

    /**
     * Setters
     */
    public void setName(String name)
    {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }
    public void setOscars(long oscars)
    {
        this.oscarsCount = oscars;
    }
    public void setGoldenPalms(Integer goldenPalms)
    {
        this.goldenPalmCount = goldenPalms;
    }
    public void setGenre(MovieGenre genre)
    {
        this.genre = genre;
    }
    public void setMpaaRating(MpaaRating mpaaRating)
    {
        this.mpaaRating = mpaaRating;
    }
    public void setScreenwriter(Person screenwriter)
    {
        this.screenwriter = screenwriter;
    }

    public int setID()
    {
        int result = 1;

        if (this.idList.isEmpty())
        {
            this.idList.add(result);
            return result;
        }
        else
        {
            for (int i = 0; i < this.idList.size(); i++)
            {
                if (this.idList.contains(result))
                    result += 1;
                else
                {
                    this.idList.add(result);
                    return result;
                }
            }
            this.idList.add(result);
            return result;
        }
    }

    public static MovieGenre getGenre(String genre)
    {
        try
        {
            String category = genre.toUpperCase();
            switch (category)
            {
                case "WESTERN":
                    return MovieGenre.WESTERN;
                case "COMEDY":
                    return MovieGenre.COMEDY;
                case "MUSICAL":
                    return MovieGenre.MUSICAL;
                case "ADVENTURE":
                    return MovieGenre.ADVENTURE;
                case "THRILLER":
                    return MovieGenre.THRILLER;
                default:
                    break;
            }
            System.out.println("Incorrect format of CSV data in file.");
            System.exit(1);
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully. ");
            System.exit(1);
        }
        return null;
    }

    public static MpaaRating getMpaaRating(String mpaaRating)
    {
        try
        {
            String category = mpaaRating.toUpperCase();
            switch (category)
            {
                case "G":
                    return MpaaRating.G;
                case "PG":
                    return MpaaRating.PG;
                case "PG_13":
                    return MpaaRating.PG_13;
                case "R":
                    return MpaaRating.R;
                default:
                    break;
            }
            System.out.println("Incorrect format of CSV data in file.");
            System.exit(1);
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully. ");
            System.exit(1);
        }
        return null;
    }
}