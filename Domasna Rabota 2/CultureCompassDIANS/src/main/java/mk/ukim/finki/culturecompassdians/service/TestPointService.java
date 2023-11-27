package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.TestPoint;

import java.util.List;
import java.util.Optional;

public interface TestPointService {

    List<TestPoint> findAllPoints();

    Optional<TestPoint> findPointById(Long id);

    List<TestPoint> findByCategory(String text);

    TestPoint savePoint(TestPoint point);

    void deletePointById(Long id);

}
