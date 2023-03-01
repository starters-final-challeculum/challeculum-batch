package companion.challeculum.batch.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Getter
@Setter
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ground_id")
    private Ground ground;

    @Column(name = "mission_at")
    private LocalDate missionAt;
}
