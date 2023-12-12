package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.Node;

import java.util.List;
import java.util.Optional;

public interface NodeService {

    List<Node> findAllNodes();

    Optional<Node> findNodeById(Long id);

    List<Node> findByCategoryOrName(String text);

    Node saveNode(Node point);

    void saveAll(List<Node> nodes);

    void deleteNodeById(Long id);
}
