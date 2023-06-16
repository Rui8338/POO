import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class House implements Serializable {
    private int id;
    private String ownerName;
    private String ownerNIF;
    private Map<Integer, SmartDevice> devices;
    private Map<String, Set<Integer>> locations;
    private EnergyProvider provider;

    public House(int id, EnergyProvider provider)
    {
        this.id = id;
        this.ownerName = "";
        this.ownerNIF = "";
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.provider = provider;
    }

    public House(int id, EnergyProvider provider, String ownerName, String nif)
    {
        this.id = id;
        this.ownerName = ownerName;
        this.ownerNIF = nif;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.provider = provider;
    }

    public House(House h)
    {
        this.id = h.getId();
        this.ownerName = h.getOwnerName();
        this.ownerNIF = h.getOwnerNIF();
        this.provider = h.getProvider();
        this.locations = h.getLocations();
        this.devices = h.getDevices();
    }
    public void setOwnerName(String name)
    {
        this.ownerName = name;
    }

    public void setOwnerNIF(String nif)
    {
        this.ownerNIF = nif;
    }

    public void setProvider(EnergyProvider provider)
    {
        this.provider = provider;
    }

    public int getId()
    {
        return this.id;
    }

    public String getOwnerName()
    {
        return this.ownerName;
    }

    public String getOwnerNIF()
    {
        return this.ownerNIF;
    }

    public EnergyProvider getProvider()
    {
        return this.provider;
    }

    public Map<String, Set<Integer>> getLocations()
    {
        Map<String, Set<Integer>> res = new HashMap<>();
        for(String location : this.locations.keySet())
        {
            res.put(location, new HashSet<>(this.locations.get(location)));
        }
        return res;
    }

    public Map<Integer, SmartDevice> getDevices()
    {
        Map<Integer,SmartDevice> res = new HashMap<>();
        for(Integer id : this.devices.keySet())
        {
            res.put(id, this.devices.get(id));
        }
        return res;
    }

    public Set<SmartDevice> getDevicesFromLocation(String location) throws ObjectUndefinedException
    {
        if(!containsLocation(location)) {
            throw new ObjectUndefinedException("Location does not exist");
        }
        Set<SmartDevice> res = new HashSet<>();
        if(locations.containsKey(location))
        {
            for (Integer i : this.locations.get(location))
            {
                res.add(devices.get(i));
            }
        }
        return res;
    }

    public void addLocation(String location) throws ObjectAlreadyCreatedException
    {
        if (!locations.containsKey(location))
        {
            locations.put(location, new HashSet<>());
        }
        else throw new ObjectAlreadyCreatedException("Location already exists");
    }

    public boolean containsDevice(int id)
    {
        return this.devices.containsKey(id);
    }

    public SmartDevice getDevice(int id) throws ObjectUndefinedException
    {
        if(!containsDevice(id))
        {
            throw new ObjectUndefinedException("Device does not exist");
        }
        return devices.get(id);
    }

    public boolean containsLocation(String location)
    {
        return this.locations.containsKey(location);
    }

    public void addDevice(SmartDevice sd, String location) throws ObjectUndefinedException, ObjectAlreadyCreatedException
    {
        if (locations.containsKey(location))
        {
            if (!devices.containsKey(sd.getId()))
            {
                devices.put(sd.getId(), sd);
                Set<Integer> set = this.locations.get(location);
                set.add(sd.getId());
            }
            else {
                throw new ObjectAlreadyCreatedException("Device already exists");
            }
        }
        else {
            throw new ObjectUndefinedException("Location does not exist");
        }
    }

    public void onDevice(int id, boolean on) throws ObjectUndefinedException
    {
        if(this.devices.containsKey(id))
        {
            this.devices.get(id).setOn(on);
        }
        else {
            throw new ObjectUndefinedException("Device does not exist");
        }
    }

    public void onAllDevicesInLocation(String location, boolean on) throws ObjectUndefinedException
    {
        if(this.locations.containsKey(location))
        {
            Set<Integer> locationDevs = this.locations.get(location);
            for(Integer i : locationDevs)
            {
                this.devices.get(i).setOn(on);
            }
        }
        else {
            throw new ObjectUndefinedException("Location does not exist");
        }
    }

    public double getDeviceDailyIntake(int deviceID) throws ObjectUndefinedException
    {
        if(devices.containsKey(deviceID))
        {
            double di = this.devices.get(deviceID).getDailyIntake();
            return (double)Math.round(di*100)/100;
        }
        else
        {
            throw new ObjectUndefinedException("Device does not exist");
        }
    }
    public double getHouseDailyIntake()
    {
        double di = 0;
        for(SmartDevice sd : this.devices.values())
        {
            if(sd.getOn()) {
                di = di + sd.getDailyIntake();
            }
        }
        return (double)Math.round(di*100)/100;
    }

    public double getDeviceDailyPrice(int deviceID) throws ObjectUndefinedException
    {
        return (double) Math.round(this.getDeviceDailyIntake(deviceID)*this.provider.dailyPricePerKw(this)*100)/100;
    }

    public double getHouseDailyPrice()
    {
        double di = getHouseDailyIntake() * provider.dailyPricePerKw(this);
        return (double)Math.round(di * 100)/100;
    }

    public Bill generateBill(LocalDate d1, LocalDate d2)
    {
        int daysPassed = (int) ChronoUnit.DAYS.between(d1,d2);
        double price = getHouseDailyPrice()*daysPassed;
        provider.addFactoringVoume(price);
        return new Bill(d1, d2, getHouseDailyIntake() * daysPassed, price, id, provider);
    }

    public int totalDevices()
    {
        return this.devices.size();
    }

    public House clone()
    {
        return new House(this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof House))
            return false;

        House h = (House) o;

        if (this.getId() != h.getId())
            return false;
        if (!this.getProvider().equals(h.getProvider()))
            return false;
        if (! this.getOwnerName().equals(h.getOwnerName()))
            return false;
        if (! this.getOwnerNIF().equals(h.getOwnerNIF()))
            return false;

        return this.getLocations().equals(h.getLocations()) && this.getDevices().equals(h.getDevices());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Casa:");
        sb.append(this.ownerName);
        sb.append(",");
        sb.append(this.ownerNIF);
        sb.append(",");
        sb.append(this.provider.toString());
        sb.append("\n");
        for (String location : this.locations.keySet())
        {
            sb.append("Divisao:");
            sb.append(location);
            sb.append("\n");
            for (Integer id : this.locations.get(location))
            {
                sb.append(this.devices.get(id).logToString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
