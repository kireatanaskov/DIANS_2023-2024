package mk.ukim.finki.culturecompassdians.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.model.Point;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public String getAllPoints(Model model) throws JsonProcessingException {
        List<Node> allNodes = nodeService.findAllNodes();
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
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

    @PostMapping("/location")
    @ResponseBody
    public String receiveLocation(@RequestParam("latitude") double latitude,
                                  @RequestParam("longitude") double longitude,
                                  Model model) {
        // Store latitude and longitude in variables
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);

        return "Location received successfully!";
    }

    @GetMapping("/findWay")
    public String findWay(Model model) {
        List<Node> waypoints = nodeService.findAllNodes();

        List<Point> waypointPoints = waypoints.stream()
                .map(node -> new Point(node.getLatitude(), node.getLongitude()))
                .collect(Collectors.toList());

        model.addAttribute("wayBetweenPoints", waypointPoints);
        model.addAttribute("bodyContent", "map-result");
        return "master-template";
    }
}
