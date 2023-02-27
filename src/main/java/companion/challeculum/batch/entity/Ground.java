package companion.challeculum.batch.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Data
@Table(name = "ground")
public class Ground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ground_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "create_user_id")
    private User createUser;

    @Column(name = "ground_title")
    private String title;

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

    @OneToMany
    @JoinColumn(name = "ground_id")
    private List<UserGround> userGroundList;

}


