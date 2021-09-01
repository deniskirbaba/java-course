import java.io.Serializable;

public class CheckId implements Serializable
{
    private long id;
    private boolean isExist;

    public CheckId(long id)
    {
        this.id = id;
        this.isExist = false;
    }

    public boolean isExist()
    {
        return this.isExist;
    }
    public void setExist(boolean isExist)
    {
        this.isExist = isExist;
    }
    public long getId()
    {
        return this.id;
    }
}
