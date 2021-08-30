import java.util.HashMap;

class MoominTroll extends Being
{
    private MoominTrollCLothes clothes;
    private int maxItemsInHands = 2;
    private int curItemsInHands;
    private String[] itemsInHands = new String[maxItemsInHands];

    public MoominTroll(String name, Location location, MoominTrollCLothes clothes)
    {
        super(name, location);
        this.clothes = clothes;
        this.curItemsInHands = 0;
        for (int i = 0; i < maxItemsInHands; i++)
        {
            itemsInHands[i] = "";
        }
    }

    public MoominTroll(String name, Location location, Condition condition, MoominTrollCLothes clothes)
    {
        super(name, location, condition);
        this.clothes = clothes;
    }

    public ResultOfAction Approval(Being being)
    {
        if (being.GetName() == "moomin troll" & this.GetName() == "moomin mom")
        {
            System.out.println(this.GetName() + " don't even known what did " + being.GetName() + " but anyway approve his act (of course because she's his mom)");
            return ResultOfAction.SUCCESSFULLY;
        }
        return ResultOfAction.UNSUCCESSFULLY;
    }

    public void ChangeClothes(MoominTrollCLothes clothes)
    {
        MoominTrollCLothes currentCLothes = this.clothes;
        if (currentCLothes == clothes)
            System.out.println(this.GetName() + " is already in the " + this.clothes);
        else
        {
            this.clothes = clothes;
            System.out.println(this.GetName() + " has weared " + this.clothes + " instead of " + currentCLothes);
        }
    }

    public void ReleaseHands()
    {
        for (int i = 0; i < maxItemsInHands; i++)
            itemsInHands[i] = "";
    }

    public void Serve(String food, Table table, GroupOfPeople people, boolean withTray)
    {
        if (withTray)
        {
            class Tray extends Thing
            {
                private int capacity;
                private int curNumberOfThings;
                private HashMap<String, Integer> food = new HashMap<>();

                public Tray(String name, Location location, int capacity)
                {
                    super(name, location);
                    this.capacity = capacity;
                    this.curNumberOfThings = 0;
                }

                public void ObjectCreated()
                {
                    System.out.println(GetName() + " has created");
                }
            }

            Tray tray = new Tray("tray", Location.moominMomHands, 15);
            ReleaseHands();
            PickUp(tray);
            for (int i=0; i < people.GetNumberOfPeople(); i++)
            {
                if (tray.curNumberOfThings == 0)
                {
                    Move(table);
                    PickUp(food, Math.min(people.GetNumberOfPeople() - i, tray.capacity), table, tray.food);
                    tray.curNumberOfThings = tray.food.get(food);
                }
                Move(people.GetBeing(i));
                Give(food, 1, people.GetBeing(i), tray.food);
                tray.curNumberOfThings -= 1;
            }
        }
        else
        {
            ReleaseHands();
            for (int i=0; i < people.GetNumberOfPeople(); i++)
            {
                if (curItemsInHands == 0)
                {
                    Move(table);
                    PickUp(food, Math.min(people.GetNumberOfPeople() - i, maxItemsInHands), table, itemsInHands);
                    curItemsInHands = itemsInHands.length;
                }
                Move(people.GetBeing(i));
                Give(food, 1, people.GetBeing(i), itemsInHands);
                curItemsInHands -= 1;
            }
        }
    }

    public void Give(String what, int quantity, MoominTroll whom, HashMap<String, Integer> from)
    {
        whom.SetCurItemsInHands(whom.GetCurItemsInHands() + quantity);
        for (int i = 0; i < quantity; i++)
        {
            whom.PickUp(what);
        }
        from.put(what, from.get(what) - 1);
        System.out.println(this.GetName() + " give " + what + " to " + whom.GetName());
    }

    public void Give(String what, int quantity, MoominTroll whom, String[] from)
    {
        whom.SetCurItemsInHands(whom.GetCurItemsInHands() + quantity);
        for (int i = 0; i < quantity; i++)
        {
            whom.PickUp(what);
        }
        for (int i = 0; i < this.maxItemsInHands; i++)
        {
            if (this.itemsInHands[i] == what)
                this.itemsInHands[i] = "";
        }
        System.out.println(this.GetName() + " give " + what + " to " + whom.GetName());
    }

    public void PickUp(Thing thing)
    {
        if (maxItemsInHands - curItemsInHands != 0)
        {
            for (int i = 0; i < maxItemsInHands; i++)
            {
                if (itemsInHands[i] == "")
                {
                    itemsInHands[i] = thing.GetName();
                    break;
                }

            }
        }
        System.out.println(this.GetName() + " pick up " + thing.GetName());
    }

    public void PickUp(String thing)
    {
        if (maxItemsInHands - curItemsInHands != 0)
        {
            for (int i = 0; i < maxItemsInHands; i++)
            {
                if (itemsInHands[i] == "")
                {
                    itemsInHands[i] = thing;
                    break;
                }

            }
        }
    }

    public void PickUp(String food, int requiredNumberOfFood, Table table, String[] to)
    {
        int realNumberOfFood = table.Contains(food);
        if (realNumberOfFood > 0)
        {
            if (realNumberOfFood >= requiredNumberOfFood)
            {
                for (int i = 0; i < this.maxItemsInHands; i++)
                    this.itemsInHands[i] = food;
                table.DeleteFood(food, requiredNumberOfFood);
            }
        }
        System.out.println(this.GetName() + " pick up " + Integer.toString(requiredNumberOfFood) + " " + food + " in hands" + " from " + table.GetName());
    }

    public void PickUp(String food, int requiredNumberOfFood, Table table, HashMap<String, Integer> to)
    {
        int realNumberOfFood = table.Contains(food);
        if (realNumberOfFood > 0)
        {
            if (realNumberOfFood >= requiredNumberOfFood)
            {
                to.put(food, requiredNumberOfFood);
                table.DeleteFood(food, requiredNumberOfFood);
            }
        }
        System.out.println(this.GetName() + " pick up " + Integer.toString(requiredNumberOfFood) + " " + food + " on tray" + " from " + table.GetName());
    }

    public void ObjectCreated()
    {
        System.out.println(this.GetName() + " has created");
    }

    public void SetCurItemsInHands(int curItemsInHands)
    {
        this.curItemsInHands = curItemsInHands;
    }

    public int GetCurItemsInHands()
    {
        return this.curItemsInHands;
    }

    public void Drop(String thing)
    {
        System.out.println(this.GetName() + " has dropped " + thing);
    }

    @Override
    public void SetCondition(Condition condition)
    {
        this.condition = condition;
        System.out.println(this.GetName() + " now have " + condition);
        if (condition == Condition.confusion && this.GetName() == "moomin mom") {
            for (int i = 0; i < maxItemsInHands; i++) {
                if (itemsInHands[i] != "")
                    Drop(itemsInHands[i]);
            }
            ReleaseHands();
        }
    }
}