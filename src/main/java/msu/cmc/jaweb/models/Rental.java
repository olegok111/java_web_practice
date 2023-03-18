package msu.cmc.jaweb.models;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import org.postgresql.util.PGmoney;

@Entity
@Table(name = "rental")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Rental implements CommonEntity<Long> {

    private enum RentalMethod {
        RENT,
        PURCHASE
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "rental_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id")
    @ToString.Exclude
    @NonNull
    private Film film;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @NonNull
    private Client client;

    @Column(nullable = false, name = "rent_or_purchase")
    @NonNull
    private RentalMethod rent_or_purchase;

    @Column(nullable = false, name = "start_time")
    @NonNull
    private Timestamp start_time;

    @Column(name = "end_time")
    private Timestamp end_time;

    @Column(nullable = false, name = "price")
    @NonNull
    private PGmoney price;
}
