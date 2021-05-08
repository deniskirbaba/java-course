/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, describing the object coordinates
 */
public class Coordinates
{
    /** Field for storage X coordinate */
    private Long x; // can't be null, max value: 414
    /** Field for storage Y coordinate */
    private long y; // max value: 211

    /**
     * Constructor for making a coordination object
     */
    public Coordinates(Long x, long y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Method toString
     */
    @Override
    public String toString()
    {
        return "[x:" + Long.toString(this.x) + ", y:" + Long.toString(this.y) + ']';
    }

    /**
     * Method to convert the object into the CSV format
     */
    public String toCSV()
    {
        return Long.toString(this.x) + "," + Long.toString(this.y);
    }

    /**
     * Getters
     */
    public Long getX()
    {
        return this.x;
    }
    public long getY()
    {
        return this.y;
    }
}