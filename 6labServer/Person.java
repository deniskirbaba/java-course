import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, describing the person
 */
public class Person implements Serializable
{
    /** Field for storage name */
    private String name; // can't be null or empty
    /** Field for storage height */
    private int height; // value should be > 0
    /** Field for storage weight */
    private float weight; // value should be > 0

    /**
     * Constructor for making a person object
     */
    public Person(String name, int height, float weight)
    {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Method toString
     */
    @Override
    public String toString()
    {
        return "[name:" + this.name + ", height:" + Integer.toString(this.height) + ", weight:" + Float.toString(this.weight) + ']';
    }

    /**
     * Method to convert the object into the CSV format
     */
    public String toCSV()
    {
        return "\"" + this.name + "\"," + Integer.toString(this.height) + "," + Float.toString(this.weight);
    }

    /**
     * Getters
     */
    public String getName()
    {
        return this.name;
    }

    public int getHeight()
    {
        return this.height;
    }

    public float getWeight()
    {
        return this.weight;
    }
}