package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.Node;

import java.util.List;
import java.util.Optional;

public interface NodeService {

    List<Node> findAllNodes();

    Node findNodeById(Long id);

    List<Node> findByNameContaining(String text);
    List<Node> findByCategory(String text);
    List<String> findAllCategories();

    Node saveNode(Node point);

    void saveAll(List<Node> nodes);

    void  deleteNodeById(Long id);
//    Node editNode(Node node);
//    Optional<Node> save(Long id, String name, Double latitude, Double longitude, String category);

}
