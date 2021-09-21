package commands;

import java.io.Serializable;
import java.util.InputMismatchException;

public class RemoveAt implements Serializable
{
    private int index;
    private String user;
    private String result;

    public boolean setIndex(String strIndex)
    {
        try
        {
            int index = Integer.parseInt(strIndex);
            if (index > 0)
            {
                this.index = index;
                return true;
            }
            else
            {
                System.out.println("Index must be > 0.");
                return false;
            }
        }
        catch (NumberFormatException | InputMismatchException inputMismatchException)
        {
            System.out.println("Index must be integer.");
        }
        return false;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public int getIndex()
    {
        return this.index;
    }

    public String getUser()
    {
        return this.user;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public void printResult()
    {
        System.out.println(this.result);
    }
}