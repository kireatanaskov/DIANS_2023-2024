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

    @PostMapping("/add/{node}")
    public Node saveNode(@PathVariable Node node) {
        return nodeService.saveNode(node);
    }

    @PostMapping("/edit/{node}")
    public Node editNode(@PathVariable Node node) {
        return nodeService.editNode(node);
    }

    @PostMapping("/delete/{id}")
    public Node deleteNode(@PathVariable Long id) {
        return nodeService.deleteNode(id);
    }
}
