package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class customer {
    @Id
    private String id;
    private String name;
    private String address;
    private double salary;
}
