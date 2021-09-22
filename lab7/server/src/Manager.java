import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import data.*;

/**
 * @author Denis Kirbaba
 * @version 2.6
 * The class that stores the collection in memory performs actions on it.
 * Implemented singleton template, since the collection should only exist in one instance.
 */

public class Manager
{
    private ArrayList<Movie> movies = new ArrayList<>();
    private LocalDateTime initializationDate = LocalDateTime.now();
    private String collectionClassType = "ArrayList";
    protected HashMap<String, String> manual = new HashMap<>();
    private static Manager instance;

    private Manager()
    {
        this.manual.put("help", " - display manual for available commands");
        this.manual.put("info", " - print collection information");
        this.manual.put("show", " - print all elements of the collection");
        this.manual.put("add {element}", " - add new element in collection");
        this.manual.put("update id {element}", " - update the value of the collection by id");
        this.manual.put("remove_by_id id", " - remove an item from the collection by id");
        this.manual.put("clear", " - clear the collection");
        this.manual.put("save", " - save collection to the file");
        this.manual.put("execute_script file_name", " - read and execute the script from the specified file");
        this.manual.put("exit", " - end the program without saving to file");
        this.manual.put("remove_at index", " - remove the element at the given position");
        this.manual.put("remove_first", " - remove first element from collection");
        this.manual.put("remove_greater {element}", " - remove all items from the collection that are greater than the given one");
        this.manual.put("count_greater_than_mpaa_rating mpaaRating", " - print the number of elements, the value of the mpaaRating field of which is greater than the given one");
        this.manual.put("filter_starts_with_the_name name", " - display elements whose name field value begins with a given substring");
        this.manual.put("filter_less_than_oscars_count oscarsCount", " - display elements whose oscarsCount field value is less than the specified one");
    }

    public static Manager getInstance()
    {
        if (Manager.instance == null)
            Manager.instance = new Manager();
        return Manager.instance;
    }

    public String getCollectionTypeClass()
    {
        return this.collectionClassType;
    }

    public LocalDateTime getInitializationDate()
    {
        return this.initializationDate;
    }

    public int getNumberOfObjectsInCollection()
    {
        return this.movies.size();
    }

    public void add(Movie movie)
    {
        this.movies.add(movie);
    }

    public void clear()
    {
        this.movies.clear();
    }

    public ArrayList<Movie> getMovies()
    {
        return this.movies;
    }

    public long countGreaterThanMpaaRating(MpaaRating mpaaRating)
    {
            HashMap<MpaaRating, Integer> rating = new HashMap<>();
            rating.put(MpaaRating.G, 0);
            rating.put(MpaaRating.PG, 1);
            rating.put(MpaaRating.PG_13, 2);
            rating.put(MpaaRating.R, 3);

            if (movies.isEmpty())
            {
                return 0;
            }
            else
            {
                Stream<Movie> stream = movies.stream();
                long counter = stream.map(Movie::getMpaaRating).filter(x -> rating.get(x) > rating.get(mpaaRating)).count();
                return counter;
            }
    }

    public ArrayList<Movie> filterLessThanOscars(long oscarsCount)
    {
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        if (!movies.isEmpty())
            movieArrayList = movies.stream().filter(x -> x.getOscarsCount() < oscarsCount).sorted().collect(Collectors.toCollection(ArrayList::new));
        return movieArrayList;
    }

    public ArrayList<Movie> filterStartsWithTheName(String prefix)
    {
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        if (!movies.isEmpty())
            movieArrayList = movies.stream().filter(x -> x.getName().toLowerCase().startsWith(prefix)).sorted().collect(Collectors.toCollection(ArrayList::new));
        return movieArrayList;
    }

    public boolean ifIdExists(long id)
    {
        long count = 0;

        if (!movies.isEmpty())
            count = movies.stream().filter(x -> x.getId() == id).count();
        if (count>0)
            return true;
        else
            return false;
    }

    public boolean ifIdAvailable(long id, String login)
    {
        long count = 0;

        if (!movies.isEmpty())
            count = movies.stream().filter(x -> x.getId() == id && Objects.equals(x.getUser(), login)).count();
        if (count>0)
            return true;
        else
            return false;
    }
}