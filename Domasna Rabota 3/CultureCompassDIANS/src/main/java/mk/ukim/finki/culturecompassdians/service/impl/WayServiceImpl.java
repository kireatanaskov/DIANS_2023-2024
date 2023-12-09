package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.Way;
import mk.ukim.finki.culturecompassdians.repository.WayRepository;
import mk.ukim.finki.culturecompassdians.service.WayService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WayServiceImpl implements WayService {

    private final WayRepository wayRepository;

    public WayServiceImpl(WayRepository wayRepository) {
        this.wayRepository = wayRepository;
    }

    @Override
    public List<Way> findAllWays() {
        return wayRepository.findAll();
    }

    @Override
    public Optional<Way> findWayById(Long id) {
        return wayRepository.findById(id);
    }

    @Override
    public List<Way> findWaysByCategory(String text) {
        return wayRepository.findAllByCategory(text);
    }

    @Override
    public Way saveWay(Way way) {
        return wayRepository.save(way);
    }

    @Override
    public void deleteWayById(Long id) {
        wayRepository.deleteById(id);
    }
}
