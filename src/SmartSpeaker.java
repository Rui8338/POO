import java.io.Serializable;
import java.time.LocalDate;

public class SmartSpeaker extends SmartDevice implements Serializable {

    private int volume;
    private String radio;
    private String brand;

    public SmartSpeaker(int id)
    {
        super(id);
        this.volume = 0;
        this.radio = "";
        this.brand = "";
    }

    public SmartSpeaker(int id, boolean on)
    {
        super(id,on);
        this.volume = 0;
        this.radio = "";
        this.brand = "";
    }

    public SmartSpeaker(int id, boolean on, int volume, String radio, String brand)
    {
        super(id,on);
        if (volume<0)
            this.volume = 0;
        else if (volume>100)
            this.volume = 100;
        else
            this.volume = volume;
        this.radio = radio;
        this.brand = brand;
    }

    public SmartSpeaker(SmartSpeaker sc) {
        super(sc);
        this.volume = sc.getVolume();
        this.radio = sc.getRadio();
        this.brand = sc.getBrand();
    }

    public int getVolume() {
        return this.volume;
    }

    public String getRadio() {
        return this.radio;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    public void setRadio(String radio) {
        this.radio = radio;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getDailyIntake()
    {
        double dailyIntake = 0;
        if (this.getOn())
        {
            dailyIntake = (double)this.volume/100;
        }
        return dailyIntake;
    }

    public SmartDevice clone() {
        return new SmartSpeaker(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(super.toString());
        str.append("\nVolume: ");
        str.append(this.volume);
        str.append("\n");
        str.append("Radio: ");
        str.append(this.radio);
        str.append("\nBrand: ");
        str.append(this.brand);
        return str.toString();
    }

    public String logToString()
    {
        String str = "SmartSpeaker:" + this.volume + "," + this.radio + "," + this.getBrand();
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartSpeaker)) return false;
        if (!super.equals(o)) return false;

        SmartSpeaker that = (SmartSpeaker) o;

        if (getVolume() != that.getVolume()) return false;
        if (getRadio() != null ? !getRadio().equals(that.getRadio()) : that.getRadio() != null) return false;
        return getBrand() != null ? getBrand().equals(that.getBrand()) : that.getBrand() == null;
    }

}
