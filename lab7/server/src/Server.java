import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Server
{
    private int port = 7777;
    private ServerSocket serverSocket;

    public void start()
    {
        ReentrantLock locker = new ReentrantLock();
        try
        {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Server started.");
            while (true)
            {
                Socket client = serverSocket.accept();
                System.out.println("Client connected.");
                Runnable interactWithClient = () ->{
                    ClientHandler clientHandler = new ClientHandler(client, locker);
                    clientHandler.start();
                };
                Thread thread = new Thread(interactWithClient);
                thread.setDaemon(true);
                thread.start();
            }
        }
        catch (IOException e)
        {
            System.out.println("Failed to create server.");
        }
    }
}