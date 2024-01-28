package mk.ukim.finki.nodesmicroservice.web;

import mk.ukim.finki.nodesmicroservice.model.Node;
import mk.ukim.finki.nodesmicroservice.service.NodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nodes-rest")
public class NodesController {

    private final NodeService nodeService;


    public NodesController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("/all")
    public List<Node> getAllNodes() {
        return nodeService.findAllNodes();
    }

    @GetMapping("/all-categories")
    public List<String> getAllCategories() {
        return nodeService.findAllCategories();
    }

    @GetMapping("/by-name/{name}")
    public List<Node> filterByName(@PathVariable String name) {
        return nodeService.findByNameContaining(name);
    }

    @GetMapping("/by-category/{category}")
    public List<Node> filterByCategory(@PathVariable String category) {
        return nodeService.findByCategory(category);
    }

    @GetMapping("/by-id/{id}")
    public Node findById(@PathVariable Long id) {
        return nodeService.findNodeById(id);
    }

    @PostMapping("/add/{nodeInfo}")
    public Node saveNode(@PathVariable String nodeInfo) {
        return nodeService.saveNode(nodeInfo);
    }

    @PostMapping("/edit/{nodeInfo}")
    public Node editNode(@PathVariable String nodeInfo) {
        return nodeService.editNode(nodeInfo);
    }

    @PostMapping("/delete/{id}")
    public Node deleteNode(@PathVariable Long id) {
        return nodeService.deleteNode(id);
    }
}
