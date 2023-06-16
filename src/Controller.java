import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Controller {
    public static Simulation deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Simulation simulation = (Simulation) ois.readObject();
        ois.close();
        return simulation;
    }

    public static void execute() {
        boolean beggin = View.beggin();
        Simulation simulation = null;
        if (beggin==true) {
            try {
                simulation = deserialize(View.askFile());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else{
            LocalDate date = View.newDate();
            simulation = new Simulation(date);
            try {
                simulation.addProvider("EDP Comercial");
            }
            catch (ObjectUndefinedException e) {
                View.error(e);
            }
            catch (ObjectAlreadyCreatedException e) {
                View.error(e);
            }
            try {
                simulation.addProvider("Galp Energia");
            }
            catch (ObjectUndefinedException e) {
                View.error(e);
            }
            catch (ObjectAlreadyCreatedException e) {
                View.error(e);
            }
            try {simulation.addProvider("Iberdrola");
            }
            catch (ObjectUndefinedException e) {
                View.error(e);
            }
            catch (ObjectAlreadyCreatedException e) {
                View.error(e);
            }
            try {
                simulation.addProvider("Endesa");
            }
            catch (ObjectUndefinedException e) {
                View.error(e);
            }
            catch (ObjectAlreadyCreatedException e)
            {
                View.error(e);
            }
            try {
                simulation.addProvider("MEO Energia");
            }
            catch (ObjectUndefinedException e) {
                View.error(e);
            }
            catch (ObjectAlreadyCreatedException e) {
                View.error(e);
            }
        }

        boolean exit = false;

        while (!exit) {
            int choice = -1;
            while (choice < 0 || choice > 11) {
                View.clearScreen();
                choice = View.MenuInicial(simulation.getCurrentSimDate());
            }
            switch (choice) {

                case 1:
                    String file = View.askFile();
                    try {
                        simulation.serialize(file);
                    } catch (Exception e) {
                        View.error(e);
                    }
                    break;
                case 2:
                    boolean exit2 = false;
                    while (!exit2) {
                        int choice2 = -1;
                        while (choice2 < 0 || choice2 > 4) {
                            View.clearScreen();
                            choice2 = View.HouseMenu();
                        }
                        switch (choice2) {
                            case 1:
                                try {
                                    int prov = View.houseProvider();
                                    switch (prov) {
                                        case 1:
                                              simulation.addHouse(simulation.houses_number() + 1, "EDP Comercial", View.ownerName(), View.ownerNif());
                                              break;
                                        case 2:
                                            simulation.addHouse(simulation.houses_number() + 1, "Galp Energia", View.ownerName(), View.ownerNif());
                                            break;
                                        case 3:
                                            simulation.addHouse(simulation.houses_number() + 1, "Iberdrola", View.ownerName(), View.ownerNif());
                                            break;
                                        case 4:
                                            simulation.addHouse(simulation.houses_number() + 1, "Endesa", View.ownerName(), View.ownerNif());
                                            break;
                                        case 5:
                                            simulation.addHouse(simulation.houses_number() + 1, "MEO Energia", View.ownerName(), View.ownerNif());
                                            break;
                                    }
                                } catch (ObjectAlreadyCreatedException e) {
                                    View.error(e);
                                } catch (ObjectUndefinedException e) {
                                    View.error(e);
                                }
                                break;
                            case 2:
                                boolean exit3 = false;
                                View.anounceHouses();
                                Map<Integer,House> housesprint = simulation.allHouses();
                                for (House h : housesprint.values()) {
                                    View.printHouses(h.getId(),h.getOwnerName(),h.getProvider().toString());
                                }
                                int house=View.houseId();
                                boolean exists;
                                exists = simulation.hasHouse(house);
                                if(exists == false){
                                    exit3=true;
                                    View.houseExist();
                                }
                                while (!exit3) {
                                    int edithousechoice = -1;
                                    while (edithousechoice < 0 || edithousechoice > 6) {
                                        View.clearScreen();
                                        edithousechoice = View.editHouseMenu();
                                    }
                                    switch (edithousechoice) {
                                        case 1:
                                            try {
                                                House housefromedit = simulation.getHouse(house);
                                                Map<Integer, SmartDevice> devices = housefromedit.getDevices();
                                                Map<String, Set<Integer>> location = housefromedit.getLocations();
                                                String energyProvider = housefromedit.getProvider().toString();
                                                View.housestate(devices, location, energyProvider);
                                                View.pressEnter();
                                                View.clearScreen();
                                            } catch (ObjectUndefinedException e) {
                                                View.error(e);
                                            }
                                            break;
                                        case 2:
                                            try {
                                                simulation.addLocationToHouse(View.askdivision(), house);
                                            } catch (Exception e) {
                                                View.error(e);
                                            }
                                            break;
                                        case 3:
                                            int device = View.device();
                                            switch (device) {
                                                case 1:
                                                    SmartCamera smartCamera = new SmartCamera(simulation.devices_number()+1,View.onOroff(), View.resolution_w(), View.resolution_h(), View.filesize());
                                                    try {
                                                        simulation.addDeviceToHouse(smartCamera.clone(), View.askdivision(), house);
                                                    } catch (ObjectUndefinedException e) {
                                                        View.error(e);
                                                    } catch (ObjectAlreadyCreatedException e) {
                                                        View.error(e);
                                                    }
                                                    break;
                                                case 2:
                                                    int toneloop=-1;
                                                    while (toneloop<1||toneloop>5) {
                                                        toneloop = View.tone();
                                                    }
                                                    Tone t = null;
                                                    if (toneloop==2) {
                                                        t = Tone.NEUTRAL;
                                                    } else if (toneloop==1) {
                                                        t = Tone.WARM;
                                                    } else if (toneloop == 3) {
                                                        t = Tone.COLD;
                                                    }
                                                    SmartBulb smartBulb = new SmartBulb(simulation.devices_number()+1,View.onOroff(), t, View.dimension(), View.dailyintake());
                                                    try {
                                                        simulation.addDeviceToHouse(smartBulb.clone(), View.askdivision(), house);
                                                    } catch (ObjectUndefinedException e) {
                                                        View.error(e);
                                                    } catch (ObjectAlreadyCreatedException e) {
                                                        View.error(e);
                                                    }
                                                    break;
                                                case 3:
                                                    SmartSpeaker smartSpeaker = new SmartSpeaker(simulation.devices_number()+1,View.onOroff(), View.volume(), View.radio(), View.brand());
                                                    try {
                                                        simulation.addDeviceToHouse(smartSpeaker.clone(), View.askdivision(), house);
                                                    } catch (ObjectUndefinedException e) {
                                                        View.error(e);
                                                    } catch (ObjectAlreadyCreatedException e) {
                                                        View.error(e);
                                                    }
                                                    break;
                                                case 0:
                                                    break;
                                            }
                                            break;
                                        case 4:
                                            try {
                                                int deviceid = View.deviceID();
                                                SmartDevice smD = simulation.getDevice(house, deviceid);
                                                if (smD instanceof SmartSpeaker) {
                                                    boolean SsSploop=false;
                                                    while (!SsSploop){
                                                        int SmSpchoice=-1;
                                                        while (SmSpchoice < 0 || SmSpchoice  > 4) {
                                                            View.clearScreen();
                                                            SmSpchoice = View.SmartSpeakerMenu();
                                                        }
                                                        switch (SmSpchoice) {
                                                            case 1:
                                                                simulation.changeVolume(house, deviceid, View.volume());
                                                                break;
                                                            case 2:
                                                                simulation.changeRadio(house, deviceid, View.radio());
                                                                break;
                                                            case 3:
                                                                simulation.onDevice(house, deviceid, View.onOroff());
                                                                break;
                                                            case 0:
                                                                SsSploop = true;
                                                                break;
                                                        }
                                                    }
                                                } else if (smD instanceof SmartBulb) {
                                                    boolean SmBuloop=false;
                                                    while (!SmBuloop) {
                                                        int SmBulchoice = -1;
                                                        while (SmBulchoice < 0 || SmBulchoice > 5) {
                                                            View.clearScreen();
                                                            SmBulchoice = View.SmartBulbMenu();
                                                        }
                                                        switch (SmBulchoice) {
                                                            case 1:
                                                                int tone2=-1;
                                                                while (tone2<1||tone2>5) {
                                                                    tone2 = View.tone();
                                                                }
                                                                Tone t = null;
                                                                if (tone2==2) {
                                                                    t = Tone.NEUTRAL;
                                                                } else if (tone2==1) {
                                                                    t = Tone.WARM;
                                                                } else if (tone2 == 3) {
                                                                    t = Tone.COLD;
                                                                }
                                                                simulation.changeTone(house, deviceid, t);
                                                                break;
                                                            case 2:
                                                                simulation.changeDimension(house, deviceid, View.dimension());
                                                                break;
                                                            case 3:
                                                                simulation.changeDailyIntake(house, deviceid, View.dailyintake());
                                                                break;
                                                            case 4:
                                                                simulation.onDevice(house, deviceid, View.onOroff());
                                                                break;
                                                            case 0:
                                                                SmBuloop = true;
                                                                break;
                                                        }
                                                    }
                                                } else if (smD instanceof SmartCamera) {
                                                    boolean SmCamloop = false;
                                                    while (!SmCamloop) {
                                                        int SmCamchoice = -1;
                                                        while (SmCamchoice < 0 || SmCamchoice > 5) {
                                                            View.clearScreen();
                                                            SmCamchoice = View.SmartCameraMenu();
                                                        }
                                                        switch (SmCamchoice) {
                                                            case 1:
                                                                simulation.changeresolution_w(house, deviceid, View.resolution_w());
                                                                break;
                                                            case 2:
                                                                simulation.changeresolution_h(house, deviceid, View.resolution_h());
                                                                break;
                                                            case 3:
                                                                simulation.changeFilesize(house, deviceid, View.filesize());
                                                                break;
                                                            case 4:
                                                                simulation.onDevice(house, deviceid, View.onOroff());
                                                                break;
                                                            case 0:SmCamloop=true;
                                                                break;
                                                        }
                                                    }
                                                }
                                            } catch (ObjectUndefinedException e) {
                                                View.error(e);
                                            }
                                            break;
                                        case 5:
                                            try {
                                                simulation.onAllDevicesInLocation(house, View.askdivision(), View.onOroff());
                                            } catch (ObjectUndefinedException e) {
                                                View.error(e);
                                            }
                                            break;
                                        case 6:
                                            try{
                                                 int prov=-1;
                                                 while (prov<1||prov>5) {
                                                     prov = View.houseProvider();
                                                 }
                                                 switch (prov) {
                                                        case 1:
                                                           simulation.changeHouseProvider(house, "EDP Comercial");
                                                           break;
                                                        case 2:
                                                            simulation.changeHouseProvider(house, "Galp Energia");
                                                            break;
                                                        case 3:
                                                            simulation.changeHouseProvider(house, "Iberdrola");
                                                             break;
                                                        case 4:
                                                            simulation.changeHouseProvider(house, "Endesa");
                                                            break;
                                                        case 5:
                                                            simulation.changeHouseProvider(house, "MEO Energia");
                                                            break;
                                                }
                                            }
                                            catch (ObjectUndefinedException e) {
                                            View.error(e);
                                        }
                                        break;
                                        case 0:
                                            View.clearScreen();
                                            exit3=true;
                                            break;
                                    }
                                }
                                break;
                            case 3:
                                View.anounceHouses();
                                Map<Integer,House> houses3 = simulation.allHouses();
                                for (House h : houses3.values()) {
                                    View.printHouses(h.getId(),h.getOwnerName(),h.getProvider().toString());
                                }
                                View.pressEnter();
                                View.clearScreen();
                                break;
                            case 0:
                                View.clearScreen();
                                exit2=true;
                                break;
                        }
                    }
                    break;
                case 3:
                    boolean exit3 = false;
                    while (!exit3) {
                        int choice3 = -1;
                        while (choice3 < 0 || choice3 > 3) {
                            View.clearScreen();
                            choice3 = View.ProviderMenu();
                        }
                        switch (choice3){
                            case 1:
                                try {
                                    int prov=-1;
                                    while (prov<1||prov>5) {
                                        prov = View.houseProvider();
                                    }
                                    switch (prov) {
                                        case 1:
                                            simulation.changeTax("EDP Comercial",View.askTax());
                                            break;
                                        case 2:
                                            simulation.changeTax("Galp Energia",View.askTax());
                                            break;
                                        case 3:
                                            simulation.changeTax("Iberdrola",View.askTax());
                                            break;
                                        case 4:
                                            simulation.changeTax("Endesa",View.askTax());
                                            break;
                                        case 5:
                                            simulation.changeTax("MEO Energia",View.askTax());
                                            break;
                                    }
                                }
                                catch (ObjectUndefinedException e){View.error(e);}
                                break;
                            case 2:
                                try {
                                    int prov=-1;
                                    while (prov<1||prov>5) {
                                        prov = View.houseProvider();
                                    }
                                    switch (prov) {
                                        case 1:
                                            simulation.changeBaseValue("EDP Comercial",View.baseValue());
                                            break;
                                        case 2:
                                            simulation.changeBaseValue("Galp Energia",View.baseValue());
                                            break;
                                        case 3:
                                            simulation.changeBaseValue("Iberdrola",View.baseValue());
                                            break;
                                        case 4:
                                            simulation.changeBaseValue("Endesa",View.baseValue());
                                            break;
                                        case 5:
                                            simulation.changeBaseValue("MEO Energia",View.baseValue());
                                            break;
                                    }
                                }
                                catch (ObjectUndefinedException e){View.error(e);}
                                break;
                            case 3:
                                View.anounceproviders();
                                Map<String, EnergyProvider> providers = simulation.getProviders();
                                for (Map.Entry<String,EnergyProvider> ep : providers.entrySet()) {
                                    View.printProvider(ep.getKey(),ep.getValue().getBaseValue(),ep.getValue().getTax());
                                }
                                View.pressEnter();
                                View.clearScreen();
                                break;
                            case 0:
                                exit3=true;
                                break;
                        }
                    }
                    break;
                case 4:
                    LocalDate newdate = simulation.getCurrentSimDate();
                    newdate=newdate.plusDays(View.ndays());
                    try{simulation.advanceToDate(newdate);}
                    catch (PreviousDateException e){View.error(e);}
                    break;
                case 5:
                    LocalDate date = View.newDate();
                    try{simulation.advanceToDate(date);}
                    catch (PreviousDateException e){View.error(e);}
                    break;
                case 6:
                    try{
                        Bill bill = simulation.mostExpensiveBillInLastPeriod();
                        try {
                            House housefrombill = simulation.getHouse(bill.getHouseID());
                            View.mostExpensiveHouse(bill.getHouseID(),housefrombill.getOwnerName(),housefrombill.getOwnerNIF(), bill.getProvider(),bill.getPrice());
                            View.pressEnter();
                            View.clearScreen();
                        }
                        catch (ObjectUndefinedException e){View.error(e);}
                    }
                    catch (ObjectUndefinedException e){View.error(e);}
                    break;
                case 7:
                    try{
                        Bill bill = simulation.mostEnergySpenderBillInLastPeriod();
                        try {
                            House housefrombill = simulation.getHouse(bill.getHouseID());
                            View.mostConsumeHouse(bill.getHouseID(),housefrombill.getOwnerName(),housefrombill.getOwnerNIF(), bill.getProvider(),bill.getIntake());
                            View.pressEnter();
                            View.clearScreen();
                        }
                        catch (ObjectUndefinedException e){View.error(e);}
                    }
                    catch (ObjectUndefinedException e){View.error(e);}
                    break;
                case 8:
                    try {
                        EnergyProvider provider = simulation.providerWithBiggestVolume();
                        View.printProvider(provider);
                        View.pressEnter();
                        View.clearScreen();
                    }
                    catch (ObjectUndefinedException e){View.error(e);}
                    break;
                case 9:
                    try {
                        int prov8=-1;
                        while (prov8<1||prov8>5) {
                            prov8 = View.houseProvider();
                        }
                        List<Bill> list=null;
                        switch (prov8) {
                            case 1:
                                list = simulation.billsFromProvider("EDP Comercial");
                                break;
                            case 2:
                                list = simulation.billsFromProvider("Galp Energia");
                                break;
                            case 3:
                                list = simulation.billsFromProvider("Iberdrola");
                                break;
                            case 4:
                                list = simulation.billsFromProvider("Endesa");
                                break;
                            case 5:
                                list = simulation.billsFromProvider("MEO Energia");
                                break;
                        }
                        View.printBill(list);
                        View.pressEnter();
                        View.clearScreen();
                    }
                    catch (ObjectUndefinedException e){View.error(e);}
                    break;
                case 10:
                    LocalDate inDate=View.initDate();
                    LocalDate finDate=View.finalDate();
                    Map<Integer,Double> mapspent = simulation.housesConsumeInPeriod(inDate,finDate);
                    List<Integer> ord =simulation.biggestEnergyConsumersInPeriod(inDate,finDate);
                    View.BiggestConsumers(inDate,finDate);
                    for (Integer i : ord)
                    {
                        View.printBigConsumers(i,mapspent.get(i));
                    }
                    break;
                case 11:
                    try
                    {
                        ParserCommands.parseCommandsFile(simulation,View.askFile());
                    }
                    catch (ObjectAlreadyCreatedException e)
                    {
                        View.error(e);
                    }
                    catch (ObjectUndefinedException e)
                    {
                        View.error(e);
                    }
                    catch (PreviousDateException e)
                    {
                        View.error(e);
                    }
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }
}
