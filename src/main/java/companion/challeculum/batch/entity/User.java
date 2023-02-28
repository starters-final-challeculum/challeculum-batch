package companion.challeculum.batch.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "point")
    private int point;

    @Column(name = "mission_score")
    private int missionScore;

    @Column(name = "role")
    private String role;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserGround> userGroundList;
}

