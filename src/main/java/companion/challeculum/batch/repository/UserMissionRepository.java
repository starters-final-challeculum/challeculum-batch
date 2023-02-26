package companion.challeculum.batch.repository;

import companion.challeculum.batch.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.repository
 */
@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
}
