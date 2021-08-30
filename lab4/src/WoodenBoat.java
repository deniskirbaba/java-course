class WoodenBoat extends WaterVehicle
{
    private int numberOfPaddles;
    private static int liftingCapacity;

    public WoodenBoat(String name, Location location, int maxNumberOfPassengers, TypeWaterVehicle type, int numberOfPaddles)
    {
        super(name, location, maxNumberOfPassengers, type);
        this.numberOfPaddles = numberOfPaddles;
    }

    public void PaddleDrop()
    {
        this.SetNumberOfPaddles(GetNumberOfPaddles()-1);
    }

    public int GetNumberOfPaddles()
    {
        return this.numberOfPaddles;
    }

    public void SetNumberOfPaddles(int numberOfPaddles)
    {
        this.numberOfPaddles = numberOfPaddles;
    }

    @Override
    public ResultOfAction Rotate(Being navigator)
    {
        if (this.GetNumberOfPaddles() >= 2)
        {
            if (navigator.GetCondition() == Condition.clumsiness) {
                this.PaddleDrop();
                System.out.println(navigator.GetName() + " dropped one paddle into the water while trying to turn the boat");
                navigator.SetCondition(Condition.confusion);
                return ResultOfAction.UNSUCCESSFULLY;
            }
            return ResultOfAction.SUCCESSFULLY;
        }
        else if ((this.GetNumberOfPaddles() ==1) & (navigator.GetCondition() == Condition.confusion))
        {
            System.out.println(navigator.GetName() + " in " + navigator.GetCondition() + " tries to row with one paddle, but the boat is spinning around in one place");
            return ResultOfAction.UNSUCCESSFULLY;
        }
        return ResultOfAction.UNSUCCESSFULLY;
    }

    @Override
    public ResultOfAction Move(Being navigator, Location to) throws PaddleException
    {
        if (this.GetLocation() == to)
        {
            System.out.println(this.GetName() + " already in " + to);
            return ResultOfAction.UNSUCCESSFULLY;
        }
        else if (this.GetNumberOfPaddles() >= 2)
        {
            this.MakeMovingSound();
            this.SetLocation(to);
            return ResultOfAction.SUCCESSFULLY;
        }
        else if (this.GetNumberOfPaddles() <= 1)
        {
            if ((this.GetNumberOfPaddles() == 1) & (navigator.GetCondition() == Condition.confusion))
                this.Rotate(navigator);
            else
            {
                throw new PaddleException("there are not enough paddles to move the boat", this.GetNumberOfPaddles());
            }
        }
        return ResultOfAction.UNSUCCESSFULLY;
    }

    @Override
    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }

    @Override
    public void MakeMovingSound()
    {
        System.out.println("the sound of paddles hitting the water");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        WoodenBoat thing = (WoodenBoat) obj;
        return (thing.GetName() == this.GetName() & thing.GetLocation() == this.GetLocation() & thing.GetCurrentNumberOfPassengers() == this.GetCurrentNumberOfPassengers() & thing.GetListOfPassengers() == this.GetListOfPassengers() & this.GetMaxNumberOfPassengers() == thing.GetMaxNumberOfPassengers() & thing.GetType() == this.GetType() & thing.numberOfPaddles == this.numberOfPaddles);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        int factor = 41;
        result = factor * result + (int)(numberOfPaddles - (numberOfPaddles >>> 32));
        return result;
    }

    public static class Drawing
    {
        private int id;
        private int yearOfConstruction;

        public static int getLiftingCapacity()
        {
            return liftingCapacity;
        }
    }
}