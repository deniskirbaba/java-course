import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;
import data.*;
import commands.*;

/**
 * @author Denis Kirbaba
 * @version 2.9
 * The class associated with the database, storing the collection.
 * Checks for jbdc driver existence.
 * Performs database authorization (jdbcURL, login, password), where credentials are reading from db.cfg file.
 * Implemented singleton template, since the database should only exist in one instance.
 * Execute commands to get, change, add and delete items from the database.
 */

public class Database
{
    private Properties info;
    private String jdbcURL = "jdbc:postgresql://pg:5432/studs";
    private static Database instance;

    public Database()
    {
        if (checkDriver())
        {
            this.info = new Properties();
            try
            {
                this.info.load(new FileInputStream("db.cfg"));
                this.load(true);
            }
            catch (IOException e)
            {
                System.out.println("FIle with credentials wasn't found.");
                System.exit(1);
            }
        }
        else
        {
            System.out.println("The driver for working with the database was not found.");
            System.exit(1);
        }
    }

    public static Database getInstance()
    {
        if (Database.instance == null)
            Database.instance = new Database();
        return Database.instance;
    }

    public boolean checkDriver()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException classNotFoundException)
        {
            return false;
        }
        return true;
    }

    public void connect()
    {
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info))
        {
            System.out.println("Database connection established.");
        }
        catch (SQLException sqlException)
        {
            System.out.println("Failed to connect to database.");
        }
    }

    public void load(boolean exitFlag)
    {
        String sqlCommand = "SELECT * FROM movies";
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             Statement loadCollection = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet res = loadCollection.executeQuery(sqlCommand))
        {
            Manager manager = Manager.getInstance();
            manager.clear();
            while (res.next())
            {
                    String id = res.getString("id");
                    String name = res.getString("name");
                    String coordinateX = res.getString("coordinate_x");
                    String coordinateY = res.getString("coordinate_y");
                    String date = res.getString("creation_date");
                    String oscarsCount = res.getString("oscars_count");
                    String  goldenPalmCount = res.getString("golden_palm_count");
                    String genre = res.getString("genre");
                    String mpaaRating = res.getString("mpaa_rating");
                    String screenwriterName = res.getString("screenwriter_name");
                    String screenwriterHeight = res.getString("screenwriter_height");
                    String screenwriterWeight = res.getString("screenwriter_weight");
                    String user = res.getString("login");

                    if (Movie.checkArguments(id, name, coordinateX, coordinateY, date, oscarsCount, goldenPalmCount,
                            genre, mpaaRating, screenwriterName, screenwriterHeight, screenwriterWeight, user))
                    {
                        Movie movie = new Movie(Integer.parseInt(id), name, new Coordinates(Long.parseLong(coordinateX),
                                Long.parseLong(coordinateY)), date, Long.parseLong(oscarsCount), Integer.parseInt(goldenPalmCount),
                                MovieGenre.stringToGenre(genre), MpaaRating.stringToMpaaRating(mpaaRating),
                                new Person(screenwriterName, Integer.parseInt(screenwriterHeight), Float.parseFloat(screenwriterWeight)),
                                user);
                        manager.add(movie);
                    }
                    else
                    {
                        this.removeById(Integer.parseInt(id));
                        System.out.println("Removed one invalid row from database.");
                    }
            }
            System.out.println("Collection successfully loaded from database.");
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database." + e.getMessage());
            if (exitFlag)
                System.exit(1);
        }
    }

    public boolean add(Add add)
    {
        String sqlCommand = "INSERT INTO movies (name, coordinate_x, coordinate_y, creation_date, oscars_count," +
                "golden_palm_count, genre, mpaa_rating, screenwriter_name, screenwriter_height, screenwriter_weight," +
                "login) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             PreparedStatement statement = connection.prepareStatement(sqlCommand))
        {
            statement.setString(1, add.getName());
            statement.setInt(2, add.getCoordinates().getX().intValue());
            statement.setInt(3, (int)add.getCoordinates().getY());
            statement.setString(4, add.getCreationDate());
            statement.setInt(5, (int)add.getOscarsCount());
            statement.setInt(6, add.getGoldenPalmCount());
            statement.setString(7, add.getGenre().toString());
            statement.setString(8, add.getMpaaRating().toString());
            statement.setString(9, add.getScreenwriter().getName());
            statement.setInt(10, add.getScreenwriter().getHeight());
            statement.setFloat(11, add.getScreenwriter().getWeight());
            statement.setString(12, add.getUser());
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database.");
            return false;
        }
    }

    public int checkUser(String login)
    {
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             PreparedStatement check = connection.prepareStatement("SELECT 1 FROM users WHERE login = ?"))
        {
            check.setString(1, login);
            ResultSet res = check.executeQuery();
            while (res.next())
                return 1;
            return 0;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database." + e.getMessage());
            return 2;
        }
    }

    public int checkUser(String login, String password)
    {
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             PreparedStatement check = connection.prepareStatement("SELECT 1 FROM users WHERE login = ? and password = ?"))
        {
            check.setString(1, login);
            check.setString(2, password);
            ResultSet res = check.executeQuery();
            while (res.next())
                return 1;
            return 0;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database." + e.getMessage());
            return 2;
        }
    }

    public boolean addUser(String login, String password)
    {
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             PreparedStatement addUser = connection.prepareStatement("INSERT INTO users (login, password) " +
                     "values (?, ?)"))
        {
            addUser.setString(1, login);
            addUser.setString(2, password);
            addUser.execute();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database.");
            return false;
        }
    }

    public boolean update(Update update)
    {
        String sqlCommand = "UPDATE movies SET name = ?, coordinate_x = ?, coordinate_y = ?, oscars_count = ?," +
                "golden_palm_count = ?, genre = ?, mpaa_rating = ?, screenwriter_name = ?, screenwriter_height = ?, screenwriter_weight = ?" +
                " WHERE id = " + update.getId();
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             PreparedStatement statement = connection.prepareStatement(sqlCommand))
        {
            statement.setString(1, update.getName());
            statement.setInt(2, update.getCoordinates().getX().intValue());
            statement.setInt(3, (int)update.getCoordinates().getY());
            statement.setInt(4, (int)update.getOscarsCount());
            statement.setInt(5, update.getGoldenPalmCount());
            statement.setString(6, update.getGenre().toString());
            statement.setString(7, update.getMpaaRating().toString());
            statement.setString(8, update.getScreenwriter().getName());
            statement.setInt(9, update.getScreenwriter().getHeight());
            statement.setFloat(10, update.getScreenwriter().getWeight());
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database.");
            return false;
        }
    }

    public boolean removeById(int id)
    {
        String sqlCommand = "DELETE FROM movies WHERE id = " + id;
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             PreparedStatement statement = connection.prepareStatement(sqlCommand))
        {
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database.");
            return false;
        }
    }

    public boolean clear(String user)
    {
        String sqlCommand = "DELETE FROM movies WHERE login = ?";
        try (Connection connection = DriverManager.getConnection(this.jdbcURL, this.info);
             PreparedStatement statement = connection.prepareStatement(sqlCommand))
        {
            statement.setString(1, user);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to connect to database." + e.getMessage());
            return false;
        }
    }

    public boolean removeAt(RemoveAt removeAt)
    {
        Manager manager = Manager.getInstance();

        if (manager.getMovies().isEmpty())
        {
            removeAt.setResult("The collection is empty.");
            return true;
        }
        else
        {
            if (manager.getMovies().size() < removeAt.getIndex())
            {
                removeAt.setResult("The object with the given index does not exist.");
                return true;
            }
            else
            {
                ArrayList<Movie> arrayList = manager.getMovies().stream().sorted().collect(Collectors.toCollection(ArrayList::new));
                Movie removingMovie = arrayList.get(removeAt.getIndex() - 1);
                if (manager.ifIdAvailable(removingMovie.getId(), removeAt.getUser()))
                {
                    if (this.removeById(removingMovie.getId()))
                    {
                        removeAt.setResult("Object deleted successfully.");
                        return false;
                    }
                    else
                    {
                        removeAt.setResult("There is a problem with the server, so removing item in the collection failed" +
                                ", please try again a bit later.");
                        return true;
                    }
                }
                else
                {
                    removeAt.setResult("You cannot modify an object that does not belong to you.");
                    return true;
                }
            }
        }
    }

    public boolean removeGreater(RemoveGreater removeGreater)
    {
        Manager manager = Manager.getInstance();

        if (manager.getMovies().isEmpty())
        {
            removeGreater.setResult("The collection is empty.");
            return true;
        }
        else
        {
            ArrayList<Movie> removingMovies = manager.getMovies().stream().filter(x ->
                    x.compareTo(removeGreater.getMovie()) > 0).collect(Collectors.toCollection(ArrayList::new));
            if (removingMovies.size() == 0)
            {
                removeGreater.setResult("There are no objects greater than the given one.");
                return true;
            }
            int counter = 0;
            for (Movie movie : removingMovies)
            {
                if (manager.ifIdAvailable(movie.getId(), removeGreater.getMovie().getUser()))
                {
                    if (!this.removeById(movie.getId()))
                    {
                        removeGreater.setResult("There is a problem with the server, so removing items in the collection failed" +
                                ", please try again a bit later.");
                        return true;
                    }
                    else
                        counter += 1;
                }
            }
            if (counter == 0)
            {
                removeGreater.setResult("There are only greater objects that you cannot modify.");
                return true;
            }
            else {
                removeGreater.setResult("Successfully deleted " + counter + " objects larger than the given one.");
                return false;
            }
        }
    }
}