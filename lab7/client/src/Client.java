import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commands.*;

/**
 * @author Denis Kirbaba
 * @version 2.5
 * The class responsible for connecting to the server, validating the entered commands,
 * sending them to the server and displaying the results.
 */

public class Client
{
    private String host = "localhost";
    private int port = 7456;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String login;

    public boolean connect(boolean outputFlag)
    {
        try
        {
            this.socket = new Socket(this.host, this.port);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            return true;
        }
        catch (IOException e)
        {
            if (outputFlag)
                System.out.println("Server is not available.");
            return false;
        }
    }

    public boolean start()
    {
        Scanner scanner = new Scanner(System.in);
        try
        {
            boolean stop = false;

            while (!stop)
            {
                try
                {
                    System.out.print("Enter a command: ");
                    String userCommand = scanner.nextLine();
                    String[] commandArguments = userCommand.trim().split("\\s+");
                    if (commandArguments[0].equals("exit"))
                    {
                        stop = true;
                        return true;
                    }
                    if (this.socket == null)
                    {
                        System.out.println("Server is not available.");
                        break;
                    }
                    else if (!this.socket.isConnected())
                    {
                        System.out.println("Server is not available.");
                        break;
                    }

                    switch (commandArguments[0])
                    {
                        case "":
                            break;
                        case "help":
                            Help help = new Help();
                            help.print();
                            break;
                        case "info":
                            Info info = new Info();
                            oos.writeObject(info);
                            info = (Info) ois.readObject();
                            info.print();
                            break;
                        case "add":
                            Add add = new Add(this.login);
                            oos.writeObject(add);
                            System.out.println((String)ois.readObject());
                            break;
                        case "show":
                            Show show = new Show();
                            oos.writeObject(show);
                            show = (Show) ois.readObject();
                            show.print();
                            break;
                        case "count_greater_than_mpaa_rating":
                            if (commandArguments.length != 1)
                            {
                                CountGreaterThanMpaaRating countGreaterThanMpaaRating = new CountGreaterThanMpaaRating();
                                commandArguments[1] = commandArguments[1].toUpperCase(Locale.ROOT);
                                if (countGreaterThanMpaaRating.setMpaaRating(commandArguments[1]))
                                {
                                    oos.writeObject(countGreaterThanMpaaRating);
                                    countGreaterThanMpaaRating = (CountGreaterThanMpaaRating) ois.readObject();
                                    countGreaterThanMpaaRating.printResult();
                                }
                            }
                            else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "filter_less_than_oscars_count":
                            if (commandArguments.length != 1)
                            {
                                FilterLessThanOscarsCount filterLessThanOscarsCount = new FilterLessThanOscarsCount();
                                if (filterLessThanOscarsCount.setOscarsCount(commandArguments[1]))
                                {
                                    oos.writeObject(filterLessThanOscarsCount);
                                    filterLessThanOscarsCount = (FilterLessThanOscarsCount) ois.readObject();
                                    filterLessThanOscarsCount.print();
                                }
                            }
                            else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "filter_starts_with_the_name":
                            if (commandArguments.length != 1)
                            {
                                FilterStartsWithTheName filterStartsWithTheName = new FilterStartsWithTheName();
                                filterStartsWithTheName.setStartingString(commandArguments[1]);
                                oos.writeObject(filterStartsWithTheName);
                                filterStartsWithTheName = (FilterStartsWithTheName) ois.readObject();
                                filterStartsWithTheName.print();
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "update":
                            if (commandArguments.length != 1)
                            {
                                if (this.checkId(commandArguments[1]))
                                {
                                    Update update = new Update();
                                    update.setID(commandArguments[1]);
                                    oos.writeObject(update);
                                    System.out.println((String)ois.readObject());
                                }
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "remove_by_id":
                            if (commandArguments.length != 1)
                            {
                                if (this.checkId(commandArguments[1]))
                                {
                                    RemoveById removeById = new RemoveById();
                                    removeById.setId(commandArguments[1]);
                                    oos.writeObject(removeById);
                                    System.out.println((String)ois.readObject());
                                }
                            }
                            else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "clear":
                            Clear clear = new Clear();
                            clear.setUser(this.login);
                            oos.writeObject(clear);
                            System.out.println((String)ois.readObject());
                            break;
                        case "remove_at":
                            if (commandArguments.length != 1)
                            {
                                RemoveAt removeAt = new RemoveAt();
                                if (removeAt.setIndex(commandArguments[1]))
                                {
                                    removeAt.setUser(this.login);
                                    oos.writeObject(removeAt);
                                    removeAt = (RemoveAt) ois.readObject();
                                    removeAt.printResult();
                                }
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "remove_first":
                            RemoveAt removeFirst = new RemoveAt();
                            removeFirst.setIndex("1");
                            removeFirst.setUser(this.login);
                            oos.writeObject(removeFirst);
                            removeFirst = (RemoveAt) ois.readObject();
                            removeFirst.printResult();
                            break;
                        case "remove_greater":
                            RemoveGreater removeGreater = new RemoveGreater(this.login);
                            oos.writeObject(removeGreater);
                            removeGreater = (RemoveGreater) ois.readObject();
                            removeGreater.printResult();
                            break;
                        case "execute_script":
                            ExecuteScript executeScript = new ExecuteScript(this.login);
                            if (commandArguments.length != 1)
                            {
                                executeScript.setFilePath(commandArguments[1]);
                                if (executeScript.checkFileForWriteRead(false))
                                {
                                    System.out.println("Executing commands from " + commandArguments[1]);
                                    if (executeScript.start(oos, ois)) {
                                        return true;
                                    }
                                }
                                ExecuteScript.resetFilePaths();
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        default:
                        {
                            System.out.println("Unknown command. Write 'help' for reference.");
                        }
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Server is not available.");
                    this.socket = null;
                    break;
                }
            }
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Data transfer error.");
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
        }
        return false;
    }

    public void stop()
    {
        if (!(this.socket == null))
        {
            try
            {
                socket.close();
            }
            catch (NullPointerException | IOException e)
            {
                System.out.print("");
            }
        }
    }

    public String getLogin()
    {
        String login = "";
        Pattern pattern = Pattern.compile("^[A-Za-z]([A-Za-z0-9-_]*)");
        try
        {
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                System.out.print("Login: ");
                login = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                if (!login.equals(""))
                {
                    Matcher matcher = pattern.matcher(login);
                    if (matcher.matches() && login.length() > 2)
                        break;
                    else
                        System.out.println("Login may contain only letters of the Latin alphabet (case-insensitive), " +
                            "numbers and underscores or dashes and start with a letter. The minimum login length is 3 characters.");
                }
            }
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(0);
        }
        return login;
    }

    public String getPassword()
    {
        Scanner scanner = new Scanner(System.in);
        String password = "";
        Pattern pattern = Pattern.compile("^[A-Za-z]([A-Za-z0-9-_]*)");
        try
        {
            Console console = System.console();
            if (console != null)
            {
                while (true)
                {
                    System.out.print("Password: ");
                    char[] symbols = console.readPassword();
                    if (symbols != null)
                    {
                        password = String.valueOf(symbols).toLowerCase(Locale.ROOT);
                        Matcher matcher = pattern.matcher(password);
                        if (matcher.matches() && password.length() > 4)
                            break;
                        else
                            System.out.println("Password may contain only letters of the Latin alphabet (case-insensitive), " +
                                    "numbers and underscores or dashes and start with a letter. The minimum login length is 5 characters.");
                    }
                }
            }
            else
            {
                while (true)
                {
                    System.out.print("Password: ");
                    password = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                    if (!password.equals(""))
                    {
                        Matcher matcher = pattern.matcher(password);
                        if (matcher.matches() && password.length() > 4)
                            break;
                        else
                            System.out.println("Password may contain only letters of the Latin alphabet (case-insensitive), " +
                                    "numbers and underscores or dashes and start with a letter. The minimum login length is 5 characters.");
                    }
                }
            }
            return password;
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
            System.exit(0);
        }
        return password;
    }

    public String encrypt(String string)
    {
        try
        {
            MessageDigest mmm = MessageDigest.getInstance("SHA-384");
            byte[] dig = mmm.digest(string.getBytes(StandardCharsets.UTF_8));
            BigInteger bi = new BigInteger(1, dig);
            String result = bi.toString(16);
            while (result.length() < 32)
                result = "0" + result;
            return result;
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("No such algorithm.");
        }
        return null;
    }

    public boolean authorize()
    {
        System.out.println("In order to work with the data, you need to log in or sign up.\n" +
                "If you want to log in, enter \"log_in\", otherwise enter \"sign_up\".");
        Scanner scanner = new Scanner(System.in);
        try
        {
            boolean stop = false;

            while (!stop)
            {
                try
                {
                    System.out.print("Enter a command: ");
                    String userCommand = scanner.nextLine();
                    String[] commandArguments = userCommand.trim().split("\\s+");
                    if (commandArguments[0].equals("exit"))
                    {
                        stop = true;
                        return true;
                    }
                    if (this.socket == null)
                    {
                        if (!this.connect(false))
                        {
                            System.out.println("Server is not available.");
                            continue;
                        }
                    }
                    else if (!this.socket.isConnected())
                    {
                        if (!this.connect(false))
                        {
                            System.out.println("Server is not available.");
                            continue;
                        }
                    }

                    switch (commandArguments[0]) {
                        case "":
                            break;
                        case "log_in":
                            String login = this.getLogin();
                            this.oos.writeObject(login);
                            this.oos.writeObject(false);
                            int res = (int)this.ois.readObject();
                            if (res == 0)
                            {
                                System.out.println("User with the given login does not exists.");
                                continue;
                            }
                            else if (res == 1)
                            {
                                String password = this.getPassword();
                                password = this.encrypt(password);
                                this.oos.writeObject(password);
                                int rres = (int)this.ois.readObject();
                                if (rres == 0)
                                {
                                    System.out.println("Wrong password.");
                                    continue;
                                }
                                else if (rres == 1)
                                {
                                    System.out.println("You have successfully logged in, " +
                                            "now you have access to commands for working with data. Write \"help\" to get " +
                                            "reference about available commands.");
                                    this.login = login;
                                    stop = true;
                                    return false;
                                }
                                else
                                {
                                    System.out.println("There is a problem with the server, please try again a bit later.");
                                    continue;
                                }
                            }
                            else
                            {
                                System.out.println("There is a problem with the server, please try again a bit later.");
                                continue;
                            }
                        case "sign_up":
                            String slogin = this.getLogin();
                            this.oos.writeObject(slogin);
                            this.oos.writeObject(true);
                            int sres = (int)this.ois.readObject();
                            if (sres == 0)
                            {
                                String password = this.getPassword();
                                password = this.encrypt(password);
                                this.oos.writeObject(password);
                                if ((boolean) this.ois.readObject())
                                {
                                    System.out.println("You have successfully registered, " +
                                            "now you have access to commands for working with data. Write \"help\" to get " +
                                            "reference about available commands.");
                                    this.login = slogin;
                                    stop = true;
                                    return false;
                                }
                                else
                                    System.out.println("There is a problem with the server, please try again a bit later.");
                            }
                            else if (sres == 1)
                            {
                                System.out.println("This login is already occupied.");
                                continue;
                            }
                            else
                            {
                                System.out.println("There is a problem with the server, please try again a bit later.");
                                continue;
                            }
                            break;
                        case "help":
                            System.out.println("log_in - login to the application with an existing username and password.\n" +
                                    "sign_up - new user registration.\n" +
                                    "help - reference about available commands.\n" +
                                    "exit - shutting down the application.");
                            break;
                        default:
                        {
                            System.out.println("Unknown command. Write 'help' for reference.");
                        }
                    }
                } catch (IOException e)
                {
                    System.out.println("Server is not available.");
                    this.connect(false);
                } catch (ClassNotFoundException e)
                {
                    System.out.println("Connection error.");
                }
            }
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
        }
        return true;
    }

    public boolean checkId(String strId)
    {
        try
        {
            int id = Integer.parseInt(strId);
            if (id > 0)
            {
                CheckId checkId = new CheckId(id);
                oos.writeObject(checkId);
                checkId = (CheckId) ois.readObject();
                if (!checkId.isExist()) {
                    System.out.println("This id does not exist.");
                    return false;
                }
                else if (!checkId.isAvailable())
                {
                    System.out.println("You cannot modify an object that does not belong to you.");
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
                System.out.println("ID must be > 0.");
        }
        catch (NumberFormatException | InputMismatchException | IOException | ClassNotFoundException numberFormatException) {
            System.out.println("ID must be integer.");
        }
        return false;
    }
}