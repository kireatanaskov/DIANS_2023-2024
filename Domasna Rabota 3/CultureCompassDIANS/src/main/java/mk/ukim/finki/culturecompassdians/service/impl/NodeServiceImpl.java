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
    public List<Node> findByCategoryOrName(String text) {
        return nodeRepository.findByCategoryOrNameContaining(text.toLowerCase());
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

}
