package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;
import Capstone.HospyHelper.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public Employee(String name, String surname, int age, String email, double salary, RoleEmployee roleEmployee,User user) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.salary = salary;
        this.roleEmployee = roleEmployee;
        this.user = user;
    }
}
