import java.time.LocalDateTime;
import java.util.UUID; // to generate a unique id
import java.lang.Math;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, describing the objects stored in the collection
 */

public class Movie
{
    /** Field for storage an ID */
    private int id; // should be > 0, unique, generated automatically
    /** Field for name storage */
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
        UUID uuid = UUID.randomUUID();
        String str=""+uuid;
        this.id = Math.abs(str.hashCode());

        this.creationDate = LocalDateTime.now().toString();
        //LocalDateTime time = LocalDateTime.now();
        //this.creationDate = String.valueOf(time.getHour()) + ':' + String.valueOf(time.getHour()) + ' ' + String.valueOf(time.getDayOfMonth()) + '.' + String.valueOf(time.getMonthValue()) + '.' + String.valueOf(time.getYear());
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
    public boolean checkMovie() throws ArrayIndexOutOfBoundsException
    {
        try {
            if (this.coordinates.getX() < 415 && this.coordinates.getY() < 212 && this.oscarsCount > 0 && this.id > 0 && (this.name != null) && this.goldenPalmCount > 0 && (this.genre != null) && (this.mpaaRating != null) && (this.screenwriter.getName() != null) && (this.screenwriter.getHeight() > 0) && this.screenwriter.getWeight() > 0) {
                /*
                String[] time = this.creationDate.split(":");
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1].split(" ")[0]);
                int day = Integer.parseInt(time[1].split(" ")[1].split(".")[0]);
                int month = Integer.parseInt(time[1].split(" ")[1].split(".")[1]);
                int year = Integer.parseInt(time[1].split(" ")[1].split(".")[2]);
                if (hour > -1 && hour < 24 && minute > -1 && minute < 60 && day > 0 && day < 32 && month > 0 && month < 13 && year > -1 && year < 2022)
                    return true;
                else
                    return false;
                    r
                 */
                return true;
            } else
                return false;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException)
        {
            System.out.println("Incorrect object in the file.");
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
}