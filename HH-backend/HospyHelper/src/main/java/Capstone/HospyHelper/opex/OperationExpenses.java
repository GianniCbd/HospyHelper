package Capstone.HospyHelper.opex;

import Capstone.HospyHelper.accommodation.Accommodation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "opex")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OperationExpenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int waterBill;
    private int gasBill;
    private int powerBill;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnore
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public OperationExpenses(int waterBill, int gasBill, int powerBill, Accommodation accommodation) {
        this.waterBill = waterBill;
        this.gasBill = gasBill;
        this.powerBill = powerBill;
        this.accommodation = accommodation;
    }
}
