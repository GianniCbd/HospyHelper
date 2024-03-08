package Capstone.HospyHelper.entities;

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


    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private Set<Accommodation> accommodations;

    @OneToOne
    @JoinColumn(name = "id_room")
    @JsonIgnore
    private Room room;


    public Booking(String fullName, String email, String phone, LocalDate checkIn, LocalDate checkOut,Room room) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
    }


}

