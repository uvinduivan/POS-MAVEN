package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class customer {
    private String id;
    private String name;
    private String address;
    private double salary;
}
