package commands;

import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 2.1
 * Class representing the command to check the object's id for existence (isExist) and for user belonging (isAvailable).
 */

public class CheckId implements Serializable
{
    private long id;
    private boolean isExist;
    private boolean isAvailable;

    public CheckId(long id)
    {
        this.id = id;
        this.isExist = false;
    }

    public boolean isExist()
    {
        return this.isExist;
    }

    public boolean isAvailable()
    {
        return this.isAvailable;
    }

    public void setExist(boolean isExist)
    {
        this.isExist = isExist;
    }

    public void setAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }

    public long getId()
    {
        return this.id;
    }
}