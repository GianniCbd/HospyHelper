package Capstone.HospyHelper.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "room")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private UUID id;
    private int number;
    private double price;
    private int maxCustomer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_accommodation")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    private Set<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;

    public Room(int number, double price, int maxCustomer,Accommodation accommodation, RoomType roomType) {
        this.number = number;
        this.price = price;
        this.maxCustomer = maxCustomer;
        this.accommodation = accommodation;
        this.roomType = roomType;
    }
}
