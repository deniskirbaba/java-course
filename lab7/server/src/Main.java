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