import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Server class that connects the client to the server and performs receiving and sending messages from clients
 */
public class Server extends Thread
{
    /** OutputStream */
    private ObjectOutputStream out;
    /** Collection manager */
    private final Manager manager;
    /** Port number */
    private final int port;
    private ByteBuffer buffer;

    public Server(String filePath, int port)
    {
        this.manager = new Manager(filePath);
        this.port = port;
    }

    public void run()
    {
        try
        {
            Selector selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", this.port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            this.buffer = ByteBuffer.allocate(32768);

            System.out.println("Started: " + serverSocketChannel.getLocalAddress());

            try
            {
                while (true)
                {
                        selector.select();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectedKeys.iterator();

                            while (iterator.hasNext()) {
                                SelectionKey key = iterator.next();
                                try {
                                    if (key.isAcceptable()) {
                                        SocketChannel client = serverSocketChannel.accept();
                                        System.out.println("Connected: " + client.getLocalAddress());
                                        client.configureBlocking(false);
                                        client.register(selector, SelectionKey.OP_READ);
                                    }

                                    if (key.isReadable()) {
                                        SocketChannel client = (SocketChannel) key.channel();
                                        Object command = this.read(client);
                                        this.applyCommand(command, client);
                                    }
                                }
                                catch (IOException ioException)
                                {
                                    System.out.println("Client was disconnected.");
                                    key.cancel();
                                }
                            }
                            iterator.remove();
                }
            }
            catch (SocketTimeoutException socketTimeoutException)
            {
                System.out.println("The timeout has been reached.");
            }
            catch (IOException ioException)
            {
                System.out.println("Client was disconnected.");
            }
            catch (SecurityException securityException)
            {
                System.out.println("Security exception while connecting with client.");
            } catch (ClassNotFoundException classNotFoundException)
            {
                System.out.println("Error while reading info from user.");
            }
        }
        catch (SecurityException securityException)
        {
            System.out.println("Security exception while creating the server socket.");
        }
        catch (IOException ioException)
        {
            System.out.println("Unable to create server socket at port: " + port);
        }
    }

    public void applyCommand(Object command, SocketChannel client) throws IOException
    {
        String commandName = command.getClass().getName();

        switch (commandName.toLowerCase())
        {
            case "help":
                Help help = (Help) command;
                help.initialize();
                send(client, help);
                break;
            case "info":
                Info info = (Info) command;
                info.initialize(this.manager.getCollectionTypeClass() ,this.manager.getInitializationDate(), this.manager.getNumberOfObjectsInCollection());
                send(client, info);
                break;
            case "add":
                Add add = (Add) command;
                manager.add(add.getMovie());
                manager.save();
                break;
            case "show":
                Show show = (Show) command;
                show.initialize(this.manager.getMovies());
                send(client, show);
                break;
            case "update":
                Update update = (Update) command;
                send(client, this.manager.update(update.getMovie(), update.getId()));
                this.manager.save();
                break;
            case "removebyid":
                RemoveById removeById = (RemoveById) command;
                send(client, this.manager.removeById(removeById.getId()));
                this.manager.save();
                break;
            case "clear":
                Clear clear = (Clear) command;
                send(client, manager.clear());
                manager.save();
                break;
            case "removeat":
                RemoveAt removeAt = (RemoveAt) command;
                send(client, this.manager.removeAt(removeAt.getIndex()));
                this.manager.save();
                break;
            case "countgreaterthanmpaarating":
                CountGreaterThanMpaaRating countGreaterThanMpaaRating = (CountGreaterThanMpaaRating) command;
                send(client, this.manager.countGreaterThanMpaaRating(countGreaterThanMpaaRating.getMpaaRating()));
                break;
            case "filterlessthanoscarscount":
                FilterLessThanOscarsCount filterLessThanOscarsCount = (FilterLessThanOscarsCount) command;
                filterLessThanOscarsCount.setMovieArrayList(this.manager.filterLessThanOscars(filterLessThanOscarsCount.getOscarsCount()));
                send(client, filterLessThanOscarsCount);
                break;
            case "filterstartswiththename":
                FilterStartsWithTheName filterStartsWithTheName = (FilterStartsWithTheName) command;
                filterStartsWithTheName.setMovieArrayList(manager.filterStartsWithTheName(filterStartsWithTheName.getStartingString()));
                send(client, filterStartsWithTheName);
                break;
            case "removegreater":
                RemoveGreater removeGreater = (RemoveGreater) command;
                manager.removeGreater(removeGreater.getMovie());
                break;
            case "checkid":
                CheckId checkId = (CheckId) command;
                checkId.setExist(manager.ifIdExists(checkId.getId()));
                send(client, checkId);
                break;
        }
    }

    public static void send(SocketChannel socket, Serializable serializable) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(serializable);
        oos.close();
        ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
        socket.write(wrap);
    }

    public Serializable read(SocketChannel socket) throws IOException, ClassNotFoundException
    {
        this.buffer.clear();
        socket.read(this.buffer);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(this.buffer.array()));
        Serializable result = (Serializable) ois.readObject();
        this.buffer.clear();
        return result;
    }

    public Manager getManager()
    {
        return this.manager;
    }
}
