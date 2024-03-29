package Capstone.HospyHelper.booking;

import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.room.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
        private long id;
        private String fullName;
        private String email;
        private String phone;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private int numberOfGuests;
        @Column(name = "owner_id")
        private UUID ownerId;
    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;


    public Booking(String fullName, String email, String phone, LocalDate checkIn, LocalDate checkOut, Room room,Accommodation accommodation,User user) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
        this.accommodation = accommodation;
        this.ownerId = user.getId();
        this.numberOfGuests = room.getCapacity();
    }
}

