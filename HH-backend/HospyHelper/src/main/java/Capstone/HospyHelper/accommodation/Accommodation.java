package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.booking.Booking;
import Capstone.HospyHelper.employee.Employee;
import Capstone.HospyHelper.opex.OperationExpenses;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnoreProperties({"id","email","salary"})
    @OneToMany(mappedBy = "accommodation",cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "accommodation",cascade = CascadeType.ALL)
    private Set<OperationExpenses> operationExpenses = new HashSet<>();

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
