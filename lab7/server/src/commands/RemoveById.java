package commands;

import java.io.Serializable;
import java.util.InputMismatchException;

public class RemoveById implements Serializable
{
    private int id;

    public boolean setId(String strId)
    {
        try
        {
            int id = Integer.parseInt(strId);
            if (id > 0)
            {
                this.id = id;
                return true;
            }
            else
            {
                System.out.println("ID must be > 0.");
                return false;
            }
        }
        catch (NumberFormatException | InputMismatchException inputMismatchException)
        {
            System.out.println("ID must be integer.");
        }
        return false;
    }

    public int getId()
    {
        return this.id;
    }
}