import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, describing the objects stored in the collection
 */
public class Main
{
    /**
     * Main method
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        if (args.length > 0)
        {
            Server server = new Server(args[0], 8081);
            server.setDaemon(true);
            server.start();

            boolean stop = false;
            while (!stop)
            {
                try
                {
                    Scanner scanner = new Scanner(System.in);
                    String command = scanner.nextLine();
                    if (command.equalsIgnoreCase("exit"))
                    {
                        stop = true;
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
        else
            System.out.println("No argument given. The file path is required as a command line argument.");
    }
}