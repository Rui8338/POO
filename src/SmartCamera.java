import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class SmartCamera extends SmartDevice implements Serializable {
     private int resolution_width;
     private int resolution_height;
     private double saveFileSize;

     public SmartCamera(int id)
     {
         super(id);
         this.resolution_width = 1280;
         this.resolution_height = 720;
         this.saveFileSize = 10;
     }

    public SmartCamera(int id, boolean on)
    {
        super(id,on);
        this.resolution_width = 1280;
        this.resolution_height = 720;
        this.saveFileSize = 10;
    }

     public SmartCamera(int id, boolean on, int resolution_width, int resolution_height, double saveFileSize)
     {
         super(id,on);
         this.resolution_width = resolution_width;
         this.resolution_height = resolution_height;
         this.saveFileSize = saveFileSize;
     }
     public SmartCamera(SmartCamera sc)
     {
         super(sc);
         this.resolution_width = sc.getResolution_width();
         this.resolution_height = sc.getResolution_height();
         this.saveFileSize = sc.getSaveFileSize();
     }

    public int getResolution_width()
    {
        return this.resolution_width;
    }

    public int getResolution_height()
    {
        return this.resolution_height;
    }

    public double getSaveFileSize()
    {
        return this.saveFileSize;
    }

    public void setResolution_width(int resolution_width)
    {
        this.resolution_width = resolution_width;
    }

    public void setResolution_height(int resolution_height)
    {
        this.resolution_height = resolution_height;
    }

    public void setSaveFileSize(double saveFileSize) {
        this.saveFileSize = saveFileSize;
    }

    public double getDailyIntake()
    {
        double dailyIntake = 0;
        if (this.getOn())
        {
            int length = (int) (Math.log10(this.saveFileSize) + 1);
            dailyIntake = (double) this.resolution_width/this.resolution_height * this.saveFileSize/Math.pow(10, length);
        }
        return dailyIntake;
    }

    @Override
    public SmartDevice clone()
    {
        return new SmartCamera(this);
    }

    @Override
    public String toString()
    {
        String str = super.toString();
        str = str + ("\nResolution: (" + this.resolution_width + "x" + this.resolution_height + ")\nFile size: " + this.saveFileSize);
        return str;
    }

    public String logToString()
    {
        String str = "SmartCamera:" + this.resolution_width + "x" + this.resolution_height + "," + this.saveFileSize;
        return str;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartCamera that = (SmartCamera) o;
        return resolution_width == that.resolution_width && resolution_height == that.resolution_height && Double.compare(that.saveFileSize, saveFileSize) == 0;
    }
}
