import java.util.ArrayList;

public class GroupOfPeople
{
    private String name;
    private int numberOfPeople;
    private ArrayList<MoominTroll> beings;

    public GroupOfPeople(String name, int numberOfPeople, Location location)
    {
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        AncientGreekNames[] namesPool = AncientGreekNames.values();
        this.beings = new ArrayList<MoominTroll>(numberOfPeople);
        for (int i = 0; i < numberOfPeople; i++)
        {
            int x = (int) ( Math.random() * 342 );
            this.beings.add(i, new MoominTroll(namesPool[x].toString(), location, MoominTrollCLothes.basicOutfit));
        }
    }

    public MoominTroll GetBeing(int numberOfBeing)
    {
        return beings.get(numberOfBeing);
    }

    public void AddBeing(MoominTroll being)
    {
        this.beings.add(being);
        this.numberOfPeople += 1;
    }

    public void DeleteBeing(MoominTroll being)
    {
        this.beings.remove(being);
        this.numberOfPeople -= 1;
    }

    public String GetName()
    {
        return this.name;
    }

    public int GetNumberOfPeople()
    {
        return numberOfPeople;
    }

    public void SetNumberOfPeople(int numberOfPeople)
    {
        this.numberOfPeople = numberOfPeople;
    }
}