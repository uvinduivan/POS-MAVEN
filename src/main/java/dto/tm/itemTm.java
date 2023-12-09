package dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString

public class itemTm{
    private String code;
    private String description;
    private Double unitPrice;
    private int qtyOnHand;

    public itemTm(String code, String description, Double unitPrice, int qtyOnHand, Button btn) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    @Override
    public String toString() {
        return "itemTm{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
