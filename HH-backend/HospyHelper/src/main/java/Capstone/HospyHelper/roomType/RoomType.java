package Capstone.HospyHelper.roomType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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


    public RoomType(String typeName, String description,String image) {
        this.typeName = typeName;
        this.description = description;
        this.image = image;
    }
    public RoomType(String typeName) {
        this.typeName = typeName;
    }

    public static RoomType fromTypeName(String typeName) {
        return new RoomType(typeName);
    }
}
