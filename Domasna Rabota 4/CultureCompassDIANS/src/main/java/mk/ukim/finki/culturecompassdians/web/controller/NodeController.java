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
import mk.ukim.finki.culturecompassdians.service.UserService;
import mk.ukim.finki.culturecompassdians.service.impl.OpenStreetMapService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
@RequestMapping("/node")
@Validated
@CrossOrigin(origins = "*")
public class NodeController {

    private final NodeService nodeService;
    private final UserService userService;
    private final OpenStreetMapService openStreetMapService;

    public NodeController(NodeService nodeService, UserService userService, OpenStreetMapService openStreetMapService) {
        this.nodeService = nodeService;
        this.userService = userService;
        this.openStreetMapService = openStreetMapService;
    }

    @GetMapping("/all")
    public String getAllPoints(@RequestParam(required = false) String error,
//                               @RequestParam(required = false) String ratingUpdate,
                               HttpServletRequest request,
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
        model.addAttribute("isAdmin", userService.isAdmin((User) request.getSession().getAttribute("user")));
        model.addAttribute("isLogin", userService.isLoggedIn((User) request.getSession().getAttribute("user")));
        return "master-template";
    }

    @GetMapping("/filteredByName")
    public String getByName(@RequestParam String search,
                            HttpServletRequest request,
                            Model model) throws JsonProcessingException {
        List<Node> allNodes = nodeService.findByNameContaining(search);
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
        model.addAttribute("isAdmin", userService.isAdmin((User) request.getSession().getAttribute("user")));
        model.addAttribute("isLogin", userService.isLoggedIn((User) request.getSession().getAttribute("user")));
        return "master-template";
    }

    @GetMapping("/filteredByCategory")
    public String getByCategory(@RequestParam String category,
                                HttpServletRequest request,
                                Model model) throws JsonProcessingException {
        List<Node> allNodes = nodeService.findByCategory(category);
        String nodesFormatted = new ObjectMapper().writeValueAsString(allNodes);
        model.addAttribute("nodes", nodesFormatted);
        model.addAttribute("bodyContent", "nodes");
        model.addAttribute("categories", nodeService.findAllCategories());
        model.addAttribute("isAdmin", userService.isAdmin((User) request.getSession().getAttribute("user")));
        model.addAttribute("isLogin", userService.isLoggedIn((User) request.getSession().getAttribute("user")));
        return "master-template";
    }

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
        Node node1 = this.nodeService.findNodeById(node.getId());
        node1.setName(node.getName());
        this.nodeService.saveNode(node1);
        return "redirect:/node/all";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String addNodePage(Model model) {
        model.addAttribute("newNode", new Node());
        model.addAttribute("bodyContent","add-page");
        return "master-template";
    }

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
        Node node = this.nodeService.findNodeById(id);
        node.setStars(node.getStars()+Double.parseDouble(userRating));
        node.setNumStars(node.getNumStars()+1);
        nodeService.deleteNodeById(id);
        nodeService.saveNode(node);
        model.addAttribute("isAdmin", userService.isAdmin((User) request.getSession().getAttribute("user")));
        model.addAttribute("isLogin", userService.isLoggedIn((User) request.getSession().getAttribute("user")));
        return "redirect:/node/all";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage() {
        return "access_denied";
    }

}
