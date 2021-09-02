import java.sql.*;
import java.util.Stack;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class for working with the database
 */
public class Database
{
    /** Database connection address */
    private final String URL;

    /** Login to connect to the database */
    private final String login;

    /** Password to connect to the database */
    private final String password;

    /**
     * An object that represents a connection to the database.
     * Any interaction with the database will be done through an object of this class.
     */
    private Connection connection;

    /** Constructor */
    public Database(String URL, String login, String password)
    {
        this.URL = URL;
        this.login = login;
        this.password = password;
    }

    /**
     * Method to connect to the database
     */
    public void connect()
    {
        try
        {
            this.connection = DriverManager.getConnection(this.URL, this.login, this.password);
            System.out.println("Database connection established.");
        }
        catch (SQLException sqlException)
        {
            System.out.println("Failed to connect to database.");
            System.exit(1);
        }
    }

    /**
     * Method for loading a data from database to a collection stored in memory
     */
    public Stack<Movie> load()
    {
        Stack<Movie> movies = new Stack<>();
        try {
            PreparedStatement getMoviesStatement = this.connection.prepareStatement("SELECT * FROM MOVIES");
            ResultSet resultSet = getMoviesStatement.executeQuery();

            while (resultSet.next())
            {
                Movie movie = getMovieFromResultSet(resultSet);
                if (Movie.checkMovie(movie))
                {
                    movies.push(movie);
                    Movie.addToIdList(movie.getId());
                }
            }
        }
        catch (SQLException exception)
        {
            System.out.println("An error occurred while loading the collection.");
        }
        return movies;
    }

    /**
     * Method to extract movie from result set
     */
    public Movie getMovieFromResultSet(ResultSet resultSet) throws SQLException
    {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Long coordinateX = resultSet.getLong("coordinateX");
        long coordinateY = resultSet.getLong("coordinateY");
        String date = resultSet.getString("creationDate");
        long oscarsCount = resultSet.getLong("oscarsCount");
        Integer goldenPalmCount = resultSet.getInt("goldenPalmCount");
        MovieGenre genre = MovieGenre.stringToGenre(resultSet.getString("genre"));
        MpaaRating mpaaRating = MpaaRating.stringToMpaaRating(resultSet.getString("mpaaRating"));
        String screenwriterName = resultSet.getString("screenwriterName");
        int screenwriterHeight = resultSet.getInt("screenwriterHeight");
        float screenwriterWeight = resultSet.getFloat("screenwriterWeight");
        String user = resultSet.getString("user");

        Movie  movie = new Movie(id, name, new Coordinates(coordinateX, coordinateY), date, oscarsCount, goldenPalmCount,
                genre, mpaaRating, new Person(screenwriterName, screenwriterHeight, screenwriterWeight), user);

        return movie;
    }
}
