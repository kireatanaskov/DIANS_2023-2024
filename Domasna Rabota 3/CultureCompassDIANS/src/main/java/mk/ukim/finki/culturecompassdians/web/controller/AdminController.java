package mk.ukim.finki.culturecompassdians.web.controller;

import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.service.CSVReaderService;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final CSVReaderService csvReaderService;
    private final NodeService nodeService;

    public AdminController(CSVReaderService csvReaderService, NodeService nodeService) {
        this.csvReaderService = csvReaderService;
        this.nodeService = nodeService;
    }

    // @Secured("ROLE_ADMIN")
    @GetMapping("/panel")
    public String adminPanel(Model model) {
        model.addAttribute("bodyContent", "admin-page");
        return "master-template";
    }

    @GetMapping("/readcsv")
    public String readCSV(@RequestParam(name = "message", required = false) String msg,
                          Model model) {
        if (msg.equalsIgnoreCase("reading")) {
            try {
                csvReaderService.saveNodes();
                csvReaderService.saveWays();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            csvReaderService.updateNodes();

            model.addAttribute("message", "Currently reading from CSV, and WebScraping");
        }
        model.addAttribute("bodyContent", "admin-page");
        return "master-template";
    }

    @GetMapping("/imagesOne")
    public String scrapeHalfImages(Model model) {
        List<Node> allNodes = nodeService.findAllNodes();
        List<Node> nodes = allNodes.stream()
                .limit(allNodes.size() / 2)
                .toList();
        csvReaderService.scrapeImagesForNodes(nodes);
        model.addAttribute("bodyContent", "admin-page");
        return "master-template";
    }

    @GetMapping("/imagesTwo")
    public String scrapeLastImages(Model model) {
        List<Node> allNodes = nodeService.findAllNodes();
        // Get from half to the end
        List<Node> nodes = IntStream.range(allNodes.size() / 2, allNodes.size())
                .mapToObj(allNodes::get).collect(Collectors.toList());

        csvReaderService.scrapeImagesForNodes(nodes);
        model.addAttribute("bodyContent", "admin-page");

        return "master-template";
    }
}
