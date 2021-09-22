package data;

import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 1.1
 * A class that represents a coordinate.
 */

public class Coordinates implements Serializable
{
    private Long x;
    private final long y;

    public Coordinates(Long x, long y)
    {
        this.x = x;
        this.y = y;
    }

    public Long getX()
    {
        return this.x;
    }

    public long getY()
    {
        return this.y;
    }

    @Override
    public String toString()
    {
        return "[x:" + Long.toString(this.x) + ", y:" + Long.toString(this.y) + ']';
    }
}