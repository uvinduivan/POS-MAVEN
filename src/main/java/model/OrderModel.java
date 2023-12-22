package model;


import dto.ItemDto;

import java.util.List;

public interface OrderModel {
    boolean saveItem(ItemDto dto);
    boolean updateItem(ItemDto dto);
    List<ItemDto> allItems();
}
