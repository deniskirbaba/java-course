import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class for working with user input in interactive mode
 */

public class Commander
{
    /** Initialization for avoid error with null string */
    private String userCommand = "";
    /** Field containing user input */
    private String commandArguments[];
    /** Collection manager, required for executing user commands and managing the collection */
    private Manager manager;

    /**
     * Constructor for making a commander
     * @param manager - required for executing user commands and managing the collection
     */
    public Commander (Manager manager)
    {
        this.manager = manager;
    }

    /**
     * Method to run interactive mode
     */
    public void interactiveMod() throws IOException
    {
        try (Scanner scanner = new Scanner(System.in))
        {

            while (!userCommand.equals("exit"))
            {
                System.out.print("Enter a command: ");
                userCommand = scanner.nextLine();
                commandArguments = userCommand.trim().split("\\s+"); // elimination of whitespaces and tabs

                switch (commandArguments[0])
                {
                    case "":
                        break;
                    case "help":
                        manager.help();
                        break;
                    case "info":
                        manager.info();
                        break;
                    case "add":
                        manager.add();
                        break;
                    case "show":
                        manager.show();
                        break;
                    case "save":
                        manager.save();
                        break;
                    case "exit":
                        break;
                    case "update":
                        if (commandArguments.length != 1)
                            manager.update(commandArguments[1]);
                        else
                            System.out.println("This function requires the parameter.");
                        break;
                    case "remove_by_id":
                        if (commandArguments.length != 1)
                            manager.remove_by_id(commandArguments[1]);
                        else
                            System.out.println("This function requires the parameter.");
                        break;
                    case "clear":
                        manager.clear();
                        break;
                    case "remove_at":
                        if (commandArguments.length != 1)
                            manager.remove_at(commandArguments[1]);
                        else
                            System.out.println("This function requires the parameter.");
                        break;
                    case "remove_first":
                        manager.remove_first();
                        break;
                    case "count_greater_than_mpaa_rating":
                        if (commandArguments.length != 1)
                            manager.count_greater_than_mpaa_rating(commandArguments[1]);
                        else
                            System.out.println("This function requires the parameter.");
                        break;
                    case "filter_less_than_oscars_count":
                        if (commandArguments.length != 1)
                            manager.filterLessThanOscars(commandArguments[1]);
                        else
                            System.out.println("This function requires the parameter.");
                        break;
                    case "filter_starts_with_the_name":
                        if (commandArguments.length != 1)
                        {
                            int index = userCommand.trim().indexOf(' ');
                            String startsWithTheName = userCommand.trim().substring(index);
                            manager.filterStartsWithTheName(startsWithTheName.trim().toLowerCase());
                        }
                        else
                            System.out.println("This function requires the parameter.");
                        break;
                    case "remove_greater":
                        manager.removeGreater();
                        break;
                    case "execute_script":
                        if (commandArguments.length != 1)
                            manager.executeScript(commandArguments[1]);
                        else
                            System.out.println("This function requires the parameter.");
                        break;
                    default:
                        System.out.println("Unknown command. Write 'help' for reference.");
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("No argument was given.");
        }
        catch (StringIndexOutOfBoundsException ex)
        {
            System.out.println("No argument was given.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Typing error cnrl+d.");
        }
    }
}
