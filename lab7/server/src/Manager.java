import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, required for executing user commands and managing the collection
 */
public class Manager
{
    /** Collection for movie storage */
    private Stack<Movie> movies;

    /** Field for storage date */
    private final LocalDateTime initializationDate = LocalDateTime.now();

    /** Field for collection class type */
    private final Class<? extends Stack> collectionTypeClass;

    /** Field for manual */
    protected HashMap<String, String> manual = new HashMap<>();

     /** Constructor */
     public Manager(Stack<Movie> movies)
     {
        this.movies = movies;
        this.collectionTypeClass = this.movies.getClass();
        this.initializeManual();
     }

    /**
     * Method for manual initialization
     */
    private void initializeManual()
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

    public void add(Movie movie)
    {
        movie.setID();
        movies.push(movie);
    }

    /**
     * Method to get movie genre from user via file
     */
    public MovieGenre getGenre(String genre) {
        String category = genre.toUpperCase();
        switch (category) {
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
                return null;
        }
    }

    /**
     * Method to get movie MPAA rating from user via file
     */
    public MpaaRating getMpaaRating(String mpaaRating) {
        String category = mpaaRating.toUpperCase();
        switch (category) {
            case "G":
                return MpaaRating.G;
            case "PG":
                return MpaaRating.PG;
            case "PG_13":
                return MpaaRating.PG_13;
            case "R":
                return MpaaRating.R;
            default:
                return null;
        }
    }

    /**
     * Method to save collection in file
     */
    public void save()
    {

    }

    /**
     * Method to get number of objects in the collection
     */
    public int getNumberOfObjectsInCollection()
    {
        if (movies.empty())
            return 0;
        else
            return movies.size();
    }

    public String update(Movie movie, int id)
    {
        if (movies.empty())
        {
            return "The collection is empty.";
        }
        else
        {
            Stream<Movie> stream = this.movies.stream();
            Optional<Movie> optMovie = stream.filter(x -> x.getId() == id).findFirst();
            if (optMovie.isPresent())
            {
                int position = this.movies.search(optMovie.get());
                movie.setId(id);
                this.movies.set(this.movies.size() - position, movie);

                System.out.println();

                return "Object updated successfully.";
            }
            else
                return "The object with the given id does not exist.";
        }
    }

    /**
     * Method to remove object by ID
     */
    public String removeById(int id)
    {
        if (this.movies.empty())
        {
            return "The collection is empty.";
        }
        else
        {
            Stream<Movie> stream = this.movies.stream();
            Optional<Movie> movie = stream.filter(x -> x.getId() == id).findFirst();
            if (movie.isPresent())
            {
                int position = this.movies.search(movie.get());
                this.movies.remove(this.movies.size() - position);
                return "Object removed successfully.";
            }
            else
                return "The object with the given id does not exist.";
        }
    }

    /**
     * Method to clear the collection
     */
    public String clear()
    {
        if (movies.empty()) {
            return "The collection is already empty.";
        }
        while (!movies.empty()) {
            movies.clear();
        }
        return "The collection cleared.";
    }

    /**
     * Method to remove an object at the specified position
     */
    public String removeAt(int index)
    {
        if (this.movies.empty())
        {
            return "The collection is empty.";
        }
        else
        {
            if (this.movies.size() < index)
                return "The object with the given index does not exist.";
            else
            {
                ArrayList<Movie> arrayList = movies.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
                Movie removingMovie = arrayList.get(index - 1);
                this.removeById(removingMovie.getId());
                return "Object deleted successfully.";
            }
        }
    }

    /**
     * Method to print objects which MPAA rating's higher than the given one
     */
    public String countGreaterThanMpaaRating(MpaaRating mpaaRating)
    {
        HashMap<MpaaRating, Integer> rating = new HashMap<>();
        rating.put(MpaaRating.G, 0);
        rating.put(MpaaRating.PG, 1);
        rating.put(MpaaRating.PG_13, 2);
        rating.put(MpaaRating.R, 3);

            if (movies.empty()) {
                return "The collection is empty.";
            } else
            {
                Stream<Movie> stream = movies.stream();
                long counter = stream.map(Movie::getMpaaRating).filter(x -> rating.get(x) > rating.get(mpaaRating)).count();

                if (counter == 0)
                    return "There are no movies whose rating is higher than " + mpaaRating + '.';
                else
                    return "There are " + counter + " movies whose rating is higher than " + mpaaRating + '.';
            }
    }

    /**
     * Method to print objects which Oscars number is less than the given one
     */
    public ArrayList<Movie> filterLessThanOscars(long oscarsCount)
    {
        ArrayList<Movie> movieArrayList = new ArrayList<>();

         if (!movies.empty())
             movieArrayList = movies.stream().filter(x -> x.getOscars() < oscarsCount).sorted().collect(Collectors.toCollection(ArrayList::new));
         return movieArrayList;
    }

    /**
     * Method to print objects which name's staring with the given substring
     */
    public ArrayList<Movie> filterStartsWithTheName(String prefix)
        {
            ArrayList<Movie> movieArrayList = new ArrayList<>();

            if (!movies.empty())
                movieArrayList = movies.stream().filter(x -> x.getName().toLowerCase().startsWith(prefix)).sorted().collect(Collectors.toCollection(ArrayList::new));
            return movieArrayList;
        }

    public void removeGreater(Movie movie)
    {
        this.movies = this.movies.stream().filter(x -> x.compareTo(movie) < 0).collect(Collectors.toCollection(Stack::new));
    }

    /**
     * Getter for collection type class
     */
    public Class getCollectionTypeClass()
    {
        return this.collectionTypeClass;
    }

    public Stack<Movie> getMovies()
    {
        return this.movies;
    }

    /**
     * Getter for initialization date
     */
    public LocalDateTime getInitializationDate()
    {
        return this.initializationDate;
    }
    
    public boolean ifIdExists(long id)
    {
        long count = 0;

        if (!movies.empty())
            count = movies.stream().filter(x -> x.getId() == id).count();
        if (count>0)
            return true;
        else
            return false;
    }
}