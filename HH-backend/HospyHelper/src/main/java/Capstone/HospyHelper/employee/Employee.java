package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;
import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private double salary;
    @Enumerated(EnumType.STRING)
    RoleEmployee roleEmployee;
    @Column(name = "owner_id")
    private UUID ownerId;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnore
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public Employee(String name, String surname, int age, String email, double salary, RoleEmployee roleEmployee,Accommodation accommodation,User user) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.salary = salary;
        this.roleEmployee = roleEmployee;
        this.accommodation = accommodation;
        this.ownerId = user.getId();

    }
}
