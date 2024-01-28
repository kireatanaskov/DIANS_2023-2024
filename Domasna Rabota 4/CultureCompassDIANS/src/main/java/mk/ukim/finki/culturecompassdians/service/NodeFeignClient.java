package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.Node;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "node-service",
        url = "http://localhost:9001/nodes-rest"
)
public interface NodeFeignClient {

    @GetMapping("/all")
    List<Node> findAllNodes();

    @GetMapping("/by-id/{id}")
    Node findNodeById(@PathVariable Long id);

    @GetMapping("/by-name/{name}")
    List<Node> findByNameContaining(@PathVariable String name);

    @GetMapping("/by-category/{category}")
    List<Node> findByCategory(@PathVariable String category);

    @GetMapping("/all-categories")
    List<String> findAllCategories();

    @PostMapping("/add/{nodeInfo}")
    Node saveNode(@PathVariable String nodeInfo);

    @PostMapping("/edit/{nodeInfo}")
    Node editNode(@PathVariable String nodeInfo);
//    void saveAll(List<Node> nodes);

    @PostMapping("/delete/{id}")
    Node deleteNodeById(@PathVariable Long id);
}