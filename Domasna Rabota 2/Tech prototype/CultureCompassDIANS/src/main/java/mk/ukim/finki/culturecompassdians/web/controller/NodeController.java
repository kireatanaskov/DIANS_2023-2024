package mk.ukim.finki.culturecompassdians.web.controller;

import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/node")
@Validated
@CrossOrigin(origins = "*")
public class NodeController {

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("/all")
    public String getAllPoints(Model model) {
        List<Node> allNodes = nodeService.findAllNodes();
        model.addAttribute("nodes", allNodes);
        model.addAttribute("bodyContent", "nodes");
        return "master-template";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Node> getPointById(@PathVariable Long id) {
//        return nodeService.findNodeById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/search-category")
//    public List<Node> getByCategory(@RequestParam String text) {
//        return nodeService.findByCategory(text);
//    }
}
