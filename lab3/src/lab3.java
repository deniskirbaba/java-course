enum TypeWaterVehicle
{
    freight,
    passenger
}

enum Condition
{
    normal,
    clumsiness,
    confusion
}

enum Location
{
    scene,
    river,
    ramp,
    boat,
    boatStern,
    coast
}

enum ResultOfAction
{
    SUCCESSFULLY,
    UNSUCCESSFULLY
}

enum HemulClothes
{
    uniformCoat,
    scientistsRobe,
    judgesCloth
}

enum HemulEmployment
{
    controller,
    watchman,
    educator,
    policeman
}

interface WaterVehicleActions
{
    /*
    public ResultOfAction Mooring();
    public ResultOfAction DisembarkationPeople();
     */
    public ResultOfAction Move(Being navigator, Location to);
    public ResultOfAction LandingPeople(Hemul passenger);
    public ResultOfAction LandingPeople(MoominTroll passenger);
    public ResultOfAction Rotate(Being navigator);
}

interface ActionsOfCreatures
{
    ResultOfAction Run(Location to);
    void SitDown();
    ResultOfAction Shout();
}

class Story
{
    private Boat boat;
    private Bell bell;
    private MoominTroll moominMom;
    private MoominTroll moominTroll;
    private Hemul hemuliha;

    public void AddBoat(Boat boat)
    {
        this.boat = boat;
    }

    public void AddBell(Bell bell)
    {
        this.bell = bell;
    }

    public void AddMoominMom(MoominTroll moominMom)
    {
        this.moominMom = moominMom;
    }

    public void AddMoominTroll(MoominTroll moominTroll)
    {
        this.moominTroll = moominTroll;
    }

    public void AddHemuliha(Hemul hemuliha)
    {
        this.hemuliha = hemuliha;
    }

    public void Go()
    {
        this.boat.LandingPeople(this.moominTroll);
        this.boat.LandingPeople(this.hemuliha);

        System.out.println("\nstory starts\n");

        this.boat.Move(moominTroll, Location.coast);
        this.bell.Ring();
        this.moominMom.Run(Location.ramp);
        this.boat.Rotate(moominTroll);
        this.boat.Move(moominTroll, Location.ramp);
        this.hemuliha.SitDown();
        this.hemuliha.Shout();
    }
}

abstract class Being implements ActionsOfCreatures
{
    private String name;
    private Location location;
    private Condition condition;

    public Being(String name, Location location, Condition conidition)
    {
        this.name = name;
        this.location = location;
        this.condition = conidition;

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

    public void SetCondition(Condition condition)
    {
        this.condition = condition;
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

    public void SitDown()
    {
        if (this.GetLocation() == Location.boat)
            this.SetLocation(Location.boatStern);
        System.out.println(this.GetName() + " sits at the " + this.GetLocation());
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
}

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

class MoominTroll extends Being
{
    public MoominTroll(String name, Location location)
    {
        super(name, location);
    }

    public MoominTroll(String name, Location location, Condition condition)
    {
        super(name, location, condition);
    }

    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }
}

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

    public ResultOfAction Move(Being navigator, Location to)
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

class Boat extends WaterVehicle
{
    private int numberOfPaddles;

    public Boat(String name, Location location, int maxNumberOfPassengers, TypeWaterVehicle type, int numberOfPaddles)
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
    public ResultOfAction Move(Being navigator, Location to)
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
                System.out.println("there are not enough paddles to move the boat");
                return ResultOfAction.UNSUCCESSFULLY;
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
        Boat thing = (Boat) obj;
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
}

class Bell extends Thing
{
    public Bell(String name, Location location)
    {
        super(name, location);
    }

    @Override
    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }

    public void Ring()
    {
        if (this.GetLocation() == Location.boat)
            System.out.println("tinkling sound of a small bell");
    }
}

public class lab3
{
    public static void main(String[] args)
    {
        Story episode = new Story();

        episode.AddBoat(new Boat("wooden boat", Location.river, 2, TypeWaterVehicle.passenger, 2));
        episode.AddBell(new Bell("small bell", Location.boat));
        episode.AddMoominTroll(new MoominTroll("moomin troll", Location.boat, Condition.clumsiness));
        episode.AddMoominMom(new MoominTroll("moomin mom", Location.scene));
        episode.AddHemuliha(new Hemul("hemuliha", Location.boat, HemulClothes.scientistsRobe, HemulEmployment.controller));

        episode.Go();
    }
}