package mk.ukim.finki.culturecompassdians.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.model.Role;
import mk.ukim.finki.culturecompassdians.model.User;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidCoordinatesException;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidNameForNode;
import mk.ukim.finki.culturecompassdians.model.exception.NodeAlreadyExistsException;
import mk.ukim.finki.culturecompassdians.model.exception.NotFoundException;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import mk.ukim.finki.culturecompassdians.service.impl.OpenStreetMapService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.Collections;
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

    private Boolean [] getUserAuth (HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        boolean isAdmin = false, isLogin = false;
        if (user != null){
            isAdmin = user.getRole() == Role.ROLE_ADMIN;
            isLogin = true;
        }
        Boolean [] array = {isAdmin, isLogin};
        return array;
    }

    @GetMapping("/all")
    public String getAllPoints(@RequestParam(required = false) String error,
//                               @RequestParam(required = false) String ratingUpdate,
                               HttpServletRequest request,
                               Model model) throws JsonProcessingException {
        Boolean [] array = getUserAuth(request);

        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
//        if (ratingUpdate != null) {
//            model.addAttribute("ratedNode", this.nodeService.findNodeById(Long.parseLong(ratingUpdate)).get());
//        }
        List<Node> allNodes = nodeService.findAllNodes();
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
        model.addAttribute("isAdmin", array[0]);
        model.addAttribute("isLogin", array[1]);
        return "master-template";
    }

    @GetMapping("/filteredByName")
    public String getByName(@RequestParam String search,
                            HttpServletRequest request,
                            Model model) throws JsonProcessingException {
        Boolean [] array = getUserAuth(request);
        List<Node> allNodes = nodeService.findByNameContaining(search);
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
        model.addAttribute("isAdmin", array[0]);
        model.addAttribute("isLogin", array[1]);
        return "master-template";
    }

    @GetMapping("/filteredByCategory")
    public String getByCategory(@RequestParam String category,
                                HttpServletRequest request,
                                Model model) throws JsonProcessingException {
        Boolean [] array = getUserAuth(request);
        List<Node> allNodes = nodeService.findByCategory(category);
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
        model.addAttribute("isAdmin", array[0]);
        model.addAttribute("isLogin", array[1]);
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
    @PreAuthorize("hasRole('ADMIN')")
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

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editNode(@ModelAttribute Node node) {
//        Node node1 = this.nodeService.findNodeById(node.getId()).get();
//        node1.setName(node.getName());
//        this.nodeService.saveNode(node1);
        nodeService.editNode(node);
        return "redirect:/node/all";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String addNodePage(Model model) {
        model.addAttribute("newNode", new Node());
        model.addAttribute("bodyContent","add-page");
        return "master-template";
    }

    // TODO edit the rating

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editNodePage(@PathVariable Long id, Model model) {
        if (this.nodeService.findNodeById(id) != null) {
            Node node = this.nodeService.findNodeById(id);
            model.addAttribute("newNode", node);
            model.addAttribute("bodyContent","edit-page");
            return "master-template";
        }
        return "redirect:/node/all?error=NodeNotFound";
    }

    @GetMapping("/updateRating/{id}")
    public String updateRating(@PathVariable Long id,
                               @RequestParam String userRating,
                               HttpServletRequest request,
                               Model model) {
        Boolean [] array = getUserAuth(request);
        Node node = this.nodeService.findNodeById(id);
        node.setStars(node.getStars()+Double.parseDouble(userRating));
        node.setNumStars(node.getNumStars()+1);
        nodeService.deleteNodeById(id);
        nodeService.saveNode(node);
        model.addAttribute("isAdmin", array[0]);
        model.addAttribute("isLogin", array[1]);
        return "redirect:/node/all";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage() {
        return "access_denied";
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
