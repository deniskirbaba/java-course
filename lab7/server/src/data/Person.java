package data;

import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 1.5
 * An enum class that represents a person.
 */

public class Person implements Serializable
{
    private String name;
    private int height;
    private float weight;

    public Person(String name, int height, float weight)
    {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public String getName()
    {
        return this.name;
    }

    public int getHeight() {
        return this.height;
    }

    public float getWeight()
    {
        return this.weight;
    }

    @Override
    public String toString()
    {
        return "[name:" + this.name + ", height:" + Integer.toString(this.height) + ", weight:" + Float.toString(this.weight) + ']';
    }
}