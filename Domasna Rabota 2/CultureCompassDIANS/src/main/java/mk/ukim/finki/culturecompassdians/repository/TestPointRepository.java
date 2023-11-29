package mk.ukim.finki.culturecompassdians.repository;

import mk.ukim.finki.culturecompassdians.model.TestPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TestPointRepository extends JpaRepository<TestPoint, Long> {

    List<TestPoint> findTestPointByCategory(String text);

}
