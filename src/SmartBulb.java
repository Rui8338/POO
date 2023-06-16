import java.io.Serializable;
import java.time.LocalDate;

public class SmartBulb extends SmartDevice implements Serializable {
    private Tone tone;
    private double dimension;
    private double fixedDailyIntake;

    public SmartBulb(int id)
    {
        super(id);
        this.tone = Tone.NEUTRAL;
        this.dimension = 5;
        this.fixedDailyIntake = 0.5;
    }

    public SmartBulb(int id, boolean on)
    {
        super(id,on);
        this.tone = Tone.NEUTRAL;
        this.dimension = 5;
        this.fixedDailyIntake = 0.5;
    }

    public SmartBulb(int id, boolean on,Tone tone, double dimension, double fixedDailyIntake)
    {
        super(id, on);
        this.tone = tone;
        this.dimension = dimension;
        this.fixedDailyIntake = fixedDailyIntake;
    }

    public SmartBulb(SmartBulb sb)
    {
        super(sb);
        this.dimension = sb.getDimension();
        this.tone = sb.getTone();
        this.fixedDailyIntake = sb.getFixedDailyIntake();
    }

    public Tone getTone() {
        return this.tone;
    }
    public double getDimension() {
        return this.dimension;
    }

    public double getFixedDailyIntake()
    {
        return this.fixedDailyIntake;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public void setFixedDailyIntake(double fdi)
    {
        this.fixedDailyIntake = fdi;
    }

    @Override
    public double getDailyIntake()
    {
        double di = 0;
        if (this.getOn())
        {
            switch (this.tone) {
                case COLD:
                    di = fixedDailyIntake + fixedDailyIntake * 0.25;
                    break;
                case WARM:
                    di = fixedDailyIntake + fixedDailyIntake * 0.75;
                    break;
                case NEUTRAL:
                    di = fixedDailyIntake + fixedDailyIntake * 0.5;
                    break;
                default:
                    di = fixedDailyIntake;
                    break;
            }
        }
        return di;
    }

    @Override
    public SmartDevice clone()
    {
        return new SmartBulb(this);
    }

    @Override
    public String toString()
    {
        String str = super.toString();
        str = str + ("\nTone: " + this.tone + "    Dimension: " + this.dimension);
        return str;
    }

    public String logToString()
    {
        String str = "SmartBulb:" + this.tone.toString() + "," + this.dimension + "," + this.fixedDailyIntake;
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartBulb)) return false;
        if (!super.equals(o)) return false;

        SmartBulb smartBulb = (SmartBulb) o;

        if (Double.compare(smartBulb.getFixedDailyIntake(), getFixedDailyIntake()) != 0) return false;
        if (Double.compare(smartBulb.getDimension(), getDimension()) != 0) return false;
        return getTone() == smartBulb.getTone();
    }
}
