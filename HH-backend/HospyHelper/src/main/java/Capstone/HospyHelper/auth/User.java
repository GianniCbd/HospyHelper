package Capstone.HospyHelper.auth;

import Capstone.HospyHelper.Enums.Role;
import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.booking.Booking;
import Capstone.HospyHelper.employee.Employee;
import Capstone.HospyHelper.post.Post;
import Capstone.HospyHelper.review.Review;
import Capstone.HospyHelper.room.Room;
import Capstone.HospyHelper.roomType.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password","confirmPassword", "credentialsNonExpired", "accountNonExpired", "authorities", "username", "accountNonLocked", "enabled"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String confirmPassword;
    @Enumerated (EnumType.STRING)
    private Set<Role> roles=new HashSet<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Post> posts = new HashSet<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Accommodation> accommodations = new HashSet<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private Set<Room> rooms;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private Set<RoomType> roomTypes;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private Set<Employee> employee;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings;
    public User(String name, String surname, String email, String password, String confirmPassword) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;

        this.roles.add(Role.USER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(el->new SimpleGrantedAuthority(el.name())).toList();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}