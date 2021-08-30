class Hemul extends Being
{
    private HemulClothes clothes;
    private HemulEmployment employment;

    public Hemul(String name, Location location, HemulClothes clothes, HemulEmployment employment)
    {
        super(name, location);
        this.clothes = clothes;
        this.employment = employment;
    }

    public Hemul(String name, Location location, Condition condition, HemulClothes clothes, HemulEmployment employment)
    {
        super(name, location, condition);
        this.clothes = clothes;
        this.employment = employment;
    }

    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Hemul being = (Hemul) obj;
        return (being.GetName() == this.GetName() & being.GetCondition() == this.GetCondition() & being.GetLocation() == this.GetLocation() & being.clothes == this.clothes & being.employment == this.employment);
    }

    @Override
    public int hashCode()
    {
        int factor = 38;
        int result = super.hashCode();
        result = result * factor + ((clothes == null) ? 0 : clothes.hashCode());
        result = result * factor + ((employment == null) ? 0 : employment.hashCode());
        return result;
    }
}