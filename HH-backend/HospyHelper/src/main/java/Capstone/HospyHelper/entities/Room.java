package Capstone.HospyHelper.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int maxCostumer;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<Booking> bookings;

    public Room(int number, double price, int maxCostumer, RoomType roomType) {
        this.number = number;
        this.price = price;
        this.maxCostumer = maxCostumer;
        this.roomType = roomType;

    }
    public void addBooking(Booking booking) {
        if (bookings == null) {
            bookings = new HashSet<>();
        }
        bookings.add(booking);
    }
}
