import java.io.Serializable;

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