package Capstone.HospyHelper.entities;

import Capstone.HospyHelper.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@JsonIgnoreProperties({"password", "credentialsNonExpired", "accountNonExpired", "authorities", "username", "accountNonLocked", "enabled"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private UUID id;
    private String email;
    private String password;
    private String name;
    private String surname;
    @Enumerated (EnumType.STRING)
    private Set<Role> roles=new HashSet<>();


    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private Set<Employee> employees = new HashSet<>();


    public User(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;

        this.roles.add(Role.ADMIN);
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