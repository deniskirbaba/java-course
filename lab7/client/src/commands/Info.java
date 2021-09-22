package commands;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Denis Kirbaba
 * @version 2.1
 * Class representing the command displaying information about the collection:
 * collection type, initialization date, number of objects.
 */

public class Info implements Serializable
{

    String collectionClassType;
    LocalDateTime initializationDate;
    int numberOfObjects;

    public void initialize(String collectionClassType, LocalDateTime initializationDate, int numberOfObjects)
    {
        this.collectionClassType = collectionClassType;
        this.initializationDate = initializationDate;
        this.numberOfObjects = numberOfObjects;
    }

    public void print()
    {
        String info = "";
        info += "Collection type: " + this.collectionClassType + '\n';
        info += "Collection initialization date: " + this.initializationDate.toString() + '\n';
        info += "The number of objects in the collection: " + Integer.toString(this.numberOfObjects);
        System.out.println(info);
    }
}