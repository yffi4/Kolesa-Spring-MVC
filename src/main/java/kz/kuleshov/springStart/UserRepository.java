package kz.kuleshov.springStart;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findAllByEmail(String email);
}
