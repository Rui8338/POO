import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class SimulationTest {

    @Test
    void testConstructor()
    {
        Simulation s = new Simulation(LocalDate.now());
        assertNotNull(s);
    }

    @Test
    void testHasHouse() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        assertFalse(s.hasHouse(1));
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Ricardinni Sousa", "123456789");
        assertTrue(s.hasHouse(1));
    }

    @Test
    void testAddHouse() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        s.addProvider("Iberdrola");
        s.addHouse(1, "Iberdrola", "Diego Capel", "123456789");
        House h = new House(1, new Iberdrola(), "Diego Capel", "123456789");
        assertTrue(h.equals(s.getHouse(1)));
    }

    @Test
    void testAddProvider() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        assertEquals(0, s.getProviders().size());
        assertNull(s.getProviders().get("MEO Energia"));
        s.addProvider("MEO Energia");
        assertNotNull(s.getProviders().get("MEO Energia"));
    }

    @Test
    void testAdvanceToDate() throws PreviousDateException
    {
        Simulation s = new Simulation(LocalDate.now());
        assertEquals(LocalDate.now(), s.getCurrentSimDate());
        s.advanceToDate(LocalDate.now().plusMonths(1));
        assertEquals(LocalDate.now().plusMonths(1), s.getCurrentSimDate());
    }

    @Test
    void testOnAllDevicesInLocation() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartBulb sb1 = new SmartBulb(1, false);
        SmartCamera sc1 = new SmartCamera(2, false);
        SmartSpeaker ss1 = new SmartSpeaker(3, false);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Living Room", 1);
        s.addDeviceToHouse(sb1, "Living Room", 1);
        s.addDeviceToHouse(sc1, "Living Room", 1);
        s.addDeviceToHouse(ss1, "Living Room", 1);

        assertFalse(s.getDevice(1, 1).getOn());
        assertFalse(s.getDevice(1, 2).getOn());
        assertFalse(s.getDevice(1, 3).getOn());

        s.onAllDevicesInLocation(1, "Living Room", true);
        assertTrue(s.getDevice(1, 1).getOn());
        assertTrue(s.getDevice(1, 2).getOn());
        assertTrue(s.getDevice(1, 3).getOn());
    }

    @Test
    void testOnDevice() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartBulb sb1 = new SmartBulb(1, false);

        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(sb1, "Kitchen", 1);
        assertFalse(s.getDevice(1, 1).getOn());

        s.onDevice(1, 1, true);
        assertTrue(s.getDevice(1, 1).getOn());
    }

    @Test
    void testAddDeviceToHouse() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartBulb sb1 = new SmartBulb(1, false);

        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(sb1, "Kitchen", 1);

        assertTrue(sb1.equals(s.getDevice(1, 1)));
    }

    @Test
    void testAddLocationToHouse() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");

        assertFalse(s.getHouse(1).getLocations().containsKey("Kitchen"));

        s.addLocationToHouse("Kitchen", 1);
        assertTrue(s.getHouse(1).getLocations().containsKey("Kitchen"));
    }

    @Test
    void testChangeTax() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        s.addProvider("EDP Comercial");
        s.addProvider("Iberdrola");
        s.addProvider("Galp Energia");
        s.addProvider("Endesa");
        s.addProvider("MEO Energia");
        assertEquals(0.64, s.getProviders().get("EDP Comercial").getTax());
        assertEquals(0.64, s.getProviders().get("Iberdrola").getTax());
        assertEquals(0.64, s.getProviders().get("Galp Energia").getTax());
        assertEquals(0.64, s.getProviders().get("Endesa").getTax());
        assertEquals(0.64, s.getProviders().get("MEO Energia").getTax());

        s.changeTax("EDP Comercial", 1.42);
        s.changeTax("Iberdrola", 1.56);
        s.changeTax("Galp Energia", 1.77);
        s.changeTax("Endesa", 1.99);
        s.changeTax("MEO Energia", 2.05);
        assertEquals(1.42, s.getProviders().get("EDP Comercial").getTax());
        assertEquals(1.56, s.getProviders().get("Iberdrola").getTax());
        assertEquals(1.77, s.getProviders().get("Galp Energia").getTax());
        assertEquals(1.99, s.getProviders().get("Endesa").getTax());
        assertEquals(2.05, s.getProviders().get("MEO Energia").getTax());
    }

    @Test
    void testChangeVolume() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartSpeaker ss1 = new SmartSpeaker(1, true);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(ss1, "Kitchen", 1);

        assertEquals(0, ((SmartSpeaker) s.getDevice(1,1)).getVolume());

        s.changeVolume(1, 1, 88);
        assertEquals(88, ((SmartSpeaker) s.getDevice(1,1)).getVolume());
    }

    @Test
    void testChangeRadio() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartSpeaker ss1 = new SmartSpeaker(1, true);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(ss1, "Kitchen", 1);

        assertEquals("", ((SmartSpeaker) s.getDevice(1,1)).getRadio());

        s.changeRadio(1, 1, "TSF");
        assertEquals("TSF", ((SmartSpeaker) s.getDevice(1,1)).getRadio());
    }

    @Test
    void testChangeDimension() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartBulb sb1 = new SmartBulb(1, true);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(sb1, "Kitchen", 1);

        assertEquals(5, ((SmartBulb) s.getDevice(1,1)).getDimension());

        s.changeDimension(1, 1, 12);
        assertEquals(12, ((SmartBulb) s.getDevice(1,1)).getDimension());
    }

    @Test
    void testChangeTone() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartBulb sb1 = new SmartBulb(1, true);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(sb1, "Kitchen", 1);

        assertEquals(Tone.NEUTRAL, ((SmartBulb) s.getDevice(1,1)).getTone());

        s.changeTone(1, 1, Tone.WARM);
        assertEquals(Tone.WARM, ((SmartBulb) s.getDevice(1,1)).getTone());
    }

    @Test
    void testChangeDailyIntake()
    {
    }

    @Test
    void testChangeFilesize() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartCamera sc1 = new SmartCamera(1, true);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(sc1, "Kitchen", 1);

        assertEquals(10, ((SmartCamera) s.getDevice(1,1)).getSaveFileSize());

        s.changeFilesize(1, 1, 25);
        assertEquals(25, ((SmartCamera) s.getDevice(1,1)).getSaveFileSize());
    }

    @Test
    void testChangeresolution_w() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartCamera sc1 = new SmartCamera(1, true);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(sc1, "Kitchen", 1);

        assertEquals(1280, ((SmartCamera) s.getDevice(1,1)).getResolution_width());

        s.changeresolution_w(1, 1, 1920);
        assertEquals(1920, ((SmartCamera) s.getDevice(1,1)).getResolution_width());
    }
    @Test
    void testChangeresolution_h() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        SmartCamera sc1 = new SmartCamera(1, true);
        s.addProvider("EDP Comercial");
        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addLocationToHouse("Kitchen", 1);
        s.addDeviceToHouse(sc1, "Kitchen", 1);

        assertEquals(720, ((SmartCamera) s.getDevice(1,1)).getResolution_height());

        s.changeresolution_h(1, 1, 1080);
        assertEquals(1080, ((SmartCamera) s.getDevice(1,1)).getResolution_height());
    }

    @Test
    void testDevices_number() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        s.addProvider("EDP Comercial");
        s.addProvider("Iberdrola");
        s.addProvider("Galp Energia");

        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        s.addHouse(2, "Iberdrola", "Owner2", "987654321");
        s.addHouse(3, "Galp Energia", "Owner3", "567891234");

        s.addLocationToHouse("Kitchen", 1);
        s.addLocationToHouse("Bathroom", 2);
        s.addLocationToHouse("Bedroom", 3);

        SmartBulb sb1 = new SmartBulb(1, true);
        SmartCamera sc1 = new SmartCamera(2, true);
        SmartSpeaker ss1 = new SmartSpeaker(3, true);

        assertEquals(0, s.devices_number());

        s.addDeviceToHouse(sb1, "Kitchen", 1);
        s.addDeviceToHouse(sc1, "Kitchen", 1);
        s.addDeviceToHouse(ss1, "Kitchen", 1);
        assertEquals(3, s.devices_number());

        s.addDeviceToHouse(sb1, "Bathroom", 2);
        s.addDeviceToHouse(sc1, "Bathroom", 2);
        s.addDeviceToHouse(ss1, "Bathroom", 2);
        assertEquals(6, s.devices_number());

        s.addDeviceToHouse(sb1, "Bedroom", 3);
        s.addDeviceToHouse(sc1, "Bedroom", 3);
        s.addDeviceToHouse(ss1, "Bedroom", 3);
        assertEquals(9, s.devices_number());
    }

    @Test
    void testHouses_number() throws ObjectAlreadyCreatedException, ObjectUndefinedException
    {
        Simulation s = new Simulation(LocalDate.now());
        s.addProvider("EDP Comercial");
        s.addProvider("Iberdrola");
        s.addProvider("Galp Energia");

        assertEquals(0, s.houses_number());

        s.addHouse(1, "EDP Comercial", "Owner1", "123456789");
        assertEquals(1, s.houses_number());

        s.addHouse(2, "Iberdrola", "Owner2", "987654321");
        assertEquals(2, s.houses_number());

        s.addHouse(3, "Galp Energia", "Owner3", "567891234");
        assertEquals(3, s.houses_number());
    }
}
