package mk.ukim.finki.culturecompassdians.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/map")
public class MapController {
    private final String openRouteServiceApiKey = "5b3ce3597851110001cf6248c7904ed6627f4afbbfe8257201c3a8ef";

    @GetMapping()
    public String showPathView(Model model) {
        model.addAttribute("bodyContent", "map");

        return "master-template";
    }

    @PostMapping("/findRoute")
    public String findRoute(@RequestParam("startLat") double startLat,
                            @RequestParam("startLon") double startLon,
                            @RequestParam("endLat") double endLat,
                            @RequestParam("endLon") double endLon,
                            HttpServletResponse response,
                            Model model) {

        model.addAttribute("startLat", startLat);
        model.addAttribute("startLon", startLon);
        model.addAttribute("endLat", endLat);
        model.addAttribute("endLon", endLon);
        model.addAttribute("contentBody", "map");
        return "master-template";
    }

}
