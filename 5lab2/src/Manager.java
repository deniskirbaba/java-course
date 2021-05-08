import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, required for executing user commands and managing the collection
 */

public class Manager
{
    /** Collection for movie storage */
    private Stack<Movie> movies = new Stack<>();
    /** Field for storage date */
    private String initializationDate = LocalDateTime.now().toString();
    /** Field for file */
    private File file;
    /** Field for manual */
    protected HashMap<String, String> manual = new HashMap<>();
    /** Field for cheking the CSV file */
    String checkString = "id,name,coordinateX,coordinateY,creationDate,oscarsCount,goldenPalmCount,genre,mpaaRating,screenwriterName,screenwriterHeight,screenwriterWeight";

    /**
     * Constructor for making a manager
     * @param filePath - path to file containing data in the CSV format
     */
    public Manager(String filePath) throws IOException
    {
        this.checkFileForRead(filePath);
        this.file = new File(filePath);
        this.load();
        this.initializeManual();
    }

    /**
     * Method for checking a file for reading
     * @param filePath - path to file containing data in the CSV format
     */
    public void checkFileForRead(String filePath) throws IOException
    {
        try
        {
            if (filePath == null)
                throw new FileNotFoundException();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File path is null.");
            System.exit(1);
        }

        File file = new File(filePath);
        try
        {
            if (!file.exists() == true)
                throw new FileNotFoundException();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File at the specified path does not exist.");
            System.exit(1);
        }

        try
        {
            if (!file.canRead())
                throw new SecurityException();
        }
        catch (SecurityException ex)
        {
            System.out.println("File cannot be read.");
            System.exit(1);
        }
    }

    /**
     * Method for checking a file for writing
     * @param filePath - path to file containing data in the CSV format
     */
    public void checkFileForWrite(String filePath) throws IOException
    {
        try
        {
            if (filePath == null)
                throw new FileNotFoundException();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File path is null.");
            System.exit(1);
        }

        File file = new File(filePath);
        try
        {
            if (!file.exists() == true)
                throw new FileNotFoundException();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File at the specified path does not exist.");
            System.exit(1);
        }

        try
        {
            if (!file.canWrite())
                throw new SecurityException();
        }
        catch (SecurityException ex)
        {
            System.out.println("File cannot be written.");
            System.exit(1);
        }
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

    /**
     * Method for loading a data from file into the collection
     */
    public void load()
    {
        BufferedInputStream bin = null;

        try {
            bin = new BufferedInputStream(new FileInputStream(this.file));

            ArrayList<String> contents = new ArrayList<>();

            int nextByte = 0;
            String s = "";

            while ((nextByte = bin.read()) != -1) {
                char nextChar = (char) nextByte;
                if (nextChar == '\n')
                {
                    contents.add(s);
                    s = "";
                }
                else
                {
                    s += nextChar;
                }
            }
            contents.add(s);

            for (int i = 0; i < contents.size(); i++)
            {
                if (contents.get(i).equals(""))
                    contents.remove(i);
            }

            for (int i = 0; i < contents.size(); i++) {
                if (i == 0)
                {
                    if (!contents.get(i).equals(checkString))
                    {
                        System.out.println("Incorrect format of CSV data in file.");
                        System.exit(1);
                    }
                }
                else
                {
                    String fields[] = new String[12];

                    String data = contents.get(i).trim();
                    int commaIndex = data.indexOf(',');
                    fields[0] = data.substring(0, commaIndex);
                    data = data.substring(commaIndex + 1).trim();

                    if (data.startsWith("\""))
                    {
                        data = data.substring(1).trim();
                        int doubleQuotesIndex = data.indexOf('\"');
                        fields[1] = data.substring(0, doubleQuotesIndex).trim();
                        data = data.substring(doubleQuotesIndex + 2).trim();
                    }

                    for (int j = 0; j < 7; j++)
                    {
                        int comma = data.indexOf(',');
                        fields[j + 2] = data.substring(0, comma);
                        data = data.substring(comma + 1).trim();
                    }

                    if (data.startsWith("\""))
                    {
                        data = data.substring(1).trim();
                        int doubleQuotesIndex = data.indexOf('\"');
                        fields[9] = data.substring(0, doubleQuotesIndex).trim();
                        data = data.substring(doubleQuotesIndex + 2).trim();
                    }

                    int lastcomma = data.indexOf(',');
                    fields[10] = data.substring(0, lastcomma);
                    data = data.substring(lastcomma + 1).trim();

                    fields[11] = data;

                    Movie movie = new Movie(Integer.parseInt(fields[0]), fields[1], new Coordinates(Long.valueOf(fields[2]), Long.parseLong(fields[3])), fields[4], Long.parseLong(fields[5]), Integer.valueOf(fields[6]), getGenre(fields[7]), getMpaaRating(fields[8]), new Person(fields[9], Integer.parseInt(fields[10]), Float.parseFloat(fields[11])));
                    if (movie.checkMovie())
                        movies.push(movie);
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("The data in the file does not match the CSV format.");
            System.exit(1);
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException)
        {
            System.out.println("The data in the file does not match the CSV format.");
            System.exit(1);
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            System.out.println("File not found.");
        }
        catch (IOException ioException)
        {
            System.out.println("Exception while reading file.");
        }
        finally
        {
            try
            {
                if (bin != null)
                {
                    bin.close();
                }
            }
            catch (Exception e)
            {
                System.out.println("Error while closing streams" + e);
            }
        }
    }

    /**
     * Method to display collection objects
     */
    public void show()
    {
        if (movies.empty())
        {
            System.out.println("Collection is empty.");
        }
        else
        {
            Stack<Movie> stack = new Stack<Movie>();
            while (!movies.empty())
            {
                Movie movie = movies.pop();
                stack.push(movie);
                System.out.println(movie.toString());
            }
            while (!stack.empty()) {
                Movie movie = stack.pop();
                movies.push(movie);
            }
        }
    }

    /**
     * Method to display help reference
     */
    public void help()
    {
        for (Map.Entry<String, String> entry : manual.entrySet())
        {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }

    /**
     * Method to add an object in collection via command line
     */
    public void add()
    {
        Movie movie = new Movie(getName(), getCoordinates(), getOscarsCount(), getGoldenPalmCount(), getGenre(), getMpaaRating(), getScreenwriter());
        movies.push(movie);
        System.out.println("Element added successfully.");
    }

    /**
     * Method to add an object in collection via file
     */
    public void add(String[] args)
    {
        Movie movie = new Movie(args[0], new Coordinates(Long.valueOf(args[1]), Long.parseLong(args[2])), Long.parseLong(args[3]),Integer.valueOf(args[4]), getGenre(args[5]), getMpaaRating(args[6]), new Person(args[7], Integer.parseInt(args[8]), Float.parseFloat(args[9])));
        movies.push(movie);
    }

    /**
     * Method to get a movie name from user
     */
    public String getName()
    {
        while (true)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name: ");
                String name = scanner.nextLine().trim();
                if (name.equals(""))
                {
                    System.out.println("This value cannot be empty.");
                    continue;
                }
                return name;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be string.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get X coordinate from user
     */
    public Long getX()
    {
        for (; ; )
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter X coordinate (max value is 414): ");
                Long x = scanner.nextLong();
                if (x.equals(""))
                {
                    System.out.println("This value cannot be empty.");
                    continue;
                }
                if (x > 414){
                    System.out.println("Max value is 414.");
                    continue;
                }
                return x;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be integer.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get Y coordinate from user
     */
    public long getY()
    {
        for (; ; )
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter Y coordinate (max value is 211): ");
                long y = scanner.nextInt();
                if (y > 211)
                {
                    System.out.println("Max value is 211.");
                    continue;
                }
                return y;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be integer.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get both (X,Y) coordinates from user
     */
    public Coordinates getCoordinates()
    {
        return new Coordinates(getX(), getY());
    }

    /**
     * Method to get the number of Oscars from user
     */
    public long getOscarsCount()
    {
        for (; ; )
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter number of oscars: ");
                long oscars = scanner.nextLong();
                if (oscars < 1)
                {
                    System.out.println("Value should be > 0.");
                    continue;
                }
                return oscars;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be integer.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get the number of Golden Palms from user
     */
    public Integer getGoldenPalmCount()
    {
        for (; ; )
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter number of golden palms: ");
                Integer goldenpalms = scanner.nextInt();
                if (goldenpalms.equals(""))
                {
                    System.out.println("This value cannot be empty.");
                    continue;
                }
                if (goldenpalms < 1)
                {
                    System.out.println("Value should be > 0.");
                    continue;
                }
                return goldenpalms;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be integer.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get movie genre from user via file
     */
    public MovieGenre getGenre(String genre)
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

    /**
     * Method to get movie genre from user via command line
     */
    public MovieGenre getGenre()
    {
        while (true)
        {
            try {
                System.out.println("Movie genres: western, comedy, musical, adventure, thriller.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter genre: ");
                String category = scanner.nextLine().toUpperCase();
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
                System.out.println("You should to enter western, comedy, musical, adventure, thriller.");
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be a words (western, comedy, musical, adventure, thriller).");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get movie MPAA rating from user via file
     */
    public MpaaRating getMpaaRating(String mpaaRating)
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

    /**
     * Method to get movie MPAA rating from user via command line
     */
    public MpaaRating getMpaaRating()
    {
        for ( ; ; )
        {
            try {
                System.out.println("MPAA ratings: G, PG, PG_13, R.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter MPAA rating: ");
                String category = scanner.nextLine().toUpperCase();
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
                System.out.println("You should to enter G, PG, PG_13, R.");
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be a words (G, PG, PG_13, R).");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get movie screenwriter's info
     */
    public Person getScreenwriter()
    {
        return new Person(getScreenwriterName(), getScreenwriterHeight(), getScreenwriterWeight());
    }

    /**
     * Method to get screenwriter's name
     */
    public String getScreenwriterName()
    {
        for (; ; )
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter screenwriter name: ");
                String name = scanner.nextLine();
                if (name.equals("")) {
                    System.out.println("This value cannot be empty.");
                    continue;
                }
                return name;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be string.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get screenwriter's height
     */
    public int getScreenwriterHeight()
    {
        for (; ; )
        {
            try
            {
                System.out.print("Enter screenwriter height: ");
                Scanner scanner = new Scanner(System.in);
                int height = scanner.nextInt();
                if (height <= 0) {
                    System.out.println("This value must be greater than 0.");
                    continue;
                }
                return height;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be integer.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to get screenwriter's weight
     */
    public float getScreenwriterWeight()
    {
        for (; ; )
        {
            try
            {
                System.out.print("Enter screenwriter weight: ");
                Scanner scanner = new Scanner(System.in);
                float weight = scanner.nextFloat();
                if (weight <= 0)
                {
                    System.out.println("This value must be greater than 0.");
                    continue;
                }
                return weight;
            }
            catch (InputMismatchException inputMismatchException)
            {
                System.out.println("This value must be float.");
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method to save collection in file
     */
    public void save() throws IOException
    {
        this.checkFileForWrite(this.file.getPath());
        BufferedOutputStream bout = null;

        try
        {
            bout = new BufferedOutputStream(new FileOutputStream(this.file));

            byte[] b = (checkString + '\n').getBytes();
            bout.write(b, 0, b.length);

            for (Movie movie:movies)
            {
                String result = "";
                result += movie.getCSVString() + '\n';
                byte[] out = result.getBytes();
                bout.write(out);
            }
            System.out.println("Collection data has been successfully saved to file.");
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println("File not found " + fnfe);
        }
        catch (IOException ioe)
        {
            System.out.println("Error while writing to file " + ioe);
        }
        finally
        {
            try
            {
                if (bout != null)
                {
                    bout.flush();
                    bout.close();
                }
            }
            catch (Exception e)
            {
                System.out.println("Error while closing streams " + e);
            }
        }
    }

    /**
     * Method to get number of objects in th collection
     */
    public int getNumberOfObjectsInCollection()
    {
        int result = 0;

        if (movies.empty())
        {
            return result;
        }
        else
        {
            Stack<Movie> stack = new Stack<Movie>();
            while (!movies.empty())
            {
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

    /**
     * Method to show collection's info
     */
    public void info()
    {
        System.out.println("Collection type: " + movies.getClass());
        System.out.println("Collection initialization date: " + this.initializationDate);
        System.out.println("The number of objects in the collection: " + Integer.toString(this.getNumberOfObjectsInCollection()));
    }

    /**
     * Method to update the object by id via command line
     */
    public void update(String strId)
    {
        try
        {
            int id = Integer.parseInt(strId);

            if (movies.empty())
            {
                System.out.println("The collection is empty.");
            }
            else
            {
                boolean find = false;

                Stack<Movie> stack = new Stack<Movie>();
                while (!movies.empty() && !find)
                {
                    Movie movie = movies.pop();
                    if (movie.getId() == id)
                    {
                        find = true;
                        this.update(movie);
                        movies.push(movie);
                    } else
                        stack.push(movie);
                }
                if (!find)
                    System.out.println("The object with the given id does not exist.");
                while (!stack.empty())
                {
                    Movie movie = stack.pop();
                    movies.push(movie);
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("ID must be integer.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("ID must be integer.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(1);
        }
    }

    /**
     * Method to update the object by id via file
     */
    public void update(String strId, String[] args)
    {
        try
        {
            int id = Integer.parseInt(strId);

            if (movies.empty())
            {
                System.out.println("The collection is empty.");
            }
            else
            {
                boolean find = false;

                Stack<Movie> stack = new Stack<Movie>();
                while (!movies.empty() && !find)
                {
                    Movie movie = movies.pop();
                    if (movie.getId() == id)
                    {
                        find = true;
                        this.update(movie, args);
                        movies.push(movie);
                    } else
                        stack.push(movie);
                }
                if (!find)
                    System.out.println("The object with the given id does not exist.");
                while (!stack.empty())
                {
                    Movie movie = stack.pop();
                    movies.push(movie);
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("ID must be integer.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("ID must be integer.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(1);
        }
    }

    /**
     * Method to update the object by id via file
     */
    public void update(Movie movie, String[] args)
    {
        movie.setName(args[0]);

        movie.setCoordinates(new Coordinates(Long.valueOf(args[1]), Long.parseLong(args[2])));

        movie.setOscars(Long.parseLong(args[3]));

        movie.setGoldenPalms(Integer.valueOf(args[4]));

        movie.setGenre(getGenre(args[5]));

        movie.setMpaaRating(getMpaaRating(args[6]));

        movie.setScreenwriter(new Person(args[7], Integer.parseInt(args[8]), Float.parseFloat(args[9])));
    }

    /**
     * Method to update the object by id via command line
     */
    public void update(Movie movie)
    {
        System.out.println("Previous film name: " + movie.getName());
        movie.setName(this.getName());

        System.out.println("Previous coordinates: " + movie.getCoordinates());
        movie.setCoordinates(this.getCoordinates());

        System.out.println("Previous number of Oscars: " + String.valueOf(movie.getOscars()));
        movie.setOscars(this.getOscarsCount());

        System.out.println("Previous number of Golden Palms: " + String.valueOf(movie.getGoldenPalms()));
        movie.setGoldenPalms(this.getGoldenPalmCount());

        System.out.println("Previous genre: " + movie.getGenre());
        movie.setGenre(this.getGenre());

        System.out.println("Previous MPAA rating: " + movie.getMpaaRating());
        movie.setMpaaRating(this.getMpaaRating());

        System.out.println("Previous screenwriter's info: " + movie.getScreenwriter().toString());
        movie.setScreenwriter(this.getScreenwriter());
    }

    /**
     * Method to remove object by ID
     */
    public void remove_by_id(String strId)
    {
        try
        {
            int id = Integer.parseInt(strId);

            if (movies.empty())
            {
                System.out.println("The collection is empty.");
            }
            else
            {
                boolean find = false;

                Stack<Movie> stack = new Stack<Movie>();
                while (!movies.empty() && !find)
                {
                    Movie movie = movies.pop();
                    if (movie.getId() == id)
                    {
                        find = true;
                    }
                    else
                        stack.push(movie);
                }
                if (!find)
                    System.out.println("The object with the given id does not exist.");
                else
                    System.out.println("The object has been deleted.");
                while (!stack.empty())
                {
                    Movie movie = stack.pop();
                    movies.push(movie);
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("ID must be integer.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("ID must be integer.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(1);
        }
    }

    /**
     * Method to clear the collection
     */
    public void clear()
    {
        if (movies.empty())
        {
            System.out.println("The collection is already empty.");
        }
        while (!movies.empty())
        {
            movies.pop();
        }
        System.out.println("The collection cleared.");
    }

    /**
     * Method to remove an object at the specified position
     */
    public void remove_at(String strIndex)
    {
        try
        {
            int index = Integer.parseInt(strIndex);

            if (movies.empty())
            {
                System.out.println("The collection is empty.");
            }
            else
            {
                boolean find = false;
                int counter = 0;

                Stack<Movie> stack = new Stack<Movie>();
                while (!movies.empty() && !find)
                {
                    Movie movie = movies.pop();
                    if (counter == index)
                    {
                        find = true;
                    }
                    else
                        stack.push(movie);
                }
                if (!find)
                    System.out.println("The object with the given index does not exist.");
                else
                    System.out.println("The object has been deleted.");
                while (!stack.empty())
                {
                    Movie movie = stack.pop();
                    movies.push(movie);
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("Index must be integer.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("Index must be integer.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(1);
        }
    }

    /**
     * Method to remove the first object in collection
     */
    public void remove_first()
    {
        if (movies.empty())
        {
            System.out.println("The collection is empty.");
        }
        else
        {
            this.movies.pop();
            System.out.println("The first object has been deleted.");
        }
    }

    /**
     * Method to print objects which MPAA rating's higher than the given one
     */
    public void count_greater_than_mpaa_rating(String strMpaaRating)
    {
        HashMap<MpaaRating, Integer> rating = new HashMap<>();
        rating.put(MpaaRating.G, 0);
        rating.put(MpaaRating.PG, 1);
        rating.put(MpaaRating.PG_13, 2);
        rating.put(MpaaRating.R, 3);

        try
        {
            MpaaRating mpaaRating = null;

            switch (strMpaaRating)
            {
                case "G":
                    mpaaRating = MpaaRating.G;
                    break;
                case "R":
                    mpaaRating = MpaaRating.R;
                    break;
                case "PG":
                    mpaaRating = MpaaRating.PG;
                    break;
                case "PG_13":
                    mpaaRating = MpaaRating.PG_13;
                    break;
                default:
                    throw new InputMismatchException();
            }

            if (movies.empty())
            {
                System.out.println("The collection is empty.");
            }
            else
            {
                Stack<Movie> stack = new Stack<Movie>();
                boolean find = false;

                while (!movies.empty())
                {
                    Movie movie = movies.pop();
                    if (rating.get(movie.getMpaaRating()) > rating.get(mpaaRating))
                    {
                        find = true;
                        System.out.println(movie.toString());
                        stack.push(movie);
                    }
                    else
                        stack.push(movie);
                }
                if (!find)
                    System.out.println("There are no movies whose rating is higher than " + mpaaRating + '.');
                while (!stack.empty())
                {
                    Movie movie = stack.pop();
                    movies.push(movie);
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("MPAA rating should be G, PG, PG_13 or R.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("MPAA rating should be G, PG, PG_13 or R.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(1);
        }
    }

    /**
     * Method to print objects which Oscars number is higher than the given one
     */
    public void filterLessThanOscars(String strOscars)
    {
        try
        {
            long oscars = Long.parseLong(strOscars);

            if (movies.empty())
            {
                System.out.println("The collection is empty.");
            }
            else
            {
                boolean find = false;

                Stack<Movie> stack = new Stack<Movie>();
                while (!movies.empty())
                {
                    Movie movie = movies.pop();
                    if (movie.getOscars() < oscars)
                    {
                        find = true;
                        System.out.println(movie);
                        stack.push(movie);
                    }
                    else
                        stack.push(movie);
                }
                if (!find)
                    System.out.println("There are no movies whose number's of Oscars is lower than " + strOscars + '.');
                while (!stack.empty())
                {
                    Movie movie = stack.pop();
                    movies.push(movie);
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("Number of Oscars must be long.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("Number of Oscars must be long.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(1);
        }
    }

    /**
     * Method to print objects which name's staring with the given substring
     */
    public void filterStartsWithTheName(String prefix)
    {
        if (movies.empty())
        {
            System.out.println("The collection is empty.");
        }
        else
        {
            boolean find = false;

            Stack<Movie> stack = new Stack<Movie>();
            while (!movies.empty())
            {
                Movie movie = movies.pop();
                if (movie.getName().toLowerCase().startsWith(prefix))
                {
                    find = true;
                    System.out.println(movie);
                    stack.push(movie);
                }
                else
                    stack.push(movie);
            }
            if (!find)
                System.out.println("There are no movies whose name's starts with " + prefix + '.');
            while (!stack.empty())
            {
                Movie movie = stack.pop();
                movies.push(movie);
            }
        }
    }

    /**
     * Method to remove objects from he collection whose greater than the given one via file
     */
    public void removeGreater(String[] args)
    {
        if (movies.empty())
        {
            System.out.println("The collection is empty.");
        }
        else
        {
            Movie userMovie = new Movie(args[0], new Coordinates(Long.valueOf(args[1]), Long.parseLong(args[2])), Long.parseLong(args[3]),Integer.valueOf(args[4]), getGenre(args[5]), getMpaaRating(args[6]), new Person(args[7], Integer.parseInt(args[8]), Float.parseFloat(args[9])));
            long score = userMovie.getOscars() + userMovie.getGoldenPalms();
            boolean find = false;

            Stack<Movie> stack = new Stack<Movie>();
            while (!movies.empty())
            {
                Movie movie = movies.pop();
                if ((movie.getOscars() + movie.getGoldenPalms()) > score)
                {
                    find = true;
                }
                else
                    stack.push(movie);
            }
            if (!find)
                System.out.println("There are no films that are larger than the given.");
            while (!stack.empty())
            {
                Movie movie = stack.pop();
                movies.push(movie);
            }
        }
    }

    /**
     * Method to remove objects from he collection whose greater than the given one via command line
     */
    public void removeGreater()
    {
        if (movies.empty())
        {
            System.out.println("The collection is empty.");
        }
        else
        {
            Movie userMovie = new Movie(getName(), getCoordinates(), getOscarsCount(), getGoldenPalmCount(), getGenre(), getMpaaRating(), getScreenwriter());
            long score = userMovie.getOscars() + userMovie.getGoldenPalms();
            boolean find = false;

            Stack<Movie> stack = new Stack<Movie>();
            while (!movies.empty())
            {
                Movie movie = movies.pop();
                if ((movie.getOscars() + movie.getGoldenPalms()) > score)
                {
                    find = true;
                }
                else
                    stack.push(movie);
            }
            if (!find)
                System.out.println("There are no films that are larger than the given.");
            while (!stack.empty())
            {
                Movie movie = stack.pop();
                movies.push(movie);
            }
        }
    }

    /**
     * Method to execute script from the file
     */
    public void executeScript(String filePath) throws IOException
    {
        this.checkFileForRead(filePath);
        BufferedInputStream bin = null;

        try
        {
            System.out.println("All execute_script commands in file will be skipped.");
            bin = new BufferedInputStream(new FileInputStream(new File(filePath)));

            int nextByte = 0;
            String s = "";

            ArrayList<String> contents = new ArrayList<>();

            while ((nextByte = bin.read()) != -1)
            {
                char nextChar = (char)nextByte;
                if (nextChar == '\n')
                {
                    contents.add(s);
                    s = "";
                }
                else
                {
                    s += nextChar;
                }
            }
            contents.add(s);

            for (int i = 0; i < contents.size(); i++)
            {
                if (contents.get(i).equals(""))
                    contents.remove(i);
            }

            String commandArguments[];

            for (int i = 0; i < contents.size(); i++)
            {
                commandArguments = contents.get(i).trim().split("\\s+"); // elimination of whitespaces and tabs

                switch (commandArguments[0])
                {
                    case "help":
                        help();
                        break;
                    case "info":
                        info();
                        break;
                    case "add":
                        String[] args = new String[10];
                        for (int j = 0; j < 10; j++)
                            args[j] = contents.get(i + j + 1).trim();
                        add(args);
                        i += 10;
                        break;
                    case "show":
                        show();
                        break;
                    case "save":
                        save();
                        break;
                    case "exit":
                        throw new Exception();
                    case "update":
                        String[] argsUpdate = new String[10];
                        for (int j = 0; j < 10; j++)
                            argsUpdate[j] = contents.get(i + j + 1).trim();
                        update(commandArguments[1], argsUpdate);
                        i += 10;
                        break;
                    case "remove_by_id":
                        remove_by_id(commandArguments[1]);
                        break;
                    case "clear":
                        clear();
                        break;
                    case "remove_at":
                        remove_at(commandArguments[1]);
                        break;
                    case "remove_first":
                        remove_first();
                        break;
                    case "count_greater_than_mpaa_rating":
                        count_greater_than_mpaa_rating(commandArguments[1]);
                        break;
                    case "filter_less_than_oscars_count":
                        filterLessThanOscars(commandArguments[1]);
                        break;
                    case "filter_starts_with_the_name":
                        int index = contents.get(i).trim().indexOf(' ');
                        String startsWithTheName = contents.get(i).trim().substring(index);
                        filterStartsWithTheName(startsWithTheName.trim().toLowerCase());
                        break;
                    case "remove_greater":
                        String[] argsGreater = new String[10];
                        for (int j = 0; j < 10; j++)
                            argsGreater[j] = contents.get(i + j + 1).trim();
                        removeGreater(argsGreater);
                        i += 10;
                        break;
                    case "execute_script":
                        break;
                    default:
                        System.out.println("Unknown command. Write 'help' for reference.");
                }
            }

            System.out.println("The commands in the file have ended.");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe)
        {
            System.out.println("Exception while reading file " + ioe);
        }
        catch (Exception exception)
        {
            System.out.println("Finish reading commands from file.");
        }
        finally
        {
            try
            {
                if (bin != null)
                {
                    bin.close();
                }
            }
            catch (Exception e)
            {
                System.out.println("Error while closing streams" + e);
            }
        }
    }
}