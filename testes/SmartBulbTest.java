import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

public class SmartBulbTest {

    @Test
    void testConstructor()
    {
        SmartBulb sb1 = new SmartBulb(1);
        assertNotNull(sb1);
        SmartBulb sb2 = new SmartBulb(2, true);
        assertNotNull(sb2);
        SmartBulb sb3 = new SmartBulb(3, true, Tone.WARM, 1, 1);
        assertNotNull(sb3);
        SmartBulb sb4 = new SmartBulb(sb1);
        assertNotNull(sb4);
    }

    @Test
    void testSetTone()
    {
        SmartBulb sb1 = new SmartBulb(1);
        assertEquals(Tone.NEUTRAL, sb1.getTone());
        sb1.setTone(Tone.WARM);
        assertEquals(Tone.WARM, sb1.getTone());
    }

    @Test
    void testSetDimension()
    {
        SmartBulb sb1 = new SmartBulb(1);
        assertEquals(5, sb1.getDimension());
        sb1.setDimension(10);
        assertEquals(10, sb1.getDimension());
    }

    @Test
    void testSetOn()
    {
        LocalDate timeOn;
        SmartBulb sd1 = new SmartBulb(1);
        assertFalse(sd1.getOn());

        sd1.setOn(true);
        assertTrue(sd1.getOn());
    }
    @Test
    void testClone()
    {
        SmartBulb sb1 = new SmartBulb(1, true, Tone.WARM, 3, 12);
        SmartDevice sb2 = sb1.clone();
        assertNotSame(sb1, sb2);
        assertTrue(sb1.equals(sb2));
    }

    @Test
    void testEquals()
    {
        SmartBulb sb1 = new SmartBulb(1);
        SmartBulb sb2 = new SmartBulb(1, false);
        SmartBulb sb3 = new SmartBulb(sb2);
        SmartBulb sb4 = new SmartBulb(2, false, Tone.NEUTRAL, 5, 0.5);
        SmartBulb sb5 = new SmartBulb(1, true, Tone.NEUTRAL, 5, 0.5);
        SmartBulb sb6 = new SmartBulb(1, false, Tone.WARM, 5, 0.5);
        SmartBulb sb7 = new SmartBulb(1, false, Tone.NEUTRAL, 6, 0.5);
        SmartBulb sb8 = new SmartBulb(1, false, Tone.NEUTRAL, 5, 0.6);
        assertTrue(sb1.equals(sb1));
        assertTrue(sb1.equals(sb2));
        assertTrue(sb2.equals(sb3));
        assertFalse(sb2.equals(sb4));
        assertFalse(sb2.equals(sb5));
        assertFalse(sb2.equals(sb6));
        assertFalse(sb2.equals(sb7));
        assertFalse(sb2.equals(sb8));
    }
}
