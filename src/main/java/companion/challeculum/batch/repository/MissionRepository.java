package companion.challeculum.batch.repository;

import companion.challeculum.batch.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByMissionAtIs(LocalDate date);
}