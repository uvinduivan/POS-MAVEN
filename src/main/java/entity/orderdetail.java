package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class orderdetail {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}