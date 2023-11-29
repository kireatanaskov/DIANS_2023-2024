package mk.ukim.finki.culturecompassdians.repository;

import mk.ukim.finki.culturecompassdians.model.Way;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WayRepository extends JpaRepository<Way, Long> {

    List<Way> findAllByCategory(String category);

}
