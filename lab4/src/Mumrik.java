class Mumrik extends Being
{
    public Mumrik(String name, Location location)
    {
        super(name, location);
    }

    @Override
    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }
}