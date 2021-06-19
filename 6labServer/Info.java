import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, containing info command
 */
public class Info implements Serializable
{
    /** Field for collection class type */
    Class collectionClassType;

    /** Field for initialization date */
    LocalDateTime initializationDate;

    /** Field for number of objects in the collection */
    int numberOfObjects;

    /**
     * Function for initializing info object
     */
    void initialize(Class collectionClassType, LocalDateTime initializationDate, int numberOfObjects)
    {
        this.collectionClassType = collectionClassType;
        this.initializationDate = initializationDate;
        this.numberOfObjects = numberOfObjects;
    }

    /**
     * Function for printing info
     */
    void print()
    {
        String info = "";
        info += "Collection type: " + this.collectionClassType.toString() + '\n';
        info += "Collection initialization date: " + this.initializationDate.toString() + '\n';
        info += "The number of objects in the collection: " + Integer.toString(this.numberOfObjects);
        System.out.println(info);
    }
}