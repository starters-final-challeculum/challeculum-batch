package companion.challeculum.batch.repository;

import companion.challeculum.batch.entity.Ground;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.repository
 */
public interface GroundRepository extends JpaRepository<Ground, Long> {
    List<Ground> findByStartAtEqualsAndStatusEquals(LocalDate date, String status);

    List<Ground> findByEndAtEqualsAndStatusEquals(LocalDate date, String status);

    // 테스트용
    List<Ground> findByStatusEquals(String status);
}
