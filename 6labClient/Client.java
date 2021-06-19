import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client
{
    private SocketChannel client;
    private ByteBuffer buffer;
    private String hostName;
    private int port;
    private Socket socket;

    public boolean startConnection(String hostName, int port, boolean outputFlag) throws IOException
    {
        try
        {
            this.hostName = hostName;
            this.port = port;
            this.client = SocketChannel.open(new InetSocketAddress(hostName, port));
            this.buffer = ByteBuffer.allocate(32768);
            this.socket = this.client.socket();
            return true;
        }
        catch (ConnectException connectException)
        {
            if (outputFlag)
                System.out.println("Server is not available.");
            return false;
        }
    }

    public void interactiveMode() throws IOException
    {
        try (Scanner scanner = new Scanner(System.in))
        {
            boolean stop = false;

            while (!stop)
            {
                try
                {
                    System.out.print("Enter a command: ");
                    String userCommand = scanner.nextLine();
                    String[] commandArguments = userCommand.trim().split("\\s+"); // elimination of whitespaces and tabs
                    if (commandArguments[0].equals("exit")) {
                        stop = true;
                        continue;
                    }
                    if (this.client == null)
                    {
                        if (!this.startConnection(this.hostName, this.port,false)) {
                            System.out.println("Server is not available.");
                            continue;
                        }
                    }
                    else if (!this.client.isConnected())
                    {
                        if (!this.startConnection(this.hostName, this.port,false)) {
                            System.out.println("Server is not available.");
                            continue;
                        }
                    }

                    switch (commandArguments[0])
                    {
                        case "":
                            break;
                        case "help":
                            Help help = new Help();
                            if (!send(this.client, help))
                                break;
                            help = (Help)this.read(this.client);
                            if (help == null)
                                break;
                            help.print();
                            break;
                        case "info":
                            Info info = new Info();
                            if (!send(this.client, info))
                                break;
                            info = (Info) this.read(this.client);
                            if (info == null)
                                break;
                            info.print();
                            break;
                        case "add":
                            Add add = new Add();
                            if (!send(this.client, add))
                                break;
                            break;
                        case "show":
                            Show show = new Show();
                            if (!send(this.client, show))
                                break;
                            show = (Show) this.read(this.client);
                            if (show == null)
                                break;
                            show.print();
                            break;
                        case "update":
                            if (commandArguments.length != 1)
                            {
                                if (this.checkId(commandArguments[1])) {
                                    Update update = new Update();
                                    if (update.setID(commandArguments[1])) {
                                        if (!send(this.client, update))
                                            break;
                                        String output = (String) this.read(this.client);
                                        if (output == null)
                                            break;
                                        System.out.println(output);
                                    }
                                }
                                else
                                    break;
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "remove_by_id":
                            if (commandArguments.length != 1) {
                                RemoveById removeById = new RemoveById();
                                if (removeById.setId(commandArguments[1])) {
                                    if (!send(this.client, removeById))
                                        break;
                                    String output = (String) this.read(this.client);
                                    if (output == null)
                                        break;
                                    System.out.println(output);
                                }
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "clear":
                            Clear clear = new Clear();
                            if (!send(this.client, clear))
                                break;
                            String output = (String) this.read(this.client);
                            if (output == null)
                                break;
                            System.out.println(output);
                            break;
                        case "remove_at":
                            if (commandArguments.length != 1) {
                                RemoveAt removeAt = new RemoveAt();
                                if (removeAt.setIndex(commandArguments[1])) {
                                    if (!send(this.client, removeAt))
                                        break;
                                    String remove_at = (String) this.read(this.client);
                                    if (remove_at == null)
                                        break;
                                    System.out.println(remove_at);
                                }
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "remove_first":
                            RemoveAt removeFirst = new RemoveAt();
                            removeFirst.setIndex("1");
                            if (!send(this.client, removeFirst))
                                break;
                            String remove_first = (String) this.read(this.client);
                            if (remove_first == null)
                                break;
                            System.out.println(remove_first);
                            break;
                        case "count_greater_than_mpaa_rating":
                            if (commandArguments.length != 1) {
                                CountGreaterThanMpaaRating countGreaterThanMpaaRating = new CountGreaterThanMpaaRating();
                                if (countGreaterThanMpaaRating.setMpaaRating(commandArguments[1])) {
                                    if (!send(this.client, countGreaterThanMpaaRating))
                                        break;
                                    String count_greater_than_mpaa_rating = (String) this.read(this.client);
                                    if (count_greater_than_mpaa_rating == null)
                                        break;
                                    System.out.println(count_greater_than_mpaa_rating);
                                }
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "filter_less_than_oscars_count":
                            if (commandArguments.length != 1) {
                                FilterLessThanOscarsCount filterLessThanOscarsCount = new FilterLessThanOscarsCount();
                                if (filterLessThanOscarsCount.setOscarsCount(commandArguments[1])) {
                                    if (!send(this.client, filterLessThanOscarsCount))
                                        break;
                                    filterLessThanOscarsCount = (FilterLessThanOscarsCount) this.read(this.client);
                                    if (filterLessThanOscarsCount == null)
                                        break;
                                    filterLessThanOscarsCount.print();
                                }
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "filter_starts_with_the_name":
                            if (commandArguments.length != 1) {
                                FilterStartsWithTheName filterStartsWithTheName = new FilterStartsWithTheName();
                                filterStartsWithTheName.setStartingString(commandArguments[1]);
                                if (!send(this.client, filterStartsWithTheName))
                                    break;
                                filterStartsWithTheName = (FilterStartsWithTheName) this.read(this.client);
                                if (filterStartsWithTheName == null)
                                    break;
                                filterStartsWithTheName.print();
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "remove_greater":
                            RemoveGreater removeGreater = new RemoveGreater();
                            if (!send(this.client, removeGreater))
                                break;
                            break;
                        case "execute_script":
                            ExecuteScript executeScript = new ExecuteScript();
                            if (commandArguments.length != 1)
                            {
                                executeScript.setFilePath(commandArguments[1]);
                                if (executeScript.checkFileForWriteRead(false))
                                {
                                    System.out.println("Executing commands from " + commandArguments[1]);
                                    executeScript.start(this.client);
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
                } catch (SocketException socketException)
                {
                    System.out.println("Server is not available." + socketException);
                }
            }
        }
        catch (ClassNotFoundException classNotFoundException)
        {
            System.out.println("Serialization exception.");
        }
        catch (SocketException socketException)
        {
            System.out.println("Server is not available." + socketException);
        }
        catch (NoSuchElementException noSuchElementException)
        {
            System.out.println("Program was stopped successfully.");
        }
        /*
        catch (NullPointerException exception)
        {
            System.out.println("Null pointer exception.");
        }

         */
    }

    public void stopConnection() throws IOException
    {
        if (!(this.client == null))
        {
            try
            {
                client.close();
            } catch (NullPointerException | SocketException nullPointerException)
            {
                System.out.print("");
            }
        }
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
                System.exit(0);
            }
        }
    }

    public boolean checkId(String strId)
    {
        try
        {
            int id = Integer.parseInt(strId);
            if (id > 0)
            {
                CheckId checkId = new CheckId(id);
                this.send(this.client, checkId);
                checkId = (CheckId) this.read(this.client);
                if (checkId.isExist())
                    return true;
                else {
                    System.out.println("This id does not exist.");
                    return false;
                }
            }
            else
                System.out.println("ID must be > 0.");
        }
        catch (NumberFormatException numberFormatException)
        {
            System.out.println("ID must be integer.");
        }
        catch (InputMismatchException inputMismatchException)
        {
            System.out.println("ID must be integer.");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return false;
    }

    /**
     * Method to get the address to the server from user, simultaneously checking the input.
     */
    public String checkAndGetHost()
    {
        while (true)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a new IP address: ");
                String name = scanner.nextLine().trim();
                if (name.equals(""))
                {
                    System.out.println("This value cannot be empty.");
                    continue;
                }
                InetAddress address = InetAddress.getByName(name);
                if (address.isReachable(5000)) {
                    return address.getHostAddress();
                }
                else
                {
                    System.out.println("Address is not reachable.");
                    continue;
                }
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
            catch (UnknownHostException unknownHostException)
            {
                System.out.println("Invalid host");
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }

    public boolean send(SocketChannel socket, Serializable serializable) throws IOException
    {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(serializable);
            oos.close();
            ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
            socket.write(wrap);
            return true;
        }
        catch (IOException ioException)
        {
            System.out.println("Server is not available.");
            this.client = null;
            return false;
        }
    }

    public Serializable read(SocketChannel socket) throws IOException, ClassNotFoundException
    {
        try {
            this.buffer.clear();
            socket.read(this.buffer);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(this.buffer.array()));
            Serializable result = (Serializable) ois.readObject();
            this.buffer.clear();
            return result;
        }
        catch (IOException ioException)
        {
            System.out.println("Server is not available.");
            this.client = null;
            return null;
        }
    }
}
