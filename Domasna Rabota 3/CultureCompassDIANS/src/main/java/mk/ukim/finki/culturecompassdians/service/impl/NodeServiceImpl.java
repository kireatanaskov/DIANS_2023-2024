package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.repository.NodeRepository;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;

    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public List<Node> findAllNodes() {
        return nodeRepository.findAll();
    }

    @Override
    public Optional<Node> findNodeById(Long id) {
        return nodeRepository.findById(id);
    }

    @Override
    public List<Node> findByNameContaining(String text) {
        return nodeRepository.findByName(text.toLowerCase());
    }

    @Override
    public List<Node> findByCategory(String text) {
        return nodeRepository.findByCategory(text);
    }

    @Override
    public List<String> findAllCategories() {
        return nodeRepository.findAllCategories();
    }

    @Override
    public Node saveNode(Node point) {
        return nodeRepository.save(point);
    }

    @Override
    public void saveAll(List<Node> nodes) {
        nodeRepository.saveAll(nodes);
    }

    @Override
    public void deleteNodeById(Long id) {
        nodeRepository.deleteById(id);
    }

    @Override
    public Optional<Node> save(Long id, String name, Double latitude, Double longitude, String category) {
        this.nodeRepository.deleteByName(name);
        Node newNode = new Node(id, name, latitude, longitude, category);
        return Optional.of(this.nodeRepository.save(newNode));
    }

}
