package msu.cmc.jaweb.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "film")
@Getter
@Setter
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

    @Column(nullable = false, name = "genre")
    @NonNull
    private String genre;

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
