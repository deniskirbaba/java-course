import java.net.*;
import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        String hostName = "localhost";
        int port  = 8081;

        Client client = new Client();
        try
            {
                try
                {
                    client.startConnection(hostName, port,true);
                    client.interactiveMode();
                    client.stopConnection();
                }
                catch (UnknownHostException unknownHostException)
                {
                    System.out.println("Invalid host.");
                }
            }
        catch (UnknownHostException unknownHostException)
        {
            System.out.println("Invalid host");
        }
    }
}