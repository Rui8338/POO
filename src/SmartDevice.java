import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class SmartDevice implements Serializable {
    private int id;
    private boolean on;
    public SmartDevice(int id)
    {
        this.id = id;
        this.on = false;
    }

    public SmartDevice(int id, boolean on)
    {
        this.id = id;
        this.on = on;
    }

    public SmartDevice(SmartDevice sd)
    {
        this.id = sd.getId();
        this.on = sd.getOn();
    }

    public int getId() {
        return id;
    }

    public boolean getOn() {
        return this.on;
    }

    public void setOn(boolean on) {

        this.on = on;
    }
    public abstract double getDailyIntake();
    @Override
    public abstract SmartDevice clone();

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ");
        sb.append(this.id);
        sb.append("      On: ");
        sb.append(this.on);
        sb.append("\n");
        sb.append("Daily Intake: ");
        sb.append((double) Math.round(this.getDailyIntake()*100)/100);
        sb.append(" kWh");
        return sb.toString();
    }

    public abstract String logToString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartDevice)) return false;

        SmartDevice that = (SmartDevice) o;

        if (getId() != that.getId()) return false;
        return getOn() == that.getOn();
    }
}
