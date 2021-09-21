import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private int port = 7777;
    private ServerSocket serverSocket;

    public void start()
    {
        try
        {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Server started.");
            while (true)
            {
                Socket client = serverSocket.accept();
                System.out.println("Client connected.");
                Runnable interactWithClient = () ->{
                    ClientHandler clientHandler = new ClientHandler(client);
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