public class lab4
{
    public static void main(String[] args)
    {
        Story episode = new Story();

        episode.AddTable(new Table("table", Location.scene, 50));
        episode.AddPerformance(new Performance("romeo and juliet"));
        episode.AddMoominMom(new MoominTroll("moomin mom", Location.scene, MoominTrollCLothes.multiLevelDress));
        episode.AddPerformance(new Performance("romeo and juliet"));
        episode.AddMoominMom(new MoominTroll("moomin mom", Location.scene, MoominTrollCLothes.multiLevelDress));
        episode.AddMoominDad(new MoominTroll("moomin dad", Location.scene, MoominTrollCLothes.smoking));
        episode.AddMoominTroll(new MoominTroll("moomin troll", Location.boat, Condition.clumsiness, MoominTrollCLothes.basicOutfit));
        episode.AddMisa(new Misa("misa", Location.scene));
        episode.AddBoat(new WoodenBoat("wooden boat", Location.river, 2, TypeWaterVehicle.passenger, 2));
        SoundProducers bell = new SoundProducers() {
            @Override
            public void Ring() {
                System.out.println("tinkling sound of a small bell");
            }
        };
        episode.AddBell(bell);
        episode.AddHemuliha(new Hemul("hemuliha", Location.boat, HemulClothes.scientistsRobe, HemulEmployment.controller));
        episode.AddHemul(new Hemul("hemul", Location.ramp, HemulClothes.judgesCloth, HemulEmployment.watchman));
        episode.AddSnusmumrik(new Mumrik("snusmumrik", Location.scene));
        episode.Go();
    }
}