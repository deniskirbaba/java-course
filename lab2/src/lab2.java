import ru.ifmo.se.pokemon.*;

class Facade extends PhysicalMove
//Facade deals damage, and hits with double power (140) if the user is burned, poisoned or paralyzed.
{
    protected Facade()
    {
        super(Type.NORMAL, 70, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage)
    {
        Status pokemonStatus = def.getCondition();
        if (pokemonStatus.equals(Status.BURN) || pokemonStatus.equals(Status.POISON) || pokemonStatus.equals(Status.PARALYZE))
        {
            def.setMod(Stat.HP, (int) Math.round(damage) * 2);
        }
    }
}

class ShadowBall extends SpecialMove
//Shadow Ball deals damage and has a 20% chance of lowering the target's Special Defense by one stage.
{
    protected ShadowBall()
    {
        super(Type.GHOST, 80, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p)
    {
        if (Math.random() <= 0.2)
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
    }
}

class Swagger extends StatusMove
//Swagger confuses the target and raises its Attack by two stages.
{
    protected Swagger()
    {
        super(Type.NORMAL, 0, 85);
    }
    @Override
    protected void applyOppEffects(Pokemon p)
    {
        p.setMod(Stat.ATTACK, 2);
        Effect.confuse(p);
    }
}

class DoubleTeam extends StatusMove
//Double Team raises the user's Evasiveness by one stage, thus making the user more difficult to hit.
{
    protected DoubleTeam()
    {
        super(Type.NORMAL, 0, 0);
    }
    @Override
    protected void applySelfEffects(Pokemon p)
    {
        p.setMod(Stat.EVASION, 1);
    }
}

class EerieImpulse extends StatusMove
//Eerie Impulse lowers the target's Special Attack by two stages.
{
    protected EerieImpulse()
    {
        super(Type.ELECTRIC, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p)
    {
        p.setMod(Stat.SPECIAL_ATTACK, -2);
    }
}

class Discharge extends SpecialMove
//Discharge deals damage and has a 30% chance of paralyzing the target(s).
{
    protected Discharge()
    {
        super(Type.ELECTRIC, 80, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p)
    {
        if (Math.random() <= 0.3)
            Effect.paralyze(p);
    }
}

class AerialAce extends PhysicalMove
//Aerial Ace deals damage and ignores changes to the Accuracy and Evasion stats.
{
    protected AerialAce()
    {
        super(Type.FLYING, 60, 999999999);
    }
    @Override
    protected void applyOppEffects(Pokemon p)
    {
        p.setMod(Stat.ACCURACY, 0);
        p.setMod(Stat.EVASION, 0);
    }
}

class Psychic extends SpecialMove
//Psychic deals damage and has a 10% chance of lowering the target's Special Defense by one stage.
{
    protected Psychic()
    {
        super(Type.PSYCHIC, 90, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p)
    {
        if (Math.random() <= 0.1)
            p.setMod(Stat.SPECIAL_DEFENSE,-1);
    }
}

class Thunder extends SpecialMove
//Thunder deals damage and has a 30% chance of paralyzing the target.
{
    protected Thunder()
    {
        super(Type.ELECTRIC, 110, 70);
    }
    @Override
    protected void applyOppEffects(Pokemon p)
    {
        if (Math.random() <= 0.3)
            Effect.paralyze(p);
    }
}

class HornAttack extends PhysicalMove
//Horn Attack deals damage with no additional effect.
{
    protected HornAttack()
    {
        super(Type.NORMAL, 65, 100);
    }
}


class Dedenne extends Pokemon
{
    public Dedenne (String name,int level)
    {
        super(name,level);
        setStats(67,58,57,81,67,101);
        setType(Type.ELECTRIC,Type.FAIRY);
        setMove(new DoubleTeam(), new EerieImpulse(), new Discharge(), new AerialAce());
    }
}

class NidoranM extends Pokemon
{
    public NidoranM (String name,int level)
    {
        super(name,level);
        setStats(46,57,40,40,40,50);
        setType(Type.POISON);
        setMove(new DoubleTeam(), new Facade());
    }
}

class Nidorino extends NidoranM
{
    public Nidorino (String name,int level)
    {
        super(name,level);
        setStats(61,72,57,55,55,65);
        setMove(new DoubleTeam(), new Facade(), new HornAttack());
    }
}

class Nidoking extends Nidorino
{
    public Nidoking (String name,int level)
    {
        super(name,level);
        setStats(81,102,77,85,75,85);
        setType(Type.POISON,Type.GROUND);
        setMove(new DoubleTeam(),new Facade(), new HornAttack(), new ShadowBall());
    }
}

class Spritzee extends Pokemon
{
    public Spritzee (String name,int level)
    {
        super(name,level);
        setStats(78,52,60,63,65,23);
        setType(Type.FAIRY);
        setMove(new Facade(), new Psychic(), new Swagger());
    }
}

class Aromatisse extends Spritzee
{
    public Aromatisse (String name,int level)
    {
        super(name,level);
        setStats(101,72,72,99,89,29);
        setMove(new Facade(), new Psychic(), new Swagger(),new Thunder());
    }
}

public class lab2
{
    public static void main(String[] args)
    {
        Battle boxingRing = new Battle();

        boxingRing.addAlly(new Dedenne("BruceLee", 3));
        boxingRing.addAlly(new Nidorino("Khabib", 2));
        boxingRing.addAlly(new Nidoking("ConorMcgregor", 1));

        boxingRing.addFoe(new NidoranM("FedorEmelianenko", 2));
        boxingRing.addFoe(new Spritzee("FloydMayweather", 1));
        boxingRing.addFoe(new Aromatisse("MikeTyson", 3));

        boxingRing.go();
    }
}