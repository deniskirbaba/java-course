abstract class Thing
{
    private String name;
    private Location location;

    public Thing(String name, Location location)
    {
        this.location = location;
        this.name = name;

        ObjectCreated();
    }

    public abstract void ObjectCreated();

    public Location GetLocation()
    {
        return this.location;
    }

    public void SetLocation(Location location)
    {
        this.location = location;
    }

    public String GetName()
    {
        return this.name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Thing thing = (Thing) obj;
        return (thing.GetName() == this.GetName() & thing.GetLocation() == this.GetLocation());
    }

    @Override
    public int hashCode() {
        int result = 19;
        int factor = 39;
        result = factor * result + (name == null ? 0 : name.hashCode());
        result = factor * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return name;
    }
}