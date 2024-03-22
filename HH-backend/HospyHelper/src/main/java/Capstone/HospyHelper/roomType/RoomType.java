package Capstone.HospyHelper.roomType;

import Capstone.HospyHelper.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "roomType")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id", nullable = false)
    private long id;
    private String typeName;
    private String description;
    private String image;

    @Column(name = "owner_id")
    private UUID ownerId;

    @ManyToOne
    private User user;

    public RoomType(String typeName, String description,String image, User user) {
        this.typeName = typeName;
        this.description = description;
        this.image = image;
        this.ownerId = user.getId();
    }
    public RoomType(String typeName) {
        this.typeName = typeName;
    }

    public static RoomType fromTypeName(String typeName) {
        return new RoomType(typeName);
    }
}
