class WaterVehicle extends Thing implements WaterVehicleActions
{
    private int maxNumberOfPassengers;
    private TypeWaterVehicle type;
    private String[] listOfPassengers;
    private int currentNumberOfPassengers;

    public WaterVehicle(String name, Location location,int maxNumberOfPassengers, TypeWaterVehicle type)
    {
        super(name, location);
        this.maxNumberOfPassengers = maxNumberOfPassengers;
        this.type = type;
        this.listOfPassengers = new String[this.maxNumberOfPassengers];
        this.currentNumberOfPassengers = 0;
    }

    public void ObjectCreated()
    {
        System.out.println("water vehicle has created");
    }

    public String[] GetListOfPassengers()
    {
        return this.listOfPassengers;
    }

    public int GetMaxNumberOfPassengers()
    {
        return this.maxNumberOfPassengers;
    }

    public TypeWaterVehicle GetType()
    {
        return this.type;
    }

    public int GetCurrentNumberOfPassengers()
    {
        return this.currentNumberOfPassengers;
    }

    public void MakeMovingSound()
    {
        System.out.println("moving sound");
    }

    public ResultOfAction LandingPeople(Hemul passenger)
    {
        if (this.currentNumberOfPassengers < this.maxNumberOfPassengers)
        {
            this.listOfPassengers[currentNumberOfPassengers] = passenger.GetName();
            this.currentNumberOfPassengers += 1;

            System.out.println(passenger.GetName() + " boarded");

            return ResultOfAction.SUCCESSFULLY;
        }

        else
        {
            System.out.println(passenger.GetName() + " didn't boarded, because all the seats on board are already taken");

            return ResultOfAction.UNSUCCESSFULLY;
        }
    }

    public ResultOfAction LandingPeople(MoominTroll passenger)
    {
        if (this.currentNumberOfPassengers < this.maxNumberOfPassengers)
        {
            this.listOfPassengers[currentNumberOfPassengers] = passenger.GetName();
            this.currentNumberOfPassengers += 1;

            System.out.println(passenger.GetName() + " boarded");

            return ResultOfAction.SUCCESSFULLY;
        }

        else
        {
            System.out.println(passenger.GetName() + " didn't boarded, because all seats on board are already taken");

            return ResultOfAction.UNSUCCESSFULLY;
        }
    }

    public ResultOfAction Move(Being navigator, Location to) throws PaddleException
    {
        if (this.GetLocation() == to)
        {
            System.out.println(this.GetName() + " already in " + to);
            return ResultOfAction.UNSUCCESSFULLY;
        }
        this.MakeMovingSound();
        this.SetLocation(to);

        return ResultOfAction.SUCCESSFULLY;
    }

    public ResultOfAction Rotate(Being navigator)
    {
        return ResultOfAction.SUCCESSFULLY;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        WaterVehicle thing = (WaterVehicle) obj;
        return (thing.GetName() == this.GetName() & thing.GetLocation() == this.GetLocation() & thing.currentNumberOfPassengers == this.currentNumberOfPassengers & thing.listOfPassengers == this.listOfPassengers & this.maxNumberOfPassengers == thing.maxNumberOfPassengers & thing.type == this.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        int factor = 39;
        result = factor * result + (int)(currentNumberOfPassengers - (currentNumberOfPassengers >>> 32));
        result = factor * result + ((type == null) ? 0 : type.hashCode());
        for( String b : listOfPassengers )
            result = factor * result + ((type == null) ? 0 : type.hashCode());
        result = factor * result + (int)(maxNumberOfPassengers - (maxNumberOfPassengers >>> 32));
        return result;
    }
}