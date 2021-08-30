import java.util.ArrayList;
import java.util.HashMap;

public class Table extends Thing
{
    private HashMap<String, Integer> food = new HashMap<>();
    private int maxCapacity;
    private int numberOfCurrentItems;

    public Table(String name, Location location, int maxCapacity)
    {
        super(name, location);
        this.maxCapacity = maxCapacity;
        this.numberOfCurrentItems = 0;
    }

    @Override
    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }

    public void AddFood(String foodName, int quantity)
    {
        if (quantity > (maxCapacity - numberOfCurrentItems))
        {
            if (food.containsKey(foodName))
                food.put(foodName, food.get(foodName) + maxCapacity - numberOfCurrentItems);
            else
                food.put(foodName, maxCapacity - numberOfCurrentItems);
            numberOfCurrentItems += maxCapacity;
        }
        else
        {
            if (food.containsKey(foodName))
                food.put(foodName, quantity + food.get(foodName));
            else
                food.put(foodName, quantity);
            numberOfCurrentItems += quantity;
        }
    }

    public void DeleteFood(String foodName, int quantity)
    {
        int result = food.get(foodName) - quantity;
        food.put(foodName, result);
    }

    public String ShowAllFood()
    {
        return food.toString();
    }

    public int Contains(String foodName)
    {
        if (food.containsKey(foodName))
            return food.get(foodName);
        else
            return 0;
    }
}
