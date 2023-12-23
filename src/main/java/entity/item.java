package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class item {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}