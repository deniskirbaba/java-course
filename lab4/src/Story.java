class Story
{
    private Performance performance;
    private Table table;
    private WoodenBoat boat;
    private SoundProducers bell;
    private MoominTroll moominMom;
    private MoominTroll moominDad;
    private MoominTroll moominTroll;
    private Hemul hemuliha;
    private Hemul hemul;
    private Misa misa;
    private Mumrik snusmumrik;

    public void AddTable(Table table)
    {
        this.table = table;
    }

    public void AddPerformance(Performance performance)
    {
        this.performance = performance;
    }

    public void AddBoat(WoodenBoat boat)
    {
        this.boat = boat;
    }

    public void AddBell(SoundProducers bell)
    {
        this.bell = bell;
    }

    public void AddMoominMom(MoominTroll moominMom)
    {
        this.moominMom = moominMom;
    }

    public void AddMoominDad(MoominTroll moominDad)
    {
        this.moominDad = moominDad;
    }

    public void AddMoominTroll(MoominTroll moominTroll)
    {
        this.moominTroll = moominTroll;
    }

    public void AddHemuliha(Hemul hemuliha)
    {
        this.hemuliha = hemuliha;
    }

    public void AddHemul(Hemul hemul)
    {
        this.hemul = hemul;
    }

    public void AddMisa(Misa misa)
    {
        this.misa = misa;
    }

    public void AddSnusmumrik(Mumrik snusmumrik)
    {
        this.snusmumrik = snusmumrik;
    }

    public void Go()
    {
        this.table.AddFood("coffee", 50);
        this.boat.LandingPeople(this.hemuliha);
        this.boat.LandingPeople(this.moominTroll);

        System.out.println("\nstory starts\n");

        this.performance.Start();
        this.performance.spectators.Eat("iceCream");
        while (performance.spectators.GetNumberOfPeople() != 0)
        {
            this.performance.spectators.BecomeAnActor();
        }
        this.moominMom.ChangeClothes(MoominTrollCLothes.typicalDress);
        this.moominMom.Serve("coffee", this.table, this.performance.spectators, true);
        this.moominMom.Serve("coffee", this.table, this.performance.actors, true);
        this.moominMom.Serve("coffee", this.table, this.performance.orchestra, true);
        this.moominMom.PickUp("coffee");
        this.performance.orchestra.Play(MusicalComposition.HemulMarch);
        this.moominDad.SetCondition(Condition.happiness);
        this.misa.SetCondition(Condition.happiness);
        this.moominMom.SetCondition(Condition.confusion);
        this.performance.Stop();
        try {
            this.boat.Move(moominTroll, Location.coast);
        }
        catch (PaddleException ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.GetNumberOfPaddles());
        }
        this.bell.Ring();
        this.moominMom.Run(Location.ramp);
        this.boat.Rotate(moominTroll);
        try {
            this.boat.Move(moominTroll, Location.ramp);
        }
        catch (PaddleException ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.GetNumberOfPaddles());
        }
        this.hemuliha.SitDown();
        this.hemuliha.Shout();
        this.moominMom.Approval(moominTroll);
        this.performance.spectators.SetCondition(Condition.astonishment);
        this.performance.spectators.SitDown(Location.ramp);
        this.performance.spectators.Applaud();
        this.performance.Continue();
        this.hemul.See(snusmumrik);
        this.hemul.Approach(snusmumrik);
        this.hemul.Grab(snusmumrik);
    }
}