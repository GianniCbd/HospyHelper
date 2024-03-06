package Capstone.HospyHelper.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
        private String checkIn;
        private String checkOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_room")
    private Room room;

    public Booking(String fullName, String email, String phone, String checkIn, String checkOut,Room room,User user) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
        this.user = user;

    }

}

