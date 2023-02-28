package companion.challeculum.batch.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Entity
@Getter
@Setter
@Table(name = "user_mission")
public class UserMission {

    @EmbeddedId
    private UserMissionId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("missionId")
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(name = "submit_at")
    private LocalDateTime submitAt;

    @Column(name = "is_accepted")
    private String isAccepted;
}
