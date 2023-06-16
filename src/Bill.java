import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

public class Bill implements Serializable {
    private LocalDate initialDate;
    private LocalDate finalDate;
    private double intake;
    private double price;
    private int houseID;
    private EnergyProvider provider;

    public Bill(LocalDate d1, LocalDate d2, double intake, double price, int houseID, EnergyProvider provider)
    {
        this.initialDate = d1;
        this.finalDate = d2;
        this.intake = intake;
        this.price = price;
        this.houseID = houseID;
        this.provider = provider;
    }

    public Bill(Bill b)
    {
        this.initialDate = b.getInitialDate();
        this.finalDate = b.getFinalDate();
        this.intake = b.getIntake();
        this.price = b.getPrice();
        this.houseID = b.getHouseID();
        this.provider = b.getProvider();
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public double getIntake()
    {
        return (double) Math.round(intake*100)/100;
    }

    public double getPrice()
    {
        return (double) Math.round(price*100)/100;
    }

    public int getHouseID() {
        return houseID;
    }

    public EnergyProvider getProvider() {
        return provider;
    }

    public Bill clone()
    {
        return new Bill(this);
    }

    public int daysInPeriod(LocalDate d1, LocalDate d2)
    {
        long numberOfOverlappingDates;
        if (d2.isBefore(initialDate) || finalDate.isBefore(d1)) {
            numberOfOverlappingDates = 0;
        } else {
            LocalDate laterStart = Collections.max(Arrays.asList(d1, initialDate));
            LocalDate earlierEnd = Collections.min(Arrays.asList(d2, finalDate));
            numberOfOverlappingDates = ChronoUnit.DAYS.between(laterStart, earlierEnd);
        }
        return (int) numberOfOverlappingDates;
    }

    @Override
    public String toString() {
        String str = "Initial Date: " + initialDate + " \tFinal Date: " + finalDate
                   + "\nHouse ID: " + houseID + " \tProvider: " + provider
                   + "\nIntake: " + (double) Math.round(intake*100)/100 + " kWh" + " \tTotal Price: " + (double) Math.round(price*100)/100 + " €";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;

        Bill bill = (Bill) o;

        if (Double.compare(bill.getIntake(), getIntake()) != 0) return false;
        if (Double.compare(bill.getPrice(), getPrice()) != 0) return false;
        if (getHouseID() != bill.getHouseID()) return false;
        if (getInitialDate() != null ? !getInitialDate().equals(bill.getInitialDate()) : bill.getInitialDate() != null)
            return false;
        if (getFinalDate() != null ? !getFinalDate().equals(bill.getFinalDate()) : bill.getFinalDate() != null)
            return false;
        return getProvider() != null ? getProvider().equals(bill.getProvider()) : bill.getProvider() == null;
    }
}
