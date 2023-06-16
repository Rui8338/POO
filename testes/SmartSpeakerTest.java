import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

public class SmartSpeakerTest {

    @Test
    void testConstructor()
    {
        SmartSpeaker ss1 = new SmartSpeaker(1);
        assertNotNull(ss1);
        SmartSpeaker ss2 = new SmartSpeaker(2, true);
        assertNotNull(ss2);
        SmartSpeaker ss3 = new SmartSpeaker(2, true,80, "Comercial", "Bose");
        assertNotNull(ss3);
        SmartSpeaker ss4 = new SmartSpeaker(ss1);
        assertNotNull(ss4);
    }

    @Test
    void testSetVolume()
    {
        SmartSpeaker ss1 = new SmartSpeaker(1);
        assertEquals(0, ss1.getVolume());
        ss1.setVolume(88);
        assertEquals(88, ss1.getVolume());
    }

    @Test
    void testSetRadio()
    {
        SmartSpeaker ss1 = new SmartSpeaker(1);
        assertEquals("", ss1.getRadio());
        ss1.setRadio("TSF");
        assertEquals("TSF", ss1.getRadio());
    }

    @Test
    void testSetBrand()
    {
        SmartSpeaker ss1 = new SmartSpeaker(1);
        assertEquals("", ss1.getBrand());
        ss1.setBrand("JBL");
        assertSame("JBL", ss1.getBrand());
    }
    @Test
    void testSetOn()
    {
        LocalDate timeOn;
        SmartSpeaker sd1 = new SmartSpeaker(1);
        assertFalse(sd1.getOn());

        sd1.setOn(true);
        assertTrue(sd1.getOn());
    }

    @Test
    void testClone()
    {
        SmartSpeaker ss1 = new SmartSpeaker(1, true,88, "TSF", "JBL");
        SmartDevice ss2 = ss1.clone();
        assertNotSame(ss1, ss2);
        assertTrue(ss1.equals(ss2));
    }

    @Test
    void testEquals()
    {
        SmartSpeaker ss1 = new SmartSpeaker(1);
        SmartSpeaker ss2 = new SmartSpeaker((1), false);
        SmartSpeaker ss3 = new SmartSpeaker(1, false,0, "", "");
        SmartSpeaker ss4 = new SmartSpeaker(ss3);
        SmartSpeaker ss5 = new SmartSpeaker(2, false,0, "", "");
        SmartSpeaker ss6 = new SmartSpeaker(1, true,0, "", "");
        SmartSpeaker ss7 = new SmartSpeaker(1, false,88, "", "");
        SmartSpeaker ss8 = new SmartSpeaker(1, false,0, "TSF", "");
        SmartSpeaker ss9 = new SmartSpeaker(1, false,0, "", "Marshall");
        assertTrue(ss1.equals(ss1));
        assertTrue(ss1.equals(ss2));
        assertTrue(ss1.equals(ss3));
        assertTrue(ss3.equals(ss4));
        assertFalse(ss3.equals(ss5));
        assertFalse(ss3.equals(ss6));
        assertFalse(ss3.equals(ss7));
        assertFalse(ss3.equals(ss8));
        assertFalse(ss3.equals(ss9));
    }
}
