import java.io.Serializable;

public class MEOEnergia extends EnergyProvider implements Serializable {
    public MEOEnergia()
    {
        super();
    }

    public MEOEnergia(EnergyProvider ep)
    {
        super(ep);
    }

    public double dailyPricePerKw(House house)
    {
        return house.totalDevices() > 100 ? getBaseValue()*1.2 : getBaseValue()*1.2 + (1+getTax())/20;
    }

    public MEOEnergia clone()
    {
        return new MEOEnergia(this);
    }

    public String toString()
    {
        return "MEO Energia";
    }

    public boolean equals(Object o)
    {
        return (o instanceof MEOEnergia);
    }
}

