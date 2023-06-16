import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueriesTest {

    void createHouse(Simulation sim) throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        int house_id, device_id;
        SmartBulb sb;
        SmartCamera sc;
        SmartSpeaker ss;

        sim.addProvider("EDP Comercial");
        sim.addProvider("Galp Energia");
        sim.addProvider("Iberdrola");
        sim.addProvider("Endesa");
        sim.addProvider("MEO Energia");

        house_id = 1;
        sim.addHouse(house_id, "EDP Comercial", "Francisco Melo", "1");
        sim.addLocationToHouse("Quarto", 1);
        sim.addLocationToHouse("Cozinha", 1);
        sim.addLocationToHouse("Sala", 1);

        house_id++;
        sim.addHouse(house_id, "Galp Energia", "Pedro Pereira", "2");
        sim.addLocationToHouse("Quarto", 2);
        sim.addLocationToHouse("Cozinha", 2);
        sim.addLocationToHouse("Sala", 2);

        house_id++;
        sim.addHouse(house_id, "Endesa", "João Filipe", "3");
        sim.addLocationToHouse("Quarto", 3);
        sim.addLocationToHouse("Cozinha", 3);
        sim.addLocationToHouse("Sala", 3);

        house_id++;
        sim.addHouse(house_id, "Iberdrola", "Luísa Fernandes", "4");
        sim.addLocationToHouse("Quarto", 4);
        sim.addLocationToHouse("Cozinha", 4);
        sim.addLocationToHouse("Sala", 4);

        house_id++;
        sim.addHouse(house_id, "MEO Energia", "Ana Marques", "5");
        sim.addLocationToHouse("Quarto", 5);
        sim.addLocationToHouse("Cozinha", 5);
        sim.addLocationToHouse("Sala", 5);

        device_id = 1;
        sb = new SmartBulb(device_id, true, Tone.WARM, 2, 1.64);
        device_id++;
        sc = new SmartCamera(device_id, true, 1280, 720, 10);
        device_id++;
        ss = new SmartSpeaker(device_id, true, 77, "TSF", "JBL");
        sim.addDeviceToHouse(sb, "Quarto", 1);
        sim.addDeviceToHouse(sc, "Cozinha", 1);
        sim.addDeviceToHouse(ss, "Sala", 1);

        device_id++;
        sb = new SmartBulb(device_id, true, Tone.COLD, 2.3, 1.24);
        device_id++;
        sc = new SmartCamera(device_id, true, 1920, 1080, 15);
        device_id++;
        ss = new SmartSpeaker(device_id, true, 85, "Comercial", "Sony");
        sim.addDeviceToHouse(sb, "Quarto", 2);
        sim.addDeviceToHouse(sc, "Cozinha", 2);
        sim.addDeviceToHouse(ss, "Sala", 2);

        device_id++;
        sb = new SmartBulb(device_id, true, Tone.NEUTRAL, 1.9, 1.45);
        device_id++;
        sc = new SmartCamera(device_id, true, 852, 480, 7);
        device_id++;
        ss = new SmartSpeaker(device_id, true, 63, "Mega Hits", "Marshall");
        sim.addDeviceToHouse(sb, "Quarto", 3);
        sim.addDeviceToHouse(sc, "Cozinha", 3);
        sim.addDeviceToHouse(ss, "Sala", 3);

        device_id++;
        sb = new SmartBulb(device_id, true, Tone.WARM, 2.1, 1.52);
        device_id++;
        sc = new SmartCamera(device_id, true, 3840, 2160, 25);
        device_id++;
        ss = new SmartSpeaker(device_id, true, 88, "RR", "BOSE");
        sim.addDeviceToHouse(sb, "Quarto", 4);
        sim.addDeviceToHouse(sc, "Cozinha", 4);
        sim.addDeviceToHouse(ss, "Sala", 4);

        device_id++;
        sb = new SmartBulb(device_id, true, Tone.COLD, 2.45, 1.67);
        device_id++;
        sc = new SmartCamera(device_id, true, 480, 360, 4.9);
        device_id++;
        ss = new SmartSpeaker(device_id, true, 72, "RFM", "Harman Kardon");
        sim.addDeviceToHouse(sb, "Quarto", 5);
        sim.addDeviceToHouse(sc, "Cozinha", 5);
        sim.addDeviceToHouse(ss, "Sala", 5);
    }

    @Test
    void houseThatSpendTheMostTest() throws ObjectAlreadyCreatedException, ObjectUndefinedException, PreviousDateException
    {
        LocalDate initialDate = LocalDate.now();
        Simulation sim = new Simulation(initialDate);

        createHouse(sim);

        int daysToAdvance = 31;
        LocalDate finalDate = LocalDate.now().plusDays(daysToAdvance);
        sim.advanceToDate(finalDate);

        double di_h1, di_h2, di_h3, di_h4, di_h5;
        di_h1 = sim.getHouse(1).getHouseDailyIntake();
        di_h2 = sim.getHouse(2).getHouseDailyIntake();
        di_h3 = sim.getHouse(3).getHouseDailyIntake();
        di_h4 = sim.getHouse(4).getHouseDailyIntake();
        di_h5 = sim.getHouse(5).getHouseDailyIntake();

        double dp_h1, dp_h2, dp_h3, dp_h4, dp_h5;
        dp_h1 = sim.getHouse(1).getHouseDailyPrice();
        dp_h2 = sim.getHouse(2).getHouseDailyPrice();
        dp_h3 = sim.getHouse(3).getHouseDailyPrice();
        dp_h4 = sim.getHouse(4).getHouseDailyPrice();
        dp_h5 = sim.getHouse(5).getHouseDailyPrice();

        double energy_spent = (double) Math.round(di_h3*daysToAdvance*100)/100;
        double money_paid = (double) Math.round(dp_h1*daysToAdvance*100)/100;

        assertEquals(energy_spent, sim.mostEnergySpenderBillInLastPeriod().getIntake());
        assertEquals(money_paid, sim.mostExpensiveBillInLastPeriod().getPrice());
    }

    @Test
    void providerWithBiggestVolumeTest() throws ObjectAlreadyCreatedException, ObjectUndefinedException, PreviousDateException
    {
        LocalDate initialDate = LocalDate.now();
        Simulation sim = new Simulation(initialDate);

        createHouse(sim);

        int daysToAdvance = 31;
        LocalDate finalDate = LocalDate.now().plusDays(daysToAdvance);
        sim.advanceToDate(finalDate);
        assertEquals("EDP Comercial", sim.providerWithBiggestVolume().toString());
    }

    @Test
    void biggestEnergyConsumersInPeriodTest() throws ObjectAlreadyCreatedException, ObjectUndefinedException, PreviousDateException
    {
        LocalDate initialDate = LocalDate.now();
        Simulation sim = new Simulation(initialDate);

        createHouse(sim);

        int daysToAdvance = 31;
        LocalDate finalDate = LocalDate.now().plusDays(daysToAdvance);
        sim.advanceToDate(finalDate);

        List<Integer> consumers_list = sim.biggestEnergyConsumersInPeriod(initialDate, finalDate);
        assertEquals(3, sim.biggestEnergyConsumersInPeriod(initialDate, finalDate).get(0));
        assertEquals(4, sim.biggestEnergyConsumersInPeriod(initialDate, finalDate).get(1));
        assertEquals(1, sim.biggestEnergyConsumersInPeriod(initialDate, finalDate).get(2));
        assertEquals(5, sim.biggestEnergyConsumersInPeriod(initialDate, finalDate).get(3));
        assertEquals(2, sim.biggestEnergyConsumersInPeriod(initialDate, finalDate).get(4));
    }


}
