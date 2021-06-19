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

public class Manager {
    /**
     * Collection for movie storage
     */
    private Stack<Movie> movies = new Stack<>();
    /**
     * Field for storage date
     */
    private final LocalDateTime initializationDate = LocalDateTime.now();
    /** Field for collection class type */
    private final Class<? extends Stack> collectionTypeClass = this.movies.getClass();
    /**
     * Field for file path
     */
    private String filePath;
    /**
     * Field for manual
     */
    protected HashMap<String, String> manual = new HashMap<>();
    /**
     * Field for checking the CSV file
     */
    String checkString = "id,name,coordinateX,coordinateY,creationDate,oscarsCount,goldenPalmCount,genre,mpaaRating,screenwriterName,screenwriterHeight,screenwriterWeight";

    /**
     * Constructor for making a manager
     *
     * @param filePath - path to file containing data in the CSV format
     */
    public Manager(String filePath)
    {
        this.filePath = filePath;
        this.checkFileForWriteRead(false);
        this.load();
        this.initializeManual();
    }

    /**
     * Method for checking a file for writing or reading
     *
     * @param writeFlag - flag for choosing between writing (true) and reading (false)
     */
    public void checkFileForWriteRead(boolean writeFlag)
    {
        boolean stop = false;

        while (!stop) {
            if (this.filePath == null || this.filePath.equals(""))
                this.filePath = this.getFilePath();

            File file = new File(this.filePath);

            if (!file.exists())
            {
                System.out.println("File at the specified path does not exist.");
                this.filePath = this.getFilePath();
                continue;
            }

            if (file.isDirectory())
            {
                System.out.println("The path to the directory is given, not to the file.");
                this.filePath = this.getFilePath();
                continue;
            }

            if (writeFlag) {
                if (!file.canWrite()) {
                    System.out.println("File cannot be write. Add permission to view the file or enter the path to a new file.");
                    this.filePath = this.getFilePath();
                    continue;
                }
            }

            if (!writeFlag) {
                if (!file.canRead()) {
                    System.out.println("File cannot be read. Add permission to view the file or enter the path to a new file.");
                    this.filePath = this.getFilePath();
                    continue;
                }
            }
            stop = true;
        }
    }

    /**
     * Method for getting path to file
     */
    private String getFilePath() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a new file path: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This value cannot be empty.");
                    continue;
                }
                return name;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            }
        }
    }

    /**
     * Method for manual initialization
     */
    private void initializeManual() {
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

    /**
     * Method for loading a data from file into the collection
     */
    public void load()
    {
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(this.filePath))) {

            ArrayList<String> contents = new ArrayList<>();

            int nextByte;
            StringBuilder s = new StringBuilder();

            while ((nextByte = bin.read()) != -1) {
                char nextChar = (char) nextByte;
                if (nextChar == '\n') {
                    contents.add(s.toString());
                    s = new StringBuilder();
                } else {
                    s.append(nextChar);
                }
            }
            contents.add(s.toString());

            for (int i = 0; i < contents.size(); i++) {
                if (contents.get(i).equals(""))
                    contents.remove(i);
            }

            for (int i = 0; i < contents.size(); i++) {
                if (i == 0) {
                    if (!contents.get(i).trim().equals(checkString)) {
                        System.out.println("Incorrect format of CSV data in file.");
                        this.movies.clear();
                    }
                } else {
                    String[] fields = new String[12];

                    String data = contents.get(i).trim();
                    int commaIndex = data.indexOf(',');
                    fields[0] = data.substring(0, commaIndex);
                    data = data.substring(commaIndex + 1).trim();

                    if (data.startsWith("\"")) {
                        data = data.substring(1).trim();
                        int doubleQuotesIndex = data.indexOf('\"');
                        fields[1] = data.substring(0, doubleQuotesIndex).trim();
                        data = data.substring(doubleQuotesIndex + 2).trim();
                    }

                    for (int j = 0; j < 7; j++) {
                        int comma = data.indexOf(',');
                        fields[j + 2] = data.substring(0, comma);
                        data = data.substring(comma + 1).trim();
                    }

                    if (data.startsWith("\"")) {
                        data = data.substring(1).trim();
                        int doubleQuotesIndex = data.indexOf('\"');
                        fields[9] = data.substring(0, doubleQuotesIndex).trim();
                        data = data.substring(doubleQuotesIndex + 2).trim();
                    }

                    int lastcomma = data.indexOf(',');
                    fields[10] = data.substring(0, lastcomma);
                    data = data.substring(lastcomma + 1).trim();

                    fields[11] = data;

                    if (Movie.checkMovie(fields)) {
                        Movie movie = new Movie(Integer.parseInt(fields[0]), fields[1], new Coordinates(Long.valueOf(fields[2]),
                                Long.parseLong(fields[3])), fields[4], Long.parseLong(fields[5]), Integer.valueOf(fields[6]),
                                getGenre(fields[7]), getMpaaRating(fields[8]), new Person(fields[9], Integer.parseInt(fields[10]),
                                Float.parseFloat(fields[11])));
                        movies.push(movie);
                    }
                }
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException exception) {
            System.out.println("The data in the file does not match the CSV format.");
            this.movies.clear();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found.");
            this.movies.clear();
        } catch (IOException ioException) {
            System.out.println("Exception while reading file.");
            this.movies.clear();
        }
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
        this.checkFileForWriteRead(true);

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(this.filePath))) {

            byte[] b = (checkString + '\n').getBytes();
            bos.write(b, 0, b.length);

            for (Movie movie : movies) {
                String result = "";
                result += movie.getCSVString() + '\n';
                byte[] out = result.getBytes();
                bos.write(out);
            }
            System.out.println("Collection data has been successfully saved to file.");
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found.");
        } catch (IOException ioException) {
            System.out.println("Error while writing to file.");
        }
    }

    /**
     * Method to get number of objects in th collection
     */
    public int getNumberOfObjectsInCollection() {
        int result = 0;

        if (movies.empty()) {
            return result;
        } else {
            Stack<Movie> stack = new Stack<>();
            while (!movies.empty()) {
                Movie movie = movies.pop();
                stack.push(movie);
                result += 1;
            }
            while (!stack.empty()) {
                Movie movie = stack.pop();
                movies.push(movie);
            }
        }
        return result;
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