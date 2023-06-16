import java.io.Serializable;

public class Endesa extends EnergyProvider implements Serializable {
    public Endesa()
    {
        super();
    }

    public Endesa(EnergyProvider ep)
    {
        super(ep);
    }

    public double dailyPricePerKw(House house)
    {
        return (getBaseValue() + getTax())/5;
    }

    public Endesa clone()
    {
        return new Endesa(this);
    }

    public String toString()
    {
        return "Endesa";
    }

    public boolean equals(Object o)
    {
        return (o instanceof Endesa);
    }
}
