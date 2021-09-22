import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import commands.*;

public class ClientHandler
{
    private Socket socket;
    private String login;
    ReentrantLock locker;

    public ClientHandler(Socket socket, ReentrantLock locker)
    {
        this.socket = socket;
        this.locker = locker;
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
                this.locker.lock();
                Database database = Database.getInstance();
                int res = database.checkUser(login);
                this.locker.unlock();
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
                    this.locker.lock();
                    boolean status = database.addUser(login, password);
                    this.locker.unlock();
                    if (status)
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
                    this.locker.lock();
                    int rres = database.checkUser(login, password);
                    this.locker.unlock();
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
                        this.locker.lock();
                        info.initialize(manager.getCollectionTypeClass(), manager.getInitializationDate(),
                                manager.getNumberOfObjectsInCollection());
                        this.locker.unlock();
                        oos.writeObject(info);
                        break;
                    case "add":
                        Add add = (Add) command;
                        this.locker.lock();
                        if (!database.add(add))
                        {
                            this.locker.unlock();
                            oos.writeObject("There is a problem with the server, so adding item to collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            this.locker.unlock();
                            oos.writeObject("Object added successfully.");
                        }
                        break;
                    case "show":
                        Show show = (Show) command;
                        this.locker.lock();
                        show.initialize(manager.getMovies());
                        this.locker.unlock();
                        oos.writeObject(show);
                        break;
                    case "countgreaterthanmpaarating":
                        CountGreaterThanMpaaRating countGreaterThanMpaaRating = (CountGreaterThanMpaaRating) command;
                        this.locker.lock();
                        countGreaterThanMpaaRating.setCounter((int) manager.countGreaterThanMpaaRating(countGreaterThanMpaaRating.getMpaaRating()));
                        this.locker.unlock();
                        oos.writeObject(countGreaterThanMpaaRating);
                        break;
                    case "filterlessthanoscarscount":
                        FilterLessThanOscarsCount filterLessThanOscarsCount = (FilterLessThanOscarsCount) command;
                        this.locker.lock();
                        filterLessThanOscarsCount.setMovieArrayList(manager.filterLessThanOscars(filterLessThanOscarsCount.getOscarsCount()));
                        this.locker.unlock();
                        oos.writeObject(filterLessThanOscarsCount);
                        break;
                    case "filterstartswiththename":
                        FilterStartsWithTheName filterStartsWithTheName = (FilterStartsWithTheName) command;
                        this.locker.lock();
                        filterStartsWithTheName.setMovieArrayList(manager.filterStartsWithTheName(filterStartsWithTheName.getStartingString()));
                        this.locker.unlock();
                        oos.writeObject(filterStartsWithTheName);
                        break;
                    case "checkid":
                        CheckId checkId = (CheckId) command;
                        this.locker.lock();
                        checkId.setExist(manager.ifIdExists(checkId.getId()));
                        checkId.setAvailable(manager.ifIdAvailable(checkId.getId(), this.login));
                        this.locker.unlock();
                        oos.writeObject(checkId);
                        break;
                    case "update":
                        Update update = (Update) command;
                        this.locker.lock();
                        if (!database.update(update))
                        {
                            this.locker.unlock();
                            oos.writeObject("There is a problem with the server, so updating item in the collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            this.locker.unlock();
                            oos.writeObject("Object updated successfully.");
                        }
                        break;
                    case "removebyid":
                        RemoveById removeById = (RemoveById) command;
                        this.locker.lock();
                        if (!database.removeById(removeById.getId()))
                        {
                            this.locker.unlock();
                            oos.writeObject("There is a problem with the server, so removing item in the collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            this.locker.unlock();
                            oos.writeObject("Object removed successfully.");
                        }
                        break;
                    case "clear":
                        Clear clear = (Clear) command;
                        this.locker.lock();
                        if (!database.clear(clear.getUser()))
                        {
                            this.locker.unlock();
                            oos.writeObject("There is a problem with the server, so clearing items from the collection failed" +
                                    ", please try again a bit later.");
                        }
                        else
                        {
                            database.load(false);
                            this.locker.unlock();
                            oos.writeObject("Objects cleared successfully.");
                        }
                        break;
                    case "removeat":
                        RemoveAt removeAt = (RemoveAt) command;
                        this.locker.lock();
                        if (!database.removeAt(removeAt))
                        {
                            database.load(false);
                        }
                        this.locker.unlock();
                        oos.writeObject(removeAt);
                        break;
                    case "removegreater":
                        RemoveGreater removeGreater = (RemoveGreater) command;
                        this.locker.lock();
                        if (!database.removeGreater(removeGreater))
                        {
                            database.load(false);
                        }
                        this.locker.unlock();
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
        finally {
            this.locker.unlock();
        }
    }
}