import java.io.Serializable;

public class Galp extends EnergyProvider implements Serializable {
    public Galp()
    {
        super();
    }

    public Galp(EnergyProvider ep)
    {
        super(ep);
    }

    public double dailyPricePerKw(House house)
    {
        return ((getBaseValue()+0.5) * getTax()) * 0.45;
    }

    public Galp clone()
    {
        return new Galp(this);
    }

    public String toString()
    {
        return "Galp Energia";
    }

    public boolean equals(Object o)
    {
        return (o instanceof Galp);
    }
}
