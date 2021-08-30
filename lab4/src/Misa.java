class Misa extends Being
{
    public Misa(String name, Location location)
    {
        super(name, location);
    }

    @Override
    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }
}