import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HouseTest {

    @Test
    void testConstructor()
    {
        House h1 = new House(1, new EDP());
        House h2 = new House(2, new EDP(), "Dono1", "123456789");
        House h3 = new House(h1);
        assertNotNull(h1);
        assertNotNull(h2);
        assertNotNull(h3);
    }

    @Test
    void testSetOwnerName()
    {
        House h1 = new House(1, new EDP());
        assertEquals("", h1.getOwnerName());
        h1.setOwnerName("Owner1");
        assertEquals("Owner1", h1.getOwnerName());
    }

    @Test
    void testSetOwnerNIF()
    {
        House h1 = new House(1, new EDP());
        assertEquals("", h1.getOwnerNIF());
        h1.setOwnerNIF("000111222");
        assertEquals("000111222", h1.getOwnerNIF());
    }

    @Test
    void testSetEneryProvider()
    {
        EnergyProvider ep1 = new EDP();
        House h1 = new House(1, ep1);
        assertEquals(ep1, h1.getProvider());

        EnergyProvider ep2 = new Iberdrola();
        h1.setProvider(ep2);
        assertEquals(ep2, h1.getProvider());
    }

    @Test
    void testAddLocation() throws ObjectAlreadyCreatedException
    {
        String location1 = "Bedroom";
        String location2 = "Bathroom";
        House house = new House(1, new EDP());
        house.addLocation(location1);
        assertTrue(house.containsLocation(location1));
        assertFalse(house.containsLocation(location2));
    }

    @Test
    void testContainsDevice() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        House house = new House(1, new EDP());
        assertFalse(house.containsDevice(1));
        house.addLocation("Kitchen");
        house.addDevice(new SmartSpeaker(1), "Kitchen");
        assertTrue(house.containsDevice(1));
    }

    @Test
    void testContainsLocation() throws ObjectAlreadyCreatedException
    {
        House house = new House(1, new EDP());
        assertFalse(house.containsLocation("Kitchen"));
        house.addLocation("Kitchen");
        assertTrue(house.containsLocation("Kitchen"));
    }

    @Test
    void testAddDevice() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(1);
        SmartSpeaker sd2 = new SmartSpeaker(2);
        House house = new House(1, new EDP());
        try {
            house.addDevice(sd1, "Kitchen");
        }
        catch (ObjectUndefinedException e) {}
        assertFalse(house.containsDevice(sd1.getId()));
        house.addLocation("Bedroom");
        house.addDevice(sd2, "Bedroom");
        assertTrue(house.containsDevice(sd2.getId()));
    }

    @Test
    void testOnDevice() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(0, false);
        House house = new House(1, new EDP());
        house.addLocation("Bathroom");
        house.addDevice(sd1,"Bathroom");
        house.onDevice(sd1.getId(),true);
        SmartDevice sd2 = house.getDevice(sd1.getId());
        assertTrue(sd2.getOn());
    }

    @Test
    void testOnAllDevicesInLocation() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(0,false);
        SmartSpeaker sd2 = new SmartSpeaker(1,true);
        SmartSpeaker sd3 = new SmartSpeaker(2,true);
        SmartSpeaker sd4 = new SmartSpeaker(3,false);
        House house = new House(1, new EDP());
        house.addLocation("Bathroom");
        house.addDevice(sd1,"Bathroom");
        house.addDevice(sd2,"Bathroom");
        house.addDevice(sd3,"Bathroom");
        house.addDevice(sd4,"Bathroom");
        house.onAllDevicesInLocation("Bathroom",true);
        for(SmartDevice sd : house.getDevicesFromLocation("Bathroom"))
        {
            assertTrue(sd.getOn());
        }
    }

    @Test
    void testGetDeviceDailyIntake() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(1,false);
        SmartBulb sb1 = new SmartBulb(2, true, Tone.NEUTRAL, 5, 1);
        SmartBulb sb2 = new SmartBulb(3, true, Tone.WARM, 5, 1);
        SmartBulb sb3 = new SmartBulb(4, true, Tone.COLD, 5, 1);
        SmartCamera sc1 = new SmartCamera(5);
        SmartCamera sc2 = new SmartCamera(6, true, 1920, 1080, 25);
        SmartSpeaker ss1 = new SmartSpeaker(7);
        SmartSpeaker ss2 = new SmartSpeaker(8, true,88, "TSF", "Bose");

        House house = new House(1, new EDP());
        house.addLocation("Bathroom");
        house.addDevice(sd1,"Bathroom");
        house.addDevice(sb1,"Bathroom");
        house.addDevice(sb2,"Bathroom");
        house.addDevice(sb3,"Bathroom");
        house.addDevice(sc1,"Bathroom");
        house.addDevice(sc2,"Bathroom");
        house.addDevice(ss1,"Bathroom");
        house.addDevice(ss2,"Bathroom");

        assertEquals(0, house.getDeviceDailyIntake(1));

        assertEquals(1.50, house.getDeviceDailyIntake(2));
        assertEquals(1.75, house.getDeviceDailyIntake(3));
        assertEquals(1.25, house.getDeviceDailyIntake(4));

        assertEquals(0, house.getDeviceDailyIntake(5));
        assertEquals(0.44, house.getDeviceDailyIntake(6));

        assertEquals(0, house.getDeviceDailyIntake(7));
        assertEquals(0.88, house.getDeviceDailyIntake(8));
    }

    @Test
    void testGetHouseDailyIntake() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(1,false);
        SmartBulb sb1 = new SmartBulb(2, true, Tone.NEUTRAL, 5, 1);
        SmartBulb sb2 = new SmartBulb(3, true, Tone.WARM, 5, 1);
        SmartBulb sb3 = new SmartBulb(4, true, Tone.COLD, 5, 1);
        SmartCamera sc1 = new SmartCamera(5);
        SmartCamera sc2 = new SmartCamera(6, true, 1920, 1080, 25);
        SmartSpeaker ss1 = new SmartSpeaker(7);
        SmartSpeaker ss2 = new SmartSpeaker(8, true,88, "TSF", "Bose");

        House house = new House(1, new EDP());
        house.addLocation("Bathroom");
        assertEquals(0, house.getHouseDailyIntake());

        house.addDevice(sd1,"Bathroom");
        house.addDevice(sb1,"Bathroom");
        house.addDevice(sb2,"Bathroom");
        house.addDevice(sb3,"Bathroom");
        house.addDevice(sc1,"Bathroom");
        house.addDevice(sc2,"Bathroom");
        house.addDevice(ss1,"Bathroom");
        house.addDevice(ss2,"Bathroom");

        assertEquals(5.82, house.getHouseDailyIntake());
    }

    @Test
    void testGetDeviceDailyPrice() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(1,false);
        SmartBulb sb1 = new SmartBulb(2, true, Tone.NEUTRAL, 5, 1);
        SmartBulb sb2 = new SmartBulb(3, true, Tone.WARM, 5, 1);
        SmartBulb sb3 = new SmartBulb(4, true, Tone.COLD, 5, 1);
        SmartCamera sc1 = new SmartCamera(5);
        SmartCamera sc2 = new SmartCamera(6, true, 1920, 1080, 25);
        SmartSpeaker ss1 = new SmartSpeaker(7);
        SmartSpeaker ss2 = new SmartSpeaker(8, true,88, "TSF", "Bose");

        House house = new House(1, new EDP());
        house.addLocation("Bathroom");
        house.addDevice(sd1,"Bathroom");
        house.addDevice(sb1,"Bathroom");
        house.addDevice(sb2,"Bathroom");
        house.addDevice(sb3,"Bathroom");
        house.addDevice(sc1,"Bathroom");
        house.addDevice(sc2,"Bathroom");
        house.addDevice(ss1,"Bathroom");
        house.addDevice(ss2,"Bathroom");

        assertEquals(0, house.getDeviceDailyPrice(1));

        assertEquals(0.32, house.getDeviceDailyPrice(2));
        assertEquals(0.38, house.getDeviceDailyPrice(3));
        assertEquals(0.27, house.getDeviceDailyPrice(4));

        assertEquals(0, house.getDeviceDailyPrice(5));
        assertEquals(0.10, house.getDeviceDailyPrice(6));

        assertEquals(0, house.getDeviceDailyPrice(7));
        assertEquals(0.19, house.getDeviceDailyPrice(8));
    }

    @Test
    void testGetHouseDailyPrice() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(1,false);
        SmartBulb sb1 = new SmartBulb(2, true, Tone.NEUTRAL, 5, 1);
        SmartBulb sb2 = new SmartBulb(3, true, Tone.WARM, 5, 1);
        SmartBulb sb3 = new SmartBulb(4, true, Tone.COLD, 5, 1);
        SmartCamera sc1 = new SmartCamera(5);
        SmartCamera sc2 = new SmartCamera(6, true, 1920, 1080, 25);
        SmartSpeaker ss1 = new SmartSpeaker(7);
        SmartSpeaker ss2 = new SmartSpeaker(8, true,88, "TSF", "Bose");

        House house = new House(1, new EDP());
        house.addLocation("Bathroom");
        house.addDevice(sd1,"Bathroom");
        house.addDevice(sb1,"Bathroom");
        house.addDevice(sb2,"Bathroom");
        house.addDevice(sb3,"Bathroom");
        house.addDevice(sc1,"Bathroom");
        house.addDevice(sc2,"Bathroom");
        house.addDevice(ss1,"Bathroom");
        house.addDevice(ss2,"Bathroom");

        assertEquals(1.26, house.getHouseDailyPrice());
    }

    @Test
    void testGenerateBill() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        LocalDate d1 = LocalDate.now();
        LocalDate d2 = LocalDate.now().plusMonths(1);

        SmartSpeaker sd1 = new SmartSpeaker(1,false);
        SmartBulb sb1 = new SmartBulb(2, true, Tone.NEUTRAL, 5, 1);
        SmartBulb sb2 = new SmartBulb(3, true, Tone.WARM, 5, 1);
        SmartBulb sb3 = new SmartBulb(4, true, Tone.COLD, 5, 1);
        SmartCamera sc1 = new SmartCamera(5);
        SmartCamera sc2 = new SmartCamera(6, true, 1920, 1080, 25);
        SmartSpeaker ss1 = new SmartSpeaker(7);
        SmartSpeaker ss2 = new SmartSpeaker(8, true,88, "TSF", "Bose");

        EnergyProvider ep1 = new EDP();
        House house = new House(1, ep1);
        house.addLocation("Bathroom");
        house.addDevice(sd1,"Bathroom");
        house.addDevice(sb1,"Bathroom");
        house.addDevice(sb2,"Bathroom");
        house.addDevice(sb3,"Bathroom");
        house.addDevice(sc1,"Bathroom");
        house.addDevice(sc2,"Bathroom");
        house.addDevice(ss1,"Bathroom");
        house.addDevice(ss2,"Bathroom");

        Bill b1 = new Bill(d1, d2, 180.42, 39.06, 1, ep1);
        assertEquals(b1, house.generateBill(d1, d2));
    }

    @Test
    void testTotalDevices() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        House house = new House(1, new EDP());
        try {
            house.addDevice(new SmartSpeaker(1), "Bathroom");
        }
        catch (ObjectUndefinedException e) {}
        assertEquals(0, house.totalDevices());
        house.addLocation("Bathroom");
        house.addDevice(new SmartSpeaker(1),"Bathroom");
        house.addDevice(new SmartSpeaker(2),"Bathroom");
        house.addDevice(new SmartSpeaker(3),"Bathroom");
        house.addLocation("Bedroom");
        try {
            house.addDevice(new SmartSpeaker(1), "Bedroom");
        }
        catch (ObjectAlreadyCreatedException e) {}
        house.addDevice(new SmartSpeaker(4), "Bedroom");
        assertEquals(4, house.totalDevices());
    }

    @Test
    void testClone() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(1,false);
        SmartBulb sb1 = new SmartBulb(2, true, Tone.NEUTRAL, 5, 1);
        SmartBulb sb2 = new SmartBulb(3, true, Tone.WARM, 5, 1);
        SmartBulb sb3 = new SmartBulb(4, true, Tone.COLD, 5, 1);
        SmartCamera sc1 = new SmartCamera(5);
        SmartCamera sc2 = new SmartCamera(6, true, 1920, 1080, 25);
        SmartSpeaker ss1 = new SmartSpeaker(7);
        SmartSpeaker ss2 = new SmartSpeaker(8, true,88, "TSF", "Bose");

        House house1 = new House(1, new EDP());
        house1.addLocation("Bathroom");
        house1.addDevice(sd1,"Bathroom");
        house1.addDevice(sb1,"Bathroom");
        house1.addDevice(sb2,"Bathroom");
        house1.addDevice(sb3,"Bathroom");
        house1.addDevice(sc1,"Bathroom");
        house1.addDevice(sc2,"Bathroom");
        house1.addDevice(ss1,"Bathroom");
        house1.addDevice(ss2,"Bathroom");

        House house2 = house1.clone();

        assertNotSame(house1, house2);
        assertTrue(house1.equals(house2));

        assertSame(house1.getProvider(), house2.getProvider());
        assertTrue(house1.getProvider().equals(house2.getProvider()));

        assertNotSame(house1.getLocations(), house2.getLocations());
        assertTrue(house1.getLocations().equals(house2.getLocations()));

        assertNotSame(house1.getDevices(), house2.getDevices());
        assertTrue(house1.getDevices().equals(house2.getDevices()));

        for (String location : house1.getLocations().keySet())
        {
            Set<Integer> ids1 = house1.getLocations().get(location);
            Set<Integer> ids2 = house2.getLocations().get(location);

            assertNotSame(ids1, ids2);
            assertTrue(ids1.equals(ids2));
        }

        for (Integer id : house1.getDevices().keySet())
        {
            SmartDevice sd1_house1 = house1.getDevice(id).clone();
            SmartDevice sd2_house2 = house2.getDevice(id).clone();
            SmartDevice sd3_house1 = house1.getDevices().get(id).clone();
            SmartDevice sd4_house2 = house2.getDevices().get(id).clone();

            assertNotSame(sd1_house1, sd2_house2);
            assertTrue(sd1_house1.equals(sd2_house2));

            assertNotSame(sd1_house1, sd3_house1);
            assertTrue(sd1_house1.equals(sd3_house1));

            assertNotSame(sd1_house1, sd4_house2);
            assertTrue(sd1_house1.equals(sd4_house2));

            assertNotSame(sd3_house1, sd4_house2);
            assertTrue(sd3_house1.equals(sd4_house2));
        }
    }

    @Test
    void testEquals() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        SmartSpeaker sd1 = new SmartSpeaker(1,false);
        SmartBulb sb1 = new SmartBulb(2, true, Tone.NEUTRAL, 5, 1);
        SmartBulb sb2 = new SmartBulb(3, true, Tone.WARM, 5, 1);
        SmartBulb sb3 = new SmartBulb(4, true, Tone.COLD, 5, 1);
        SmartCamera sc1 = new SmartCamera(5);
        SmartCamera sc2 = new SmartCamera(6, true, 1920, 1080, 25);
        SmartSpeaker ss1 = new SmartSpeaker(7);
        SmartSpeaker ss2 = new SmartSpeaker(8, true,88, "TSF", "Bose");

        House house1 = new House(1, new EDP());
        house1.addLocation("Bathroom");
        house1.addDevice(sd1,"Bathroom");
        house1.addDevice(sb1,"Bathroom");
        house1.addDevice(sb2,"Bathroom");
        house1.addDevice(sb3,"Bathroom");
        house1.addDevice(sc1,"Bathroom");
        house1.addDevice(sc2,"Bathroom");
        house1.addDevice(ss1,"Bathroom");
        house1.addDevice(ss2,"Bathroom");

        House house2 = new House(1, new EDP());
        house2.addLocation("Bathroom");
        house2.addDevice(sd1,"Bathroom");
        house2.addDevice(sb1,"Bathroom");
        house2.addDevice(sb2,"Bathroom");
        house2.addDevice(sb3,"Bathroom");
        assertFalse(house1.equals(house2));

        house2.addDevice(sc1,"Bathroom");
        house2.addDevice(sc2,"Bathroom");
        house2.addDevice(ss1,"Bathroom");
        house2.addDevice(ss2,"Bathroom");
        assertTrue(house1.equals(house2));

        house2.addLocation("Bedroom");
        assertFalse(house1.equals(house2));

        House house3 = new House(3, new EDP());
        house3.addLocation("Bathroom");
        house3.addDevice(sd1,"Bathroom");
        house3.addDevice(sb1,"Bathroom");
        house3.addDevice(sb2,"Bathroom");
        house3.addDevice(sb3,"Bathroom");
        house3.addDevice(sc1,"Bathroom");
        house3.addDevice(sc2,"Bathroom");
        house3.addDevice(ss1,"Bathroom");
        house3.addDevice(ss2,"Bathroom");
        assertFalse(house1.equals(house3));

        House house4 = new House(1, new EDP());
        house4.addLocation("Bathroom");
        house4.addDevice(sd1,"Bathroom");
        house4.addDevice(sb1,"Bathroom");
        house4.addDevice(sb2,"Bathroom");
        house4.addDevice(sb3,"Bathroom");
        house4.addDevice(sc1,"Bathroom");
        house4.addDevice(sc2,"Bathroom");
        house4.addDevice(ss1,"Bathroom");
        house4.addDevice(ss2,"Bathroom");
        assertTrue(house1.equals(house4));
        house4.setOwnerName("OwnerTest");
        assertFalse(house1.equals(house4));

        House house5 = new House(1, new EDP());
        house5.addLocation("Bathroom");
        house5.addDevice(sd1,"Bathroom");
        house5.addDevice(sb1,"Bathroom");
        house5.addDevice(sb2,"Bathroom");
        house5.addDevice(sb3,"Bathroom");
        house5.addDevice(sc1,"Bathroom");
        house5.addDevice(sc2,"Bathroom");
        house5.addDevice(ss1,"Bathroom");
        house5.addDevice(ss2,"Bathroom");
        assertTrue(house1.equals(house5));
        house5.setOwnerNIF("123456789");
        assertFalse(house1.equals(house5));
    }
}