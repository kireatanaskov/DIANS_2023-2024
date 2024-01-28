package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.model.UserDto;
import mk.ukim.finki.culturecompassdians.repository.NodeRepository;
import mk.ukim.finki.culturecompassdians.service.NodeFeignClient;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;
    private final NodeFeignClient feignClient;

    public NodeServiceImpl(NodeRepository nodeRepository, NodeFeignClient feignClient) {
        this.nodeRepository = nodeRepository;
        this.feignClient = feignClient;
    }

    @Override
    public List<Node> findAllNodes() {
        return feignClient.findAllNodes();
    }

    @Override
    public Node findNodeById(Long id) {
        return feignClient.findNodeById(id);
    }

    @Override
    public List<Node> findByNameContaining(String text) {
        return feignClient.findByNameContaining(text.toLowerCase());
    }

    @Override
    public List<Node> findByCategory(String text) {
        return feignClient.findByCategory(text);
    }

    @Override
    public List<String> findAllCategories() {
        return feignClient.findAllCategories();
    }

    @Override
    public Node saveNode(Node point) {
        String nodeInfo = createStringForNode(point);
        return feignClient.saveNode(nodeInfo);
    }

//    @Override
//    public Node editNode(Node node) {
//        String nodeInfo = createStringForNode(node);
//        return feignClient.editNode(nodeInfo);
//    }

    @Override
    public void saveAll(List<Node> nodes) {
        nodeRepository.saveAll(nodes);
    }

    @Override
    public void deleteNodeById(Long id) {
        feignClient.deleteNodeById(id);
    }
//    @Override
//    public Optional<Node> save(Long id, String name, Double latitude, Double longitude, String category) {
//        this.nodeRepository.deleteByName(name);
//        Node newNode = new Node(id, name, latitude, longitude, category, 1.0);
//        return Optional.of(this.nodeRepository.save(newNode));
//    }
    private String createStringForNode(Node point) {
        StringBuilder sb = new StringBuilder();

        sb.append(point.getId())
                .append("; ")
                .append(point.getName())
                .append("; ")
                .append(point.getLatitude())
                .append("; ")
                .append(point.getLongitude())
                .append("; ")
                .append(point.getCategory())
                .append("; ")
                .append(point.getStars())
                .append("; ")
                .append(point.getNumStars())
                .append("; ")
                .append(point.getRating());

        return sb.toString();
    }
}