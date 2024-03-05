package Capstone.HospyHelper.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roomType")
@Getter
@Setter
@NoArgsConstructor
public class RoomType {
    @Id
    @GeneratedValue
    private UUID id;
    private String type;
    @OneToMany(mappedBy = "roomType")
    @ToString.Exclude
    private Set<Room> rooms;

    public RoomType(String type) {
        this.type = type;
    }
}
