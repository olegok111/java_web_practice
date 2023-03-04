package msu.cmc.jaweb.models;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "film")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Film implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "film_id")
    private Long id;

    @Column(nullable = false, name = "title")
    @NonNull
    private String title;

    @Column(nullable = false, name = "company")
    @NonNull
    private String company;

    @Column(nullable = false, name = "director")
    @NonNull
    private String director;

    @Column(nullable = false, name = "release_year")
    @NonNull
    private Long release_year;

    @Column(name = "purchase_price")
    private Long purchase_price;

    @Column(name = "rent_price")
    private Long rent_price;
}
