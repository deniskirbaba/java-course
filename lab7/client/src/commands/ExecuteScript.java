package commands;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;

public class ExecuteScript implements Serializable
{
    private String filePath;
    private static ArrayList<String> filePaths = new ArrayList<>();
    private String login;

    public ExecuteScript(String login)
    {
        this.login = login;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public static void resetFilePaths()
    {
        filePaths.clear();
    }

    public boolean checkFileForWriteRead(boolean writeFlag) throws IOException
    {
        if (this.filePath == null) {
            System.out.println("File path is null.");
            return false;
        }

        File file = new File(this.filePath);

        if (!(file.exists()))
        {
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

    public boolean start(ObjectOutputStream oos, ObjectInputStream ois)
    {
        if (!filePaths.contains(filePath))
        {
            filePaths.add(filePath);

            int numberOfFields = 10;

            try (FileReader fileReader = new FileReader(this.filePath))
            {
                ArrayList<String> contents = new ArrayList<>();

                int c;
                String string = "";
                while ((c = fileReader.read()) != -1)
                {
                    char nextChar = (char) c;
                    if (nextChar == '\n')
                    {
                        contents.add(string);
                        string = "";
                    }
                    else
                    {
                        string += nextChar;
                    }
                }
                contents.add(string);

                for (int i = 0; i < contents.size(); i++)
                {
                    if (contents.get(i).equals(""))
                        contents.remove(i);
                }

                String[] commandArguments;

                for (int i = 0; i < contents.size(); i++)
                {
                    commandArguments = contents.get(i).trim().split("\\s+");

                    try
                    {
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
                            case "exit":
                                return true;
                            case "add":
                                String[] args = new String[numberOfFields];
                                for (int j = 0; j < numberOfFields; j++)
                                    args[j] = contents.get(i + j + 1).trim();
                                i += numberOfFields;
                                Add add = new Add(this.login, args);
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
                                    if (this.checkId(commandArguments[1], oos, ois))
                                    {
                                        String[] uargs = new String[numberOfFields];
                                        for (int j = 0; j < numberOfFields; j++)
                                            uargs[j] = contents.get(i + j + 1).trim();
                                        i += numberOfFields;
                                        Update update = new Update(uargs);
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
                                    if (this.checkId(commandArguments[1], oos, ois))
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
                                String[] rargs = new String[numberOfFields];
                                for (int j = 0; j < numberOfFields; j++)
                                    rargs[j] = contents.get(i + j + 1).trim();
                                i += numberOfFields;
                                RemoveGreater removeGreater = new RemoveGreater(this.login, rargs);
                                oos.writeObject(removeGreater);
                                removeGreater = (RemoveGreater) ois.readObject();
                                removeGreater.printResult();
                                break;
                            case "execute_script":
                                if (commandArguments.length != 1)
                                {
                                    ExecuteScript executeScript = new ExecuteScript(this.login);
                                    executeScript.setFilePath(commandArguments[1]);
                                    if (executeScript.checkFileForWriteRead(false))
                                    {
                                        System.out.println("Executing commands from " + commandArguments[1]);
                                        executeScript.start(oos, ois);
                                    }
                                }
                                else
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
                        break;
                    }
                    catch (NumberFormatException | IndexOutOfBoundsException exception)
                    {
                        System.out.println("Format exception.");
                    }
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("File not found.");
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
        return false;
    }

    public boolean checkId(String strId, ObjectOutputStream oos, ObjectInputStream ois)
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