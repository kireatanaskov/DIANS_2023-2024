package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.Way;

import java.util.List;
import java.util.Optional;

public interface WayService {

    List<Way> findAllWays();

    Optional<Way> findWayById(Long id);

    List<Way> findWaysByCategory(String text);

    Way saveWay(Way way);

    void deleteWayById(Long id);

}
