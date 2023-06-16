import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Simulation implements Serializable {
    private Map<Integer,House> houses;
    private Map<String, EnergyProvider> providers;
    private Map<Integer,Bill> lastPeriodBills;
    private BillManager globalBills;
    private LocalDate lastSimDate;
    private LocalDate currentSimDate;

    public Simulation(LocalDate initialDate)
    {
        houses = new HashMap<>();
        providers = new HashMap<>();
        lastPeriodBills = new HashMap<>();
        globalBills = new BillManager();
        lastSimDate = initialDate;
        currentSimDate = initialDate;
    }

    public EnergyProvider getProvider(String provider)
    {
        EnergyProvider provider1 = null;
        if(provider.equals("EDP Comercial"))
        {
            provider1 = new EDP();
        }
        else if(provider.equals("Galp Energia"))
        {
            provider1 = new Galp();
        }
        else if(provider.equals("Iberdrola"))
        {
            provider1 = new Iberdrola();
        }
        else if (provider.equals("Endesa"))
        {
            provider1 = new Endesa();
        }
        else if(provider.equals("MEO Energia"))
        {
            provider1 = new MEOEnergia();
        }
        return provider1;
    }

    public void changeHouseProvider(int id, String provider) throws ObjectUndefinedException {
        if(!houses.containsKey(id))
        {
            throw new ObjectUndefinedException("House does not exist");
        }
        else
        {
            if (!this.providers.containsKey(provider)) throw new ObjectUndefinedException("Provider does not exist");
            else
            {
                houses.get(id).setProvider(providers.get(provider));
            }
        }
    }

    public boolean hasHouse(int id)
    {
        if (houses.containsKey(id))
            return true;
        else
            return false;
    }

    public void addHouse(int id, String provider, String ownerName, String nif) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        if(houses.containsKey(id))
        {
            throw new ObjectAlreadyCreatedException("House already exists");
        }
        else
        {
            if (!this.providers.containsKey(provider)) throw new ObjectUndefinedException("Provider does not exist");
            else
            {
                House h = new House(id,providers.get(provider),ownerName,nif);
                houses.put(id,h);
            }
        }
    }

    public void addProvider(String provider) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        EnergyProvider provider1 = getProvider(provider);
        if (provider1 != null)
        {
            if (providers.containsValue(provider1))
            {
                throw new ObjectAlreadyCreatedException("Provider already exists");
            }
            providers.put(provider1.toString(), provider1);
        }
        else
        {
            throw new ObjectUndefinedException("Provider does not exist");
        }
    }

    public void advanceToDate(LocalDate newDate) throws PreviousDateException
    {
        if(newDate.isBefore(currentSimDate) || newDate.isEqual(currentSimDate))
        {
            throw new PreviousDateException("Date " + newDate + " is not further than " + currentSimDate);
        }
        else {
            lastSimDate = currentSimDate;
            currentSimDate = newDate;
            lastPeriodBills.clear();
            for(House h : houses.values())
            {
                Bill bill = h.generateBill(lastSimDate,currentSimDate);
                lastPeriodBills.put(bill.getHouseID(),bill);
                globalBills.addBill(bill);
            }
        }
    }

    public EnergyProvider providerWithBiggestVolume() throws ObjectUndefinedException
    {
        if(!providers.isEmpty())
        {
            Comparator<EnergyProvider> c = (a, b) -> {
                return Double.compare(a.getFactoringVolume(), b.getFactoringVolume()) == 0 ? b.toString().compareTo(a.toString()) : Double.compare(a.getFactoringVolume(), b.getFactoringVolume());
            };
            TreeSet<EnergyProvider> treeSet = new TreeSet<>(c);
            for (EnergyProvider e : this.providers.values())
            {
                treeSet.add(e);
            }
            return treeSet.last().clone();
        }
        else
        {
            throw new ObjectUndefinedException("There is no providers in the Simulation");
        }
    }

   public Bill mostExpensiveBillInLastPeriod() throws ObjectUndefinedException
    {
        if(!lastPeriodBills.isEmpty()) {
            Comparator<Bill> c = (a, b) -> Double.compare(a.getPrice(), b.getPrice());
            TreeSet<Bill> treeSet = new TreeSet<>(c);
            for (Bill b : lastPeriodBills.values()) {
                treeSet.add(b);
            }
            return treeSet.last().clone();
        }
        else {
            throw new ObjectUndefinedException("There is no Last Period in the Simulation");
        }
    }

    public Bill mostEnergySpenderBillInLastPeriod() throws ObjectUndefinedException
    {
        if(!lastPeriodBills.isEmpty()) {
            Comparator<Bill> c = (a, b) -> Double.compare(a.getIntake(), b.getIntake());
            TreeSet<Bill> treeSet = new TreeSet<>(c);
            for (Bill b : lastPeriodBills.values()) {
                treeSet.add(b);
            }
            return treeSet.last().clone();
        }
        else {
            throw new ObjectUndefinedException("There is no Last Period in the Simulation");
        }
    }

    public List<Bill> billsFromProvider(String provider) throws ObjectUndefinedException
    {

            if (!providers.containsKey(provider)) {
                throw new ObjectUndefinedException("Provider does not Exist");
            }
            else return globalBills.billsFromProvider(providers.get(provider));
    }


    public void onAllDevicesInLocation(int house_id, String location, boolean on) throws ObjectUndefinedException
    {
        if(houses.containsKey(house_id))
        {
            House h = houses.get(house_id);
           h.onAllDevicesInLocation(location,on);
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void onDevice(int house_id, int device_id, boolean on) throws ObjectUndefinedException
    {
        if(houses.containsKey(house_id))
        {
            House h = houses.get(house_id);
            h.onDevice(device_id, on);
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void addDeviceToHouse(SmartDevice sd, String location, int house_id) throws ObjectAlreadyCreatedException, ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            House h = houses.get(house_id);
            h.addDevice(sd.clone(), location);
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void addLocationToHouse(String location, int house_id) throws ObjectAlreadyCreatedException, ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            House h = houses.get(house_id);
            h.addLocation(location);
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public SmartDevice getDevice(int house_id,int device_id)throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            House house=getHouse(house_id);
            return  house.getDevice(device_id);
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public House getHouse(int house_id) throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            return houses.get(house_id).clone();
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public Map<String, EnergyProvider> getProviders()
    {
        Map<String, EnergyProvider> res = new HashMap<>();
        for (EnergyProvider e : providers.values())
        {
            res.put(e.toString(), e.clone());
        }
        return res;
    }

    public LocalDate getCurrentSimDate(){
        return currentSimDate;
    }

    public Map<Integer,House> allHouses()
    {
        Map<Integer,House> allhouses = new HashMap<>();
        for (House h : houses.values()){
            allhouses.put(h.getId(),h.clone());
        }
        return allhouses;
    }

    public void serialize(String fileName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public void changeTax(String provider, double newtax) throws ObjectUndefinedException
    {
        if(this.providers.containsKey(provider))
        {
            this.providers.get(provider).setTax(newtax);
        }
        else {
            throw new ObjectUndefinedException("Provider does not exist");
        }
    }

    public void changeBaseValue(String provider, double newBaseValue) throws ObjectUndefinedException
    {
        if(this.providers.containsKey(provider))
        {
            this.providers.get(provider).setBaseValue(newBaseValue);
        }
        else {
            throw new ObjectUndefinedException("Provider does not exist");
        }
    }

    public void changeVolume(int house_id,int device_id,int volume)throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartSpeaker){((SmartSpeaker) houses.get(house_id).getDevice(device_id)).setVolume(volume);}
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void changeRadio(int house_id,int device_id,String radio)throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartSpeaker){((SmartSpeaker) houses.get(house_id).getDevice(device_id)).setRadio(radio);}
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void changeDimension(int house_id,int device_id,double dimension)throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartBulb){((SmartBulb) houses.get(house_id).getDevice(device_id)).setDimension(dimension);}
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void changeTone(int house_id,int device_id,Tone tone)throws ObjectUndefinedException{
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartBulb){((SmartBulb) houses.get(house_id).getDevice(device_id)).setTone(tone);}
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }
    public void changeDailyIntake(int house_id,int device_id,double dailyintake)throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartBulb){((SmartBulb) houses.get(house_id).getDevice(device_id)).setDimension(dailyintake);}
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void changeFilesize(int house_id,int device_id,double filesize)throws ObjectUndefinedException{
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartCamera)
            {
                ((SmartCamera) houses.get(house_id).getDevice(device_id)).setSaveFileSize(filesize);
            }
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void changeresolution_h(int house_id,int device_id,int resolution_h)throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartCamera)
            {
                ((SmartCamera) houses.get(house_id).getDevice(device_id)).setResolution_height(resolution_h);
            }
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public void changeresolution_w(int house_id,int device_id,int resolution_w)throws ObjectUndefinedException {
        if(houses.containsKey(house_id))
        {
            if (houses.get(house_id).getDevice(device_id) instanceof SmartCamera)
            {
                ((SmartCamera) houses.get(house_id).getDevice(device_id)).setResolution_width(resolution_w);
            }
        }
        else {
            throw new ObjectUndefinedException("House does not exist");
        }
    }

    public int devices_number()
    {
        int total = 0;
        for(House h : houses.values())
        {
            total+= h.totalDevices();
        }
        return total;
    }

    public int houses_number()
    {
        return houses.size();
    }

    public Map<Integer,Double> housesConsumeInPeriod(LocalDate d1, LocalDate d2)
    {
        Map<Integer,Double> house_consume_map = new HashMap<>();
        for(Integer i : houses.keySet())
        {
            if(globalBills.containsBillsFromHouse(i)) {
                house_consume_map.put(i, globalBills.intakeFromHouseInPeriod(i,d1,d2));
            }
        }
        return house_consume_map;
    }
    public List<Integer> biggestEnergyConsumersInPeriod(LocalDate d1, LocalDate d2)
    {
        Map<Integer,Double> house_consume_map = housesConsumeInPeriod(d1,d2);
        Comparator<Integer> c = (dob1,dob2) -> Double.compare(house_consume_map.get(dob2),house_consume_map.get(dob1));
        List<Integer> orderlist = new ArrayList<>();

        for(Integer i : house_consume_map.keySet())
        {
            orderlist.add(i);
        }
        orderlist.sort(c);

        return orderlist;
    }
}
