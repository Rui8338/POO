import java.io.Serializable;

public class Iberdrola extends EnergyProvider implements Serializable {
    public Iberdrola()
    {
        super();
    }

    public Iberdrola(EnergyProvider ep)
    {
        super(ep);
    }

    public double dailyPricePerKw(House house)
    {
        return getBaseValue() + 0.1*(0.3+getTax());
    }

    public Iberdrola clone()
    {
        return new Iberdrola(this);
    }

    public String toString()
    {
        return "Iberdrola";
    }

    public boolean equals(Object o)
    {
        return (o instanceof Iberdrola);
    }
}
