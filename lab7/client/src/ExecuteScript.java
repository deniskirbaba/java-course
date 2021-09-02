import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class ExecuteScript implements Serializable {
    private String filePath;
    /**
     * Field for containing file path in execute_script method
     */
    private static ArrayList<String> filePaths = new ArrayList<>();
    private ByteBuffer buffer;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static void resetFilePaths()
    {
        filePaths.clear();
    }

    /**
     * Method for checking a file for writing or reading
     *
     * @param writeFlag - flag for choosing between writing (true) and reading (false)
     */
    public boolean checkFileForWriteRead(boolean writeFlag) throws IOException {
        if (this.filePath == null) {
            System.out.println("File path is null.");
            return false;
        }

        File file = new File(this.filePath);

        if (!(file.exists())) {
            System.out.println("File at the specified path does not exist.");
            return false;
        }

        if (writeFlag) {
            if (!file.canWrite()) {
                System.out.println("File cannot be write. Add permission to view the file or enter the path to a new file.");
                return false;
            }
        }

        if (!writeFlag) {
            if (!file.canRead()) {
                System.out.println("File cannot be read. Add permission to view the file or enter the path to a new file.");
                return false;
            }
        }
        return true;
    }

    public void start(SocketChannel client)
    {
        this.buffer = ByteBuffer.allocate(32768);
        if (!filePaths.contains(filePath))
        {
            this.filePaths.add(filePath);

            int numberOfFields = Movie.class.getDeclaredFields().length;

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
                int nextByte = 0;
                String s = "";

                ArrayList<String> contents = new ArrayList<>();

                while ((nextByte = bis.read()) != -1) {
                    char nextChar = (char) nextByte;
                    if (nextChar == '\n') {
                        contents.add(s);
                        s = "";
                    } else {
                        s += nextChar;
                    }
                }
                contents.add(s);

                for (int i = 0; i < contents.size(); i++) {
                    if (contents.get(i).equals(""))
                        contents.remove(i);
                }

                String commandArguments[];

                for (int i = 0; i < contents.size(); i++) {
                    commandArguments = contents.get(i).trim().split("\\s+"); // elimination of whitespaces and tabs

                    try {
                        switch (commandArguments[0]) {
                            case "":
                                break;
                            case "help":
                                Help help = new Help();
                                help.print();
                                break;
                            case "info":
                                Info info = new Info();
                                if (!send(client, info))
                                    break;
                                info = (Info) this.read(client);
                                if (info == null)
                                    break;
                                info.print();
                                break;
                            case "add":
                                String[] args = new String[numberOfFields];
                                for (int j = 0; j < numberOfFields; j++)
                                    args[j] = contents.get(i + j + 1).trim();
                                i += numberOfFields;
                                Add add = new Add(args);
                                if (!send(client, add))
                                    break;
                                break;
                            case "show":
                                Show show = new Show();
                                if (!send(client, show))
                                    break;
                                show = (Show) this.read(client);
                                if (show == null)
                                    break;
                                show.print();
                                break;
                            case "update":
                                if (commandArguments.length != 1) {
                                    if (this.checkId(commandArguments[1], client)) {
                                        String[] argsUpdate = new String[numberOfFields];
                                        for (int j = 0; j < numberOfFields; j++) {
                                            argsUpdate[j] = contents.get(i + j + 1).trim();
                                        }
                                        i += numberOfFields;
                                        Update update = new Update(argsUpdate);
                                        if (update.setID(commandArguments[1])) {
                                            if (!send(client, update))
                                                break;
                                            String output = (String) this.read(client);
                                            if (output == null)
                                                break;
                                            System.out.println(output);
                                        }
                                    } else
                                        System.out.println("This function requires the parameter.");
                                    break;
                                }
                                else
                                    break;
                        case "remove_by_id":
                            if (commandArguments.length != 1)
                            {
                                RemoveById removeById = new RemoveById();
                                if (removeById.setId(commandArguments[1]))
                                {
                                    if (!send(client, removeById))
                                        break;
                                    String output = (String) this.read(client);
                                    if (output == null)
                                        break;
                                    System.out.println(output);
                                }
                            } else
                                System.out.println("This function requires the parameter.");
                            break;
                        case "clear":
                            Clear clear = new Clear();
                            if (!send(client, clear))
                                break;
                            String output = (String) this.read(client);
                            if (output == null)
                                break;
                            System.out.println(output);
                            break;
                        case "remove_at":
                            if (commandArguments.length != 1) {
                                RemoveAt removeAt = new RemoveAt();
                                if (removeAt.setIndex(commandArguments[1])) {
                                    if (!send(client, removeAt))
                                        break;
                                    String remove_at = (String) this.read(client);
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
                            if (!send(client, removeFirst))
                                break;
                            String remove_first = (String) this.read(client);
                            if (remove_first == null)
                                break;
                            System.out.println(remove_first);
                            break;
                        case "count_greater_than_mpaa_rating":
                            if (commandArguments.length != 1) {
                                CountGreaterThanMpaaRating countGreaterThanMpaaRating = new CountGreaterThanMpaaRating();
                                if (countGreaterThanMpaaRating.setMpaaRating(commandArguments[1])) {
                                    if (!send(client, countGreaterThanMpaaRating))
                                        break;
                                    String count_greater_than_mpaa_rating = (String) this.read(client);
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
                                    if (!send(client, filterLessThanOscarsCount))
                                        break;
                                    filterLessThanOscarsCount = (FilterLessThanOscarsCount) this.read(client);
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
                                    if (!send(client, filterStartsWithTheName))
                                        break;
                                    filterStartsWithTheName = (FilterStartsWithTheName) this.read(client);
                                    if (filterStartsWithTheName == null)
                                        break;
                                    filterStartsWithTheName.print();
                                } else
                                    System.out.println("This function requires the parameter.");
                                break;
                        case "remove_greater":
                            String[] argsGreater = new String[numberOfFields];
                            for (int j = 0; j < numberOfFields; j++)
                                argsGreater[j] = contents.get(i + j + 1).trim();
                            i += numberOfFields;
                            RemoveGreater removeGreater = new RemoveGreater(argsGreater);
                            if (!send(client, removeGreater))
                                break;
                            break;
                        case "execute_script":
                            if (commandArguments.length != 1)
                            {
                                ExecuteScript executeScript = new ExecuteScript();
                                executeScript.setFilePath(commandArguments[1]);
                                if (executeScript.checkFileForWriteRead(false))
                                {
                                    System.out.println("Executing commands from " + commandArguments[1]);
                                    executeScript.start(client);
                                }
                            }
                            else
                                System.out.println("This function requires the parameter.");
                            break;
                            default: {
                                System.out.println("Unknown command. Write 'help' for reference.");
                            }
                        }
                    }
                    catch (NumberFormatException | IndexOutOfBoundsException exception)
                    {
                        System.out.println("Format exception.");
                    }
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("File not found.");
            }
            catch (SocketException socketException)
            {
                System.out.println("Server is not available.");
            }
            catch (IOException ioException)
            {
                System.out.println("File error.");
            } catch (ClassNotFoundException classNotFoundException) {
                System.out.println("Parsing exception.");
            }
        } else {
            System.out.println("Recursion detected, commands from files were run only once.");
            ExecuteScript.filePaths.clear();
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
            return false;
        }
    }

    public Serializable read(SocketChannel socket) throws IOException, ClassNotFoundException
    {
        try
        {
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
            return null;
        }
    }

    public boolean checkId(String strId, SocketChannel client)
    {
        try
        {
            int id = Integer.parseInt(strId);
            if (id > 0)
            {
                CheckId checkId = new CheckId(id);
                this.send(client, checkId);
                checkId = (CheckId) this.read(client);
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
}
