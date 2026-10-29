import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {

    @Test
    void addIncreasesCount() {
        ItemService service = new ItemService();

        service.add("Book");

        assertEquals(1, service.count());
    }

    @Test
    void blankNameThrowsException() {
        ItemService service = new ItemService();

        assertThrows(
            IllegalArgumentException.class,
            () -> service.add("")
        );
    }

    @Test
    void invalidActionThrowsException() {
        ItemService service = new ItemService();

        assertThrows(
            IllegalArgumentException.class,
            () -> service.invalidAction()
        );
    }
}