import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SmartCameraTest {

    @Test
    void testConstructor()
    {
        SmartCamera sc1 = new SmartCamera(1);
        assertNotNull(sc1);
        SmartCamera sc2 = new SmartCamera(2, true);
        assertNotNull(sc2);
        SmartCamera sc3 = new SmartCamera(3, true, 1920, 1080, 25);
        assertNotNull(sc3);
        SmartCamera sc4 = new SmartCamera(sc1);
        assertNotNull(sc4);
    }

    @Test
    void testSetResolution_width()
    {
        SmartCamera sc1 = new SmartCamera(1);
        assertEquals(1280, sc1.getResolution_width());
        sc1.setResolution_width(1920);
        assertEquals(1920, sc1.getResolution_width());

        SmartCamera sc2 = new SmartCamera(2, true);
        assertEquals(1280, sc2.getResolution_width());
        sc2.setResolution_width(1920);
        assertEquals(1920, sc2.getResolution_width());

        SmartCamera sc3 = new SmartCamera(3, true, 852, 480, 5);
        assertEquals(852, sc3.getResolution_width());

        SmartCamera sc4 = new SmartCamera(sc1);
        assertEquals(1920, sc4.getResolution_width());
        sc4.setResolution_width(480);
        assertEquals(480, sc4.getResolution_width());
    }

    @Test
    void testSetResolution_height()
    {
        SmartCamera sc1 = new SmartCamera(1);
        assertEquals(720, sc1.getResolution_height());
        sc1.setResolution_height(1080);
        assertEquals(1080, sc1.getResolution_height());

        SmartCamera sc2 = new SmartCamera(2, true);
        assertEquals(720, sc2.getResolution_height());
        sc2.setResolution_height(1080);
        assertEquals(1080, sc2.getResolution_height());

        SmartCamera sc3 = new SmartCamera(3, true, 852, 480, 5);
        assertEquals(480, sc3.getResolution_height());

        SmartCamera sc4 = new SmartCamera(sc1);
        assertEquals(1080, sc4.getResolution_height());
        sc4.setResolution_height(360);
        assertEquals(360, sc4.getResolution_height());
    }

    @Test
    void testSetSaveFileSize()
    {
        SmartCamera sc1 = new SmartCamera(1);
        assertEquals(10, sc1.getSaveFileSize());
        sc1.setSaveFileSize(15);
        assertEquals(15, sc1.getSaveFileSize());

        SmartCamera sc2 = new SmartCamera(2, true);
        assertEquals(10, sc2.getSaveFileSize());

        SmartCamera sc3 = new SmartCamera(3, true, 1920, 1080, 25);
        assertEquals(25, sc3.getSaveFileSize());

        SmartCamera sc4 = new SmartCamera(sc1);
        assertEquals(15, sc4.getSaveFileSize());
        sc4.setSaveFileSize(20);
        assertEquals(20, sc4.getSaveFileSize());
    }

    @Test
    void testSetOn()
    {
        LocalDate timeOn;
        SmartCamera sd1 = new SmartCamera(1);
        assertFalse(sd1.getOn());

        sd1.setOn(true);
        assertTrue(sd1.getOn());
    }

    @Test
    void testClone()
    {
        SmartCamera sc1 = new SmartCamera(1, true, 1920, 1080 , 25);
        SmartDevice sc2 = sc1.clone();
        assertNotSame(sc1, sc2);
        assertTrue(sc1.equals(sc2));
    }

    @Test
    void testEquals()
    {
        SmartCamera sc1 = new SmartCamera(1);
        SmartCamera sc2 = new SmartCamera(1, false);
        SmartCamera sc3 = new SmartCamera(1, false,1280, 720, 10);
        SmartCamera sc4 = new SmartCamera(sc3);
        SmartCamera sc5 = new SmartCamera(2, false, 1280, 720, 10);
        SmartCamera sc6 = new SmartCamera(1, true, 1280, 720, 10);
        SmartCamera sc7 = new SmartCamera(1, false, 1281, 720, 10);
        SmartCamera sc8 = new SmartCamera(1, false, 1280, 721, 10);
        SmartCamera sc9 = new SmartCamera(1, false, 1280, 720, 11);
        assertTrue(sc1.equals(sc1));
        assertTrue(sc1.equals(sc2));
        assertTrue(sc1.equals(sc3));
        assertTrue(sc3.equals(sc4));
        assertFalse(sc3.equals(sc5));
        assertFalse(sc3.equals(sc6));
        assertFalse(sc3.equals(sc7));
        assertFalse(sc3.equals(sc8));
        assertFalse(sc3.equals(sc9));
    }
}
