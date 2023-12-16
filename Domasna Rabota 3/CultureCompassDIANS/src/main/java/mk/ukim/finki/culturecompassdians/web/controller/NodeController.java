package mk.ukim.finki.culturecompassdians.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidCoordinatesException;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidNameForNode;
import mk.ukim.finki.culturecompassdians.model.exception.NodeAlreadyExistsException;
import mk.ukim.finki.culturecompassdians.model.exception.NotFoundException;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import mk.ukim.finki.culturecompassdians.service.impl.OpenStreetMapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@RequestMapping("/node")
@Validated
@CrossOrigin(origins = "*")
public class NodeController {

    private final NodeService nodeService;
    private final OpenStreetMapService openStreetMapService;

    public NodeController(NodeService nodeService, OpenStreetMapService openStreetMapService) {
        this.nodeService = nodeService;
        this.openStreetMapService = openStreetMapService;
    }

    @GetMapping("/all")
    public String getAllPoints(@RequestParam(required = false) String error,
                               Model model) throws JsonProcessingException {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        List<Node> allNodes = nodeService.findAllNodes();
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
        return "master-template";
    }

    @GetMapping("/filteredByName")
    public String getByName(@RequestParam String search,
                               Model model) throws JsonProcessingException {
        List<Node> allNodes = nodeService.findByNameContaining(search);
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
        return "master-template";
    }

    @GetMapping("/filteredByCategory")
    public String getByCategory(@RequestParam String category,
                               Model model) throws JsonProcessingException {
        List<Node> allNodes = nodeService.findByCategory(category);
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
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

    @PostMapping("/add")
    public String saveNode(@ModelAttribute Node newNode, Model model) {
        try {
            Node node = openStreetMapService.getNodeInfo(newNode.getName());

            newNode.setId(node.getId());
            newNode.setLongitude(node.getLongitude());
            newNode.setLatitude(node.getLatitude());
            newNode.setCategory(node.getCategory());
            newNode.setStars(1.0);
            newNode.setNumStars(1);
            newNode.setRating(newNode.getRating());
            newNode.setWikipediaData("");

            Node savedNode = this.nodeService.saveNode(newNode);
            model.addAttribute("newNode", savedNode);

            return "redirect:/node/all";
        } catch (NotFoundException | NodeAlreadyExistsException | InvalidCoordinatesException | InvalidNameForNode e) {
            model.addAttribute("newNode", new Node());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("bodyContent", "add-page");

            return "master-template";
        }
    }

    @GetMapping("/add-form")
    public String addNodePage(Model model) {
        model.addAttribute("newNode", new Node());
        model.addAttribute("bodyContent","add-page");
        return "master-template";
    }

    // TODO edit the rating

    @GetMapping("/edit-form/{id}")
    public String editNodePage(@PathVariable Long id, Model model) {
        if (this.nodeService.findNodeById(id).isPresent()) {
            Node node = this.nodeService.findNodeById(id).get();
            model.addAttribute("newNode", node);
            model.addAttribute("bodyContent","edit-page");
            return "master-template";
        }
        return "redirect:/node/all?error=NodeNotFound";
    }

    @GetMapping("/updateRating/{id}")
    public String updateRating(@PathVariable Long id,
                               @RequestParam String userRating) {
        Node node = this.nodeService.findNodeById(id).get();
        node.setStars(node.getStars()+Double.parseDouble(userRating));
        node.setNumStars(node.getNumStars()+1);
        nodeService.deleteNodeById(id);
        nodeService.saveNode(node);
        return "redirect:/node/all";
    }

//    @PostMapping("/location")
//    @ResponseBody
//    public String receiveLocation(@RequestParam("latitude") double latitude,
//                                  @RequestParam("longitude") double longitude,
//                                  Model model) {
//        // Store latitude and longitude in variables
//        model.addAttribute("latitude", latitude);
//        model.addAttribute("longitude", longitude);
//
//        return "Location received successfully!";
//    }

}
