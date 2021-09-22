import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Denis Kirbaba
 * @version 2.3
 * The class responsible for the operation of the console on the server.
 */

public class ServerConsole
{
    public void start()
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
            }
            catch (NoSuchElementException noSuchElementException)
            {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }
}
