import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Class, containing help command
 */
public class Help implements Serializable
{
    /** Field for manual */
    private HashMap<String, String> manual;

    /**
     * Function for initializing help-manual
     */
    public void initialize()
    {
        this.manual = new HashMap<>();

        this.manual.put("help", " - display manual for available commands");
        this.manual.put("info", " - print collection information");
        this.manual.put("show", " - print all elements of the collection");
        this.manual.put("add {element}", " - add new element in collection");
        this.manual.put("update id {element}", " - update the value of the collection by id");
        this.manual.put("remove_by_id id", " - remove an item from the collection by id");
        this.manual.put("clear", " - clear the collection");
        this.manual.put("execute_script file_name", " - read and execute the script from the specified file");
        this.manual.put("exit", " - end the program");
        this.manual.put("remove_at index", " - remove the element at the given position");
        this.manual.put("remove_first", " - remove first element from collection");
        this.manual.put("remove_greater {element}", " - remove all items from the collection that are greater than the given one");
        this.manual.put("count_greater_than_mpaa_rating mpaaRating", " - print the number of elements, the value of the mpaaRating field of which is greater than the given one");
        this.manual.put("filter_starts_with_the_name name", " - display elements whose name field value begins with a given substring");
        this.manual.put("filter_less_than_oscars_count oscarsCount", " - display elements whose oscarsCount field value is less than the specified one");
    }

    /**
     * Function for printing help-manual
     */
    public void print()
    {
        for (Map.Entry<String, String> entry : manual.entrySet())
        {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }

    /**
     * Getter for help-manual
     */
    public HashMap<String, String> getManual()
    {
        return this.manual;
    }
}