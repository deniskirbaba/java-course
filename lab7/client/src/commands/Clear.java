package commands;

import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 2.0
 * Class representing the command to remove all objects of a given user from the collection.
 */

public class Clear implements Serializable
{
    private String user;

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getUser()
    {
        return this.user;
    }
}