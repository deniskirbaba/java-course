interface AudienceActions
{
    public void SetCondition(Condition condition);
    public void SitDown(Location location);
    public void Watch();
}

interface OrchestraActions
{
    public void Play(MusicalComposition composition);
    public void Stop();
    public void ChangeComposition(MusicalComposition composition);
}

interface WaterVehicleActions
{
    /*
    public ResultOfAction Mooring();
    public ResultOfAction DisembarkationPeople();
     */
    public ResultOfAction Move(Being navigator, Location to) throws PaddleException;
    public ResultOfAction LandingPeople(Hemul passenger);
    public ResultOfAction LandingPeople(MoominTroll passenger);
    public ResultOfAction Rotate(Being navigator);
}

interface ActionsOfCreatures
{
    public void Move(Being being);
    public ResultOfAction Run(Location to);
    public void SitDown();
    public ResultOfAction Shout();
    public void See(Being at);
}

interface SoundProducers
{
    public void Ring();
}