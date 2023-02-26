package companion.challeculum.batch.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Data
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ground_id")
    private Ground ground;

    @Column(name = "assignment")
    private String assignment;

    @Column(name = "mission_at")
    private LocalDate missionAt;

    // constructors, getters, and setters
}
