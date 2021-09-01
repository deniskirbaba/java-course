import java.io.IOException;

/**
 * Main class
 * @author Denis Kirbaba
 * @version 1.0
 */

public class Main
{
    public static void main(String[] args) throws IOException
    {
        if (args.length > 0)
        {
            Commander commander = new Commander(new Manager(args[0]));
            commander.interactiveMod();
        }
        else
        {
            System.out.println("No argument given. The file path is required as a command line argument.");
            Commander commander = new Commander(new Manager());
            commander.interactiveMod();
        }
    }
}
