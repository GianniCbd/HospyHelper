package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.booking.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accommodation")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeAccommodation;

    @ManyToOne
    @JsonIgnore
    private Booking booking;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public Accommodation(String typeAccommodation, Booking booking,User user) {
        this.typeAccommodation = typeAccommodation;
        this.booking = booking;
        this.user = user;
    }
}
