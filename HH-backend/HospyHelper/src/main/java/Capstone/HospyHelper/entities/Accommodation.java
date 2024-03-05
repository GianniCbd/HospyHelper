package Capstone.HospyHelper.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accommodation")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;
    private String name;
    private String address;
    private String city;

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST})
    private Set<Room> rooms;

    public Accommodation(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
}
