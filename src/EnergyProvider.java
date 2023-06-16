import java.util.Objects;
import java.io.Serializable;

public abstract class EnergyProvider implements Serializable {
    private double baseValue;
    private double tax;
    private double factoringVolume;

    public EnergyProvider()
    {
        this.baseValue = 0.10;
        this.tax = 0.64;
        this.factoringVolume = 0;
    }

    public EnergyProvider(double baseValue, double tax, double factoringVolume)
    {
        this.baseValue = baseValue;
        this.tax = tax;
        this.factoringVolume = factoringVolume;
    }

    public EnergyProvider(EnergyProvider ep)
    {
        this.baseValue = ep.getBaseValue();
        this.tax = ep.getTax();
        this.factoringVolume = ep.getFactoringVolume();
    }

    public abstract double dailyPricePerKw(House house);

    public double getBaseValue()
    {
        return baseValue;
    }

    public double getTax()
    {
        return tax;
    }

    public void setTax(double newtax)
    {
        this.tax = newtax;
    }

    public void setBaseValue(double newBaseValue)
    {
        this.baseValue = newBaseValue;
    }

    public double getFactoringVolume()
    {
        return factoringVolume;
    }

    public void addFactoringVoume(double v)
    {
        factoringVolume += v;
    }

    public abstract boolean equals(Object o);
    public abstract EnergyProvider clone();
    public abstract String toString();

    @Override
    public int hashCode()
    {
        return Objects.hash(baseValue, tax, factoringVolume);
    }
}
