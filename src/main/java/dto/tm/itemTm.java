package dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class itemTm {
    private String code;
    private String description;
    private Double unitPrice;
    private int qtyOnHand;
    private Button btn;  // Added Button field

    // Constructor without Button
    public itemTm(String code, String description, Double unitPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    // Override toString method
    @Override
    public String toString() {
        return "itemTm{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", btn=" + btn +  // Added btn field to toString
                '}';
    }
}
