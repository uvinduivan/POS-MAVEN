package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class ItemDto {
    private String code;
    private String description;
    private Double unitPrice;
    private int qtyOnHand;
}






