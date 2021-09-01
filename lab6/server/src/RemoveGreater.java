import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RemoveGreater implements Serializable
{
    private final Movie movie;

    public RemoveGreater() {
        this.movie = new Movie(this.getName(), this.getCoordinates(), this.getOscarsCount(),
                this.getGoldenPalmCount(), this.getGenre(), this.getMpaaRating(), this.getScreenwriter());
    }

    public Movie getMovie()
    {
        return this.movie;
    }

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
                System.exit(0);
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
        while (true)
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
                System.exit(0);
            }
        }
    }

    /**
     * Method to get the number of Golden Palms from user
     */
    public Integer getGoldenPalmCount()
    {
        while (true)
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
                System.exit(0);
            }
        }
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
     * Method to get movie MPAA rating from user via command line
     */
    public MpaaRating getMpaaRating()
    {
        while (true)
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
        while (true)
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
        while (true)
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
        while (true)
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
     * Method to get X coordinate from user
     */
    public Long getX()
    {
        while (true)
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
                System.exit(0);
            }
        }
    }

    /**
     * Method to get Y coordinate from user
     */
    public long getY()
    {
        while (true)
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
                System.exit(0);
            }
        }
    }
}
