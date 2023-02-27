package companion.challeculum.batch.repository;

import companion.challeculum.batch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}