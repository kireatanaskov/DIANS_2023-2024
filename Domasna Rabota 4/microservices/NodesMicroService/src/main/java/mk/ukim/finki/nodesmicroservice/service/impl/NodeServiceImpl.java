package mk.ukim.finki.nodesmicroservice.service.impl;


import mk.ukim.finki.nodesmicroservice.model.Node;
import mk.ukim.finki.nodesmicroservice.model.exception.NodeNotFoundException;
import mk.ukim.finki.nodesmicroservice.repository.NodeRepository;
import mk.ukim.finki.nodesmicroservice.service.NodeService;
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
    public Node findNodeById(Long id) {
        return nodeRepository.findById(id)
                .orElseThrow(() -> new NodeNotFoundException(id));
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
    public Node saveNode(String nodeInfo) {
        String[] nodeParts = nodeInfo.split("; ");
        Node newNode = new Node(Long.parseLong(nodeParts[0]),
                                nodeParts[1],
                                Double.parseDouble(nodeParts[2]),
                                Double.parseDouble(nodeParts[3]),
                                nodeParts[4],
                                Double.parseDouble(nodeParts[5]));
        newNode.setNumStars(Integer.parseInt(nodeParts[6]));
        newNode.setRating(Double.parseDouble(nodeParts[7]));
        return nodeRepository.save(newNode);
    }

    @Override
    public Node editNode(String nodeInfo) {
        String[] nodeParts = nodeInfo.split("; ");
        Node node1 = findNodeById(Long.parseLong(nodeParts[0]));
        node1.setName(nodeParts[1]);
        return nodeRepository.save(node1);
    }

    @Override
    public void saveAll(List<Node> nodes) {
        nodeRepository.saveAll(nodes);
    }

    @Override
    public Node deleteNode(Long id) {
        Node node = findNodeById(id);
        nodeRepository.delete(node);
        return node;
    }

    @Override
    public Optional<Node> save(Long id, String name, Double latitude, Double longitude, String category) {
        this.nodeRepository.deleteByName(name);
        Node newNode = new Node(id, name, latitude, longitude, category, 1.0);
        return Optional.of(this.nodeRepository.save(newNode));
    }

}