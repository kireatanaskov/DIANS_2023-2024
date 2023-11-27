package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.TestPoint;
import mk.ukim.finki.culturecompassdians.repository.TestPointRepository;
import mk.ukim.finki.culturecompassdians.service.TestPointService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestPointServiceImpl implements TestPointService {

    private final TestPointRepository pointRepository;

    public TestPointServiceImpl(TestPointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public List<TestPoint> findAllPoints() {
        return pointRepository.findAll();
    }

    @Override
    public Optional<TestPoint> findPointById(Long id) {
        return pointRepository.findById(id);
    }

    @Override
    public List<TestPoint> findByCategory(String text) {
        return pointRepository.findTestPointByCategory(text);
    }

    @Override
    public TestPoint savePoint(TestPoint point) {
        return pointRepository.save(point);
    }

    @Override
    public void deletePointById(Long id) {
        pointRepository.deleteById(id);
    }
}
