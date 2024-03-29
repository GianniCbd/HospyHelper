package Capstone.HospyHelper.room;


import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.booking.Booking;
import Capstone.HospyHelper.roomType.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;
    @Column(name = "numberRoom", unique = true, nullable = false)
    private int number;
    private double price;
    private int capacity;
    @Column(name = "owner_id")
    private UUID ownerId;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<Booking> bookings;

    @ManyToOne
    private User user;
    public Room(int number, double price, int capacity, RoomType roomType,User user) {
        this.number = number;
        this.price = price;
        this.capacity = capacity;
        this.roomType = roomType;
        this.ownerId = user.getId();
    }
}
