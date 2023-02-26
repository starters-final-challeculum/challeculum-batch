package companion.challeculum.batch.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.entity
 */
@Embeddable
@Data
public class UserGroundId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ground_id")
    private Long groundId;
}
