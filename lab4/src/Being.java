abstract class Being implements ActionsOfCreatures
{
    private String name;
    private Location location;
    protected Condition condition;

    public Being(String name, Location location, Condition condition)
    {
        this.name = name;
        this.location = location;
        this.condition = condition;

        ObjectCreated();
    }

    public Being(String name, Location location)
    {
        this.name = name;
        this.location = location;
        this.condition = Condition.normal;

        ObjectCreated();
    }

    public abstract void ObjectCreated();

    public String GetName()
    {
        return this.name;
    }

    public Location GetLocation()
    {
        return this.location;
    }

    public Condition GetCondition()
    {
        return this.condition;
    }

    public void SetName(String name)
    {
        this.name = name;
    }

    public void SetCondition(Condition condition)
    {
        this.condition = condition;
        System.out.println(this.GetName() + " now have " + condition);
    }

    public void SetLocation(Location location)
    {
        this.location = location;
    }

    public ResultOfAction Run(Location to)
    {
        if (this.GetLocation() == to)
        {
            System.out.println(this.GetName() + " already in " + to);
            return ResultOfAction.UNSUCCESSFULLY;
        }
        this.SetLocation(to);
        System.out.println(this.GetName() + " ran to the " + this.GetLocation());
        return ResultOfAction.SUCCESSFULLY;
    }

    public ResultOfAction Approach(Being goal)
    {
        if (this.GetLocation() == goal.GetLocation())
        {
            System.out.println(this.GetName() + " already in " + goal.GetLocation());
            return ResultOfAction.UNSUCCESSFULLY;
        }
        this.SetLocation(goal.GetLocation());
        System.out.println(this.GetName() + " approaching to " + goal.GetName());
        return ResultOfAction.SUCCESSFULLY;
    }

    public ResultOfAction Grab(Being goal)
    {
        if (this.GetLocation() == goal.GetLocation())
        {
            System.out.println(this.GetName() + " grabbing " + goal.GetName());
            return ResultOfAction.SUCCESSFULLY;
        }
        System.out.println(this.GetName() + " far from " + goal.GetName());
        return ResultOfAction.UNSUCCESSFULLY;
    }

    public void SitDown()
    {
        if (this.GetLocation() == Location.boat)
            this.SetLocation(Location.boatStern);
        System.out.println(this.GetName() + " sits at the " + this.GetLocation());
    }

    public void See(Being at)
    {
        System.out.println(this.GetName() + " stared at " + at.GetName());
    }

    public ResultOfAction Shout()
    {
        if (this.GetName() == "hemuliha")
        {
            System.out.println("hemuliha screams, but no one understands what");
            return ResultOfAction.UNSUCCESSFULLY;
        }
        System.out.println(this.GetName() + " screams");
        return ResultOfAction.SUCCESSFULLY;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Being being = (Being) obj;
        return (being.GetName() == this.GetName() & being.GetCondition() == this.GetCondition() & being.GetLocation() == this.GetLocation());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int factor = 37;
        result = factor * result + (name == null ? 0 : name.hashCode());
        result = factor * result + ((condition == null) ? 0 : condition.hashCode());
        result = factor * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        String result = name;
        return result;
    }

    public void Move(Being being)
    {
        this.SetLocation(being.GetLocation());
    }

    public void Move(Thing thing)
    {
        this.SetLocation(thing.GetLocation());
    }

    public void Move(Location location)
    {
        this.SetLocation(location);
    }
}