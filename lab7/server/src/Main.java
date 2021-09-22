/**
 * @author Denis Kirbaba
 * @version 2.0
 * The main class containing the entry point to the program.
 * Creates a separate daemon-thread of execution for the functioning of the server.
 * Creates a separate non-daemon-thread for server console.
 */

public class Main
{
    public static void main(String args[])
    {
        Database.getInstance();

        Server server = new Server();
        Runnable startServer = () -> {
            server.start();
        };
        Thread serverThread = new Thread(startServer);
        serverThread.setDaemon(true);
        serverThread.start();

        ServerConsole serverConsole = new ServerConsole();
        serverConsole.start();
    }
}