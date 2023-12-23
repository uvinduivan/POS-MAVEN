package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class orders {
    private String id;
    private String date;
    private String customerId;
}