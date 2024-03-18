package Capstone.HospyHelper.booking;

import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.room.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

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

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private Set<Accommodation> accommodations;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_room")
    private Room room;


    public Booking(String fullName, String email, String phone, LocalDate checkIn, LocalDate checkOut, Room room) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;

        this.numberOfGuests = room.getCapacity();
    }
}

