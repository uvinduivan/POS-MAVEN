package dao.custom;

import dto.CustomerDto;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteItem(ItemDto id) throws SQLException, ClassNotFoundException;

    static List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    CustomerDto searchItem(String id);
}
