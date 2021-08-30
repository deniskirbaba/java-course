public class Performance
{
    private String name;
    public Actors actors;
    public Orchestra orchestra;
    public Spectators spectators;

    public class Orchestra extends GroupOfPeople
    {
        private MusicalComposition currentComposition;

        public Orchestra(String name, int numberOfMusicians, Location location)
        {
            super(name,numberOfMusicians, location);
            this.currentComposition = null;
        }
        public void Play(MusicalComposition composition)
        {
            System.out.println("orchestra has started playing " + composition);
            this.currentComposition = composition;
        }
        public void Stop()
        {
            System.out.println("orchestra stopped playing " + currentComposition);
            this.currentComposition = null;
        }
    }

    public class Actors extends GroupOfPeople
    {
        public Actors(String name, int numberOfActors, Location location)
        {
            super(name,numberOfActors, location);
        }

        public void Play()
        {
            System.out.println(this.GetName() + " start acting Romeo and Juliet");
        }

        public void Stop()
        {
            System.out.println("the actors stopped acting");
        }
    }

    public class Spectators extends GroupOfPeople
    {
        private Condition condition;

        public Spectators(String name, int numberOfSpectators, Location location) throws SpectatorsException
        {
            super(name, numberOfSpectators, location);
            if (numberOfSpectators<1)
                throw new SpectatorsException("Number of spectators less than 1", numberOfSpectators);
        }

        public void SetCondition(Condition condition)
        {
            this.condition = condition;
            System.out.println(this.GetName() + " now have " + condition);
        }

        public void SitDown(Location location)
        {
            System.out.println(this.GetName() + " sit down at the " + location);
        }

        public void Applaud()
        {
            System.out.println(this.GetName() + " applaud");
        }

        public void Eat(String food)
        {
            class Food
            {
                private String food;

                public Food(String food)
                {
                    this.food = food;
                }

                public boolean ValidateFood()
                {
                    for (AllowedFood allFood : AllowedFood.values())
                    {
                        if (this.food.equals(allFood.toString()))
                            return true;
                    }
                    return false;
                }
            }

            Food f = new Food(food);
            if (f.ValidateFood())
                System.out.println("spectators eating " + food);
            else
                System.out.println(food + " is not allowed to eat over here");
        }

        public void BecomeAnActor()
        {
            for (int i = 0; i < this.GetNumberOfPeople(); i++)
            {
                MoominTroll immigrant = this.GetBeing(i);
                actors.AddBeing(immigrant);
                this.DeleteBeing(immigrant);
                System.out.println(immigrant.GetName() + " decided to become an actor, so number of spectators: " + this.GetNumberOfPeople());
            }
        }
    }

    public Performance(String name)
    {
        this.name = name;
        this.actors = new Actors("actors", 5, Location.scene);
        this.orchestra = new Orchestra("orchestra", 5, Location.scene);
        try {
            this.spectators = new Spectators("spectators", 20, Location.scene);
        }
        catch (SpectatorsException ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.GetNumberOfSpectators());
            System.exit(0);
        }
    }

    public String GetName()
    {
        return this.name;
    }

    public void Start()
    {
        System.out.println("performance " + this.name + " has started");
        this.orchestra.Play(MusicalComposition.Introduction);
        this.orchestra.Stop();
        this.actors.Play();
    }

    public void Stop()
    {
        this.actors.Stop();
        this.orchestra.Stop();
        System.out.println("performance " + this.name + " has suddenly stopped");
    }

    public void Continue()
    {
        System.out.println("performance continues");
        this.actors.Play();
        this.orchestra.Play(MusicalComposition.HemulMarch);

    }
}