package kz.kuleshov.springStart;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findAllById(Long id);
    List<Car> findAllByModelContainsIgnoreCase(String search);
    List<Car> findAllByOrderByPriceAsc();
    List<Car> findAllByOrderByPriceDesc();
    List<Car> findAllByOrderByYearAsc();
    List<Car> findAllByOrderByYearDesc();
}
