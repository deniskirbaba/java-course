import java.io.Serializable;
import java.util.ArrayList;

public class Command implements Serializable {
    String name;
    ArrayList<String> arguments;

    public Command(String name, ArrayList<String> arguments)
    {
        this.name = name;
        this.arguments = arguments;
    }

    public Command()
    {
        this.name = null;
        this.arguments = new ArrayList<String>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArguments(String name, Coordinates coordinates, long oscarsCount, Integer goldenPalmCount,
                             MovieGenre genre, MpaaRating mpaaRating, Person screenwriter)
    {
        this.arguments.add(name);
        this.arguments.add(coordinates.getX().toString());
        this.arguments.add(Long.toString(coordinates.getY()));
        this.arguments.add(Long.toString(oscarsCount));
        this.arguments.add(goldenPalmCount.toString());
        this.arguments.add(genre.toString());
        this.arguments.add(mpaaRating.toString());
        this.arguments.add(screenwriter.getName());
        this.arguments.add(Integer.toString(screenwriter.getHeight()));
        this.arguments.add(Float.toString(screenwriter.getWeight()));
    }

    public void setArguments(String id)
    {
        this.arguments.add(id);
    }

    public void setArguments(String id, String name, Coordinates coordinates, long oscarsCount, Integer goldenPalmCount,
                             MovieGenre genre, MpaaRating mpaaRating, Person screenwriter)
    {
        this.arguments.add(id);
        this.arguments.add(name);
        this.arguments.add(coordinates.getX().toString());
        this.arguments.add(Long.toString(coordinates.getY()));
        this.arguments.add(Long.toString(oscarsCount));
        this.arguments.add(goldenPalmCount.toString());
        this.arguments.add(genre.toString());
        this.arguments.add(mpaaRating.toString());
        this.arguments.add(screenwriter.getName());
        this.arguments.add(Integer.toString(screenwriter.getHeight()));
        this.arguments.add(Float.toString(screenwriter.getWeight()));
    }

    public String toString()
    {
        return this.name;
    }
}
