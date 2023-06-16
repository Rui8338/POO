import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BillManager implements Serializable {
    private Map<Integer, ArrayList<Bill>> bills;

    public BillManager()
    {
        bills = new HashMap<>();
    }

    public void addBill(Bill b)
    {
        if(bills.containsKey(b.getHouseID()))
        {
            ArrayList<Bill> arr = bills.get(b.getHouseID());
            arr.add(b);
        }
        else
        {
            ArrayList<Bill> arr = new ArrayList<>();
            arr.add(b);
            bills.put(b.getHouseID(),arr);
        }
    }

    public void addBills(Collection<Bill> c)
    {
        for(Bill b : c)
        {
            addBill(b);
        }
    }

    List<Bill> billsFromProvider(EnergyProvider provider)
    {
        List<Bill> list = new ArrayList<>();
        for(Integer i : bills.keySet())
        {
            ArrayList<Bill> billList = bills.get(i);
            for(Bill b:billList)
            {
                if(b.getProvider().equals(provider))
                {
                    list.add(b);
                }
            }
        }
        return list;
    }

    public double intakeFromHouseInPeriod(int house_id, LocalDate d1, LocalDate d2)
    {
        double t = 0;
        ArrayList<Bill> ar = bills.get(house_id);
        for(Bill b : ar)
        {
            int dp =(int) ChronoUnit.DAYS.between(b.getInitialDate(),b.getFinalDate());
            t+=b.getIntake()/dp*b.daysInPeriod(d1,d2);
        }
        return t;
    }

    public boolean containsBillsFromHouse(int id)
    {
        return bills.containsKey(id);
    }
}
