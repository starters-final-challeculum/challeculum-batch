package companion.challeculum.batch.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Data
@Table(name = "user_ground")
public class UserGround {

    @EmbeddedId
    private UserGroundId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groundId")
    @JoinColumn(name = "ground_id")
    private Ground ground;

    @Column(name = "is_success")
    private boolean isSuccess;
}
