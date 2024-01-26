package mk.ukim.finki.culturecompassdians.web.controller;

import mk.ukim.finki.culturecompassdians.service.CSVReaderService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final CSVReaderService csvReaderService;

    public AdminController(CSVReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
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
//            csvReaderService.scrapeImagesForNodes();

            model.addAttribute("message", "Currently reading from CSV, and WebScraping");
        }
        model.addAttribute("bodyContent", "admin-page");
        return "master-template";
    }
}
