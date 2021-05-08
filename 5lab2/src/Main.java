import java.io.IOException;

/**
 * Main class
 * @author Denis Kirbaba
 * @version 1.0
 * @param args - passing the path to the data file via the command line argument
 * @throws IOException
 */

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Commander commander = new Commander(new Manager(args[0]));
        commander.interactiveMod();
    }
}
