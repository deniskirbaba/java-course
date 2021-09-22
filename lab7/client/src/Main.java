/**
 * @author Denis Kirbaba
 * @version 2.3
 * The main class containing the entry point to the program.
 */

public class Main
{
    public static void main(String[] args)
    {
        Client client = new Client();
        client.connect(true);
        while (true)
        {
            if (client.authorize())
                break;
            if (client.start())
                break;
        }
        client.stop();
    }
}