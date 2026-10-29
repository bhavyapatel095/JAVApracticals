import app.model.Item;
import app.service.ItemService;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {

    @Test
    void saveAndLoadPreservesData() throws Exception {
        Path file = Files.createTempFile("items", ".dat");

        try {
            ItemService original = new ItemService();
            original.add("Book");
            original.add("Pen");

            original.save(file);

            ItemService restored = new ItemService();
            restored.load(file);

            assertEquals(original.count(), restored.count());
            assertEquals("Book", restored.all().get(0).getName());
            assertEquals("Pen", restored.all().get(1).getName());
        } finally {
            Files.deleteIfExists(file);
        }
    }

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
            () -> service.add(" ")
        );
    }
}