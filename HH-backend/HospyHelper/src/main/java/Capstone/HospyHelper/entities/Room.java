package Capstone.HospyHelper.entities;

import Capstone.HospyHelper.Enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;
    private int number;
    private double price;
    private int maxCustomer;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;


    @OneToMany(mappedBy = "room")
    private Set<Booking> bookings;



    public Room(int number, double price, int maxCustomer, RoomType roomType) {
        this.number = number;
        this.price = price;
        this.maxCustomer = maxCustomer;
        this.roomType = roomType;
    }

    public void addBooking(Booking booking) {
        if (bookings == null) {
            bookings = new HashSet<>();
        }
        bookings.add(booking);
    }
}
