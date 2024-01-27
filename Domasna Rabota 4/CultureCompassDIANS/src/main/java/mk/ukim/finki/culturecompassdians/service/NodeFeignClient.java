package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.Node;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "node-service",
        url = "http://localhost:9001"
)
public interface NodeFeignClient {
    // /nodes-rest

    @GetMapping("/nodes-rest/all")
    List<Node> findAllNodes();

    @GetMapping("/nodes-rest/by-id/{id}")
    Node findNodeById(@PathVariable Long id);

    @GetMapping("/nodes-rest/{name}")
    List<Node> findByNameContaining(@PathVariable String name);

    @GetMapping("/nodes-rest/{category}")
    List<Node> findByCategory(@PathVariable String category);

    @GetMapping("/nodes-rest/all-categories")
    List<String> findAllCategories();

    @PostMapping("/nodes-rest/add/{node}")
    Node saveNode(@PathVariable Node node);

    @PostMapping("/nodes-rest/edit/{node}")
    Node editNode(@PathVariable Node node);
//    void saveAll(List<Node> nodes);

    @PostMapping("/nodes-rest/delete/{id}")
    Node deleteNodeById(@PathVariable Long id);
}
