package Capstone.HospyHelper.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
        private UUID id;
        private String fullName;
        private String email;
        private String phone;
        private String checkIn;
        private String checkOut;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

    public Booking(String fullName, String email, String phone, String checkIn, String checkOut, Room room) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
    }
}

