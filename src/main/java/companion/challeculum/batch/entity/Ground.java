package companion.challeculum.batch.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Getter
@Setter
@Table(name = "ground")
public class Ground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ground_id")
    private Long id;

    @Column(name = "min_capacity")
    private int minCapacity;

    @Column(name = "deposit")
    private int deposit;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @Column(name = "status")
    private String status;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ground_id")
    private List<UserGround> userGroundList;

}


