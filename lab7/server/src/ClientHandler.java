import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import commands.*;

public class ClientHandler
{
    private Socket socket;
    private String login;

    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    public void start()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()))
        {
            while (true)
            {
                String login = (String)ois.readObject();
                // if type = true this is sign up, otherwise log in
                boolean type = (boolean)ois.readObject();
                Database database = Database.getInstance();
                int res = database.checkUser(login);
                if (res == 0)
                {
                    oos.writeObject(res);
                    if (!type)
                        continue;
                }
                else if (res == 1)
                {
                    oos.writeObject(res);
                    if (type)
                        continue;
                }
                else
                {
                    oos.writeObject(res);
                    continue;
                }

                String password = (String)ois.readObject();
                if (type)
                {
                    if (database.addUser(login, password))
                    {
                        oos.writeObject(true);
                        this.login = login;
                        break;
                    }
                    else
                    {
                        oos.writeObject(false);
                        continue;
                    }
                }
                else
                {
                    int rres = database.checkUser(login, password);
                    if (rres == 1) {
                        oos.writeObject(rres);
                        this.login = login;
                        break;
                    }
                    else if (rres == 0)
                    {
                        oos.writeObject(rres);
                        continue;
                    }
                    else
                    {
                        oos.writeObject(rres);
                        continue;
                    }
                }
            }

            while (true)
            {
                Object command = ois.readObject();
                String commandName = command.getClass().getName();
                commandName = commandName.split("\\.")[1];
                Manager manager = Manager.getInstance();
                Database database = Database.getInstance();
                switch (commandName.toLowerCase())
                {
                    case "info":
                        Info info = (Info) command;
                        info.initialize(manager.getCollectionTypeClass(), manager.getInitializationDate(),
                                manager.getNumberOfObjectsInCollection());
                        oos.writeObject(info);
                        break;
                    case "add":
                        Add add = (Add) command;
                        if (!database.add(add))
                        {
                            oos.writeObject("There is a problem with the server, so adding item to collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            oos.writeObject("Object added successfully.");
                        }
                        break;
                    case "show":
                        Show show = (Show) command;
                        show.initialize(manager.getMovies());
                        oos.writeObject(show);
                        break;
                    case "countgreaterthanmpaarating":
                        CountGreaterThanMpaaRating countGreaterThanMpaaRating = (CountGreaterThanMpaaRating) command;
                        countGreaterThanMpaaRating.setCounter((int) manager.countGreaterThanMpaaRating(countGreaterThanMpaaRating.getMpaaRating()));
                        oos.writeObject(countGreaterThanMpaaRating);
                        break;
                    case "filterlessthanoscarscount":
                        FilterLessThanOscarsCount filterLessThanOscarsCount = (FilterLessThanOscarsCount) command;
                        filterLessThanOscarsCount.setMovieArrayList(manager.filterLessThanOscars(filterLessThanOscarsCount.getOscarsCount()));
                        oos.writeObject(filterLessThanOscarsCount);
                        break;
                    case "filterstartswiththename":
                        FilterStartsWithTheName filterStartsWithTheName = (FilterStartsWithTheName) command;
                        filterStartsWithTheName.setMovieArrayList(manager.filterStartsWithTheName(filterStartsWithTheName.getStartingString()));
                        oos.writeObject(filterStartsWithTheName);
                        break;
                    case "checkid":
                        CheckId checkId = (CheckId) command;
                        checkId.setExist(manager.ifIdExists(checkId.getId()));
                        checkId.setAvailable(manager.ifIdAvailable(checkId.getId(), this.login));
                        oos.writeObject(checkId);
                        break;
                    case "update":
                        Update update = (Update) command;
                        if (!database.update(update))
                        {
                            oos.writeObject("There is a problem with the server, so updating item in the collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            oos.writeObject("Object updated successfully.");
                        }
                        break;
                    case "removebyid":
                        RemoveById removeById = (RemoveById) command;
                        if (!database.removeById(removeById.getId()))
                        {
                            oos.writeObject("There is a problem with the server, so removing item in the collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            oos.writeObject("Object removed successfully.");
                        }
                        break;
                    case "clear":
                        Clear clear = (Clear) command;
                        if (!database.clear(clear.getUser()))
                        {
                            oos.writeObject("There is a problem with the server, so clearing items from the collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            oos.writeObject("Objects cleared successfully.");
                        }
                        break;
                    case "removeat":
                        RemoveAt removeAt = (RemoveAt) command;
                        if (!database.removeAt(removeAt))
                        {
                            database.load(false);
                        }
                        oos.writeObject(removeAt);
                        break;
                    case "removegreater":
                        RemoveGreater removeGreater = (RemoveGreater) command;
                        if (!database.removeGreater(removeGreater))
                        {
                            database.load(false);
                        }
                        oos.writeObject(removeGreater);
                        break;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Client disconnected.");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Data transfer error.");
        }
    }
}