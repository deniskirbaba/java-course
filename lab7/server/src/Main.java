import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Main class
 */
public class Main
{
    /**
     * Main method
     */
    public static void main(String[] args)
    {
        if (checkDriver())
        {
            String[] loginAndPassword = getLoginAndPassword();

            //database connection address
            String jdbcURL = "jdbc:postgresql://localhost:8000/studs";
            Database database = new Database(jdbcURL, loginAndPassword[0], loginAndPassword[1]);
            database.connect();

            Server server = new Server(8001, database.load());
            server.setDaemon(true);
            server.start();

            serverConsoleInteraction(server);
        }
        else
            System.out.println("The driver for working with the database was not found.");
    }

    /**
     * Method to check if there is a driver to work with PostgreSQL
     */
    public static boolean checkDriver()
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

    /**
     * Method to get database login details from user
     */
    public static String[] getLoginAndPassword()
    {
        String[] data = {"", ""};
        try
        {
            System.out.println("To access the database, you must enter your username and password.");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Login: ");
                String login = scanner.nextLine().trim();
                if (!login.equals(""))
                {
                    data[0] = login;
                    break;
                }
            }

            Console console = System.console();
            if (console != null)
            {
                while (true) {
                    System.out.print("Password: ");
                    char[] symbols = console.readPassword();
                    if (symbols != null) {
                        data[1] = String.valueOf(symbols);
                        break;
                    }
                }
            }
            else
            {
                while (true)
                {
                    System.out.print("Password: ");
                    String password = scanner.nextLine().trim();
                    if (!password.equals("")) {
                        data[1] = password;
                        break;
                    }
                }
            }
            return data;
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(0);
            return data;
        }
    }

    /**
     * Method for work with the console on the server (available commands: exit, save)
     */
    public static void serverConsoleInteraction(Server server)
    {
        while (true)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("exit"))
                {
                    break;
                }
                else if (command.equalsIgnoreCase("save"))
                {
                    server.getManager().save();
                }
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }
}