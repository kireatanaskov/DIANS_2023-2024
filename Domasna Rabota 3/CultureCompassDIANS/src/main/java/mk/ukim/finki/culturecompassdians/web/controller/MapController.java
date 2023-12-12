package mk.ukim.finki.culturecompassdians.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/map")
public class MapController {
    private final String openRouteServiceApiKey = "5b3ce3597851110001cf6248c7904ed6627f4afbbfe8257201c3a8ef";

    @PostMapping("/findRoute")
    public String findRoute(@RequestParam("startLat") double startLat,
                            @RequestParam("startLon") double startLon,
                            @RequestParam("endLat") double endLat,
                            @RequestParam("endLon") double endLon,
                            Model model) {

        String apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=" + openRouteServiceApiKey +
                "&start=" + startLon + "," + startLat +
                "&end=" + endLon + "," + endLat;

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        model.addAttribute("routeJson", jsonResponse);
        return "Location received successfully";
    }
}
