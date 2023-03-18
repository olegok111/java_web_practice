package msu.cmc.jaweb.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Client implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "client_id")
    private Long id;

    @Column(nullable = false, name = "full_name")
    @NonNull
    private String full_name;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @Column(name = "phone")
    private String phone;
}
