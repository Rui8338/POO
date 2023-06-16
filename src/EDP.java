import java.io.Serializable;

public class EDP extends EnergyProvider implements Serializable {

    public EDP()
    {
        super();
    }

    public EDP(EnergyProvider ep)
    {
        super(ep);
    }

    public double dailyPricePerKw(House house)
    {
        return house.totalDevices() > 0 ? (getBaseValue()+0.1) * (1+(getTax() / house.totalDevices())) : (getBaseValue()+0.1) * (1+ getTax());
    }

    public EDP clone()
    {
        return new EDP(this);
    }

    public String toString()
    {
        return "EDP Comercial";
    }

    public boolean equals(Object o)
    {
        return (o instanceof EDP);
    }
}
