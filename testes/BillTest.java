import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class BillTest {

    @Test
    void testConstructor()
    {
        Bill b1 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(1), 45.7, 20.5, 1, new EDP());
        assertNotNull(b1);
        Bill b2 = new Bill(b1);
        assertNotNull(b2);
    }

    @Test
    void testClone()
    {
        Bill b1 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(1), 45.7, 20.5, 1, new EDP());
        Bill b2 = b1.clone();
        assertNotSame(b1, b2);
        assertTrue(b1.equals(b2));
    }

    @Test
    void testEquals()
    {
        Bill b1 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(1), 45.7, 20.5, 1, new EDP());
        Bill b2 = new Bill(b1);
        Bill b3 = new Bill(LocalDate.now().plusDays(1), LocalDate.now().plusMonths(1), 45.7, 20.5, 1, new EDP());
        Bill b4 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(2), 45.7, 20.5, 1, new EDP());
        Bill b5 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(1), 55.7, 20.5, 1, new EDP());
        Bill b6 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(1), 45.7, 30.5, 1, new EDP());
        Bill b7 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(1), 45.7, 20.5, 2, new EDP());
        Bill b8 = new Bill(LocalDate.now(), LocalDate.now().plusMonths(1), 45.7, 20.5, 1, new Iberdrola());

        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b2));
        assertFalse(b1.equals(b3));
        assertFalse(b1.equals(b4));
        assertFalse(b1.equals(b5));
        assertFalse(b1.equals(b6));
        assertFalse(b1.equals(b7));
        assertFalse(b1.equals(b8));
    }
}
