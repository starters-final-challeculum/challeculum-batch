package companion.challeculum.batch.repository;

import companion.challeculum.batch.entity.UserGround;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.repository
 */
public interface UserGroundRepository extends JpaRepository<UserGround, Long> {
    List<UserGround> findAllByGroundId(Long groundId);
}
