import org.junit.jupiter.api.*;
import org.web.pahanaedu.dao.ItemDAO;
import org.web.pahanaedu.model.Item;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemDAOTest {

    static int testItemId;

    @Test
    @Order(1)
    public void testAddItem() {
        Item item = new Item();
        item.setName("JUnit Test Book");
        item.setPrice(750.0);
        item.setQuantity(3);

        boolean result = ItemDAO.addItem(item);
        Assertions.assertTrue(result, "Item should be added successfully");

        // You may need to retrieve the inserted ID for update/delete tests
        List<Item> allItems = ItemDAO.getAllItems();
        Item lastItem = allItems.get(allItems.size() - 1);
        testItemId = lastItem.getId();

        Assertions.assertEquals("JUnit Test Book", lastItem.getName());
    }

    @Test
    @Order(2)
    public void testGetAllItems() {
        List<Item> items = ItemDAO.getAllItems();
        Assertions.assertNotNull(items, "Item list should not be null");
        Assertions.assertFalse(items.isEmpty(), "Item list should not be empty");
    }

    @Test
    @Order(3)
    public void testUpdateItem() {
        Item item = new Item();
        item.setId(testItemId);
        item.setName("JUnit Updated Book");
        item.setPrice(800.0);
        item.setQuantity(5);

        boolean result = ItemDAO.updateItem(item);
        Assertions.assertTrue(result, "Item should be updated");

        List<Item> items = ItemDAO.getAllItems();
        boolean found = items.stream().anyMatch(i ->
                i.getId() == testItemId && i.getName().equals("JUnit Updated Book")
        );
        Assertions.assertTrue(found, "Updated item should exist in list");
    }

    @Test
    @Order(4)
    public void testDeleteItem() {
        boolean result = ItemDAO.deleteItem(testItemId);
        Assertions.assertTrue(result, "Item should be deleted");

        List<Item> items = ItemDAO.getAllItems();
        boolean exists = items.stream().anyMatch(i -> i.getId() == testItemId);
        Assertions.assertFalse(exists, "Deleted item should not be found");
    }
}
