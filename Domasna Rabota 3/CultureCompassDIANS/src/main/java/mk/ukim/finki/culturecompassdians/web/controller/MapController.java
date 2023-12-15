package mk.ukim.finki.culturecompassdians.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/map")
public class MapController {
    private final String openRouteServiceApiKey = "5b3ce3597851110001cf6248c7904ed6627f4afbbfe8257201c3a8ef";

    @GetMapping()
    public String showPathView(Model model) {
        model.addAttribute("bodyContent", "navigate");

        return "master-template";
    }

    @PostMapping("/findRoute")
    public String findRoute(@RequestParam("startLat") double startLat,
                            @RequestParam("startLon") double startLon,
                            @RequestParam("endLat") double endLat,
                            @RequestParam("endLon") double endLon,
                            HttpSession session, Model model) {

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 303725df8c4c41ced12c4410164a0c9a48751966
        session.setAttribute("startLat", startLat);
        session.setAttribute("startLon", startLon);
        session.setAttribute("endLat", endLat);
        session.setAttribute("endLon", endLon);
        model.addAttribute("bodyContent", "navigate");
        return "master-template";
<<<<<<< HEAD
=======
        model.addAttribute("startLat", startLat);
        model.addAttribute("startLon", startLon);
        model.addAttribute("endLat", endLat);
        model.addAttribute("endLon", endLon);
//        model.addAttribute("bodyContent", "map");
        return "redirect:/";
>>>>>>> 9f660dd9e2fc316db4437536d3f07f5bf4a4e260
=======
>>>>>>> 303725df8c4c41ced12c4410164a0c9a48751966
    }
}
