package mk.ukim.finki.culturecompassdians.web.controller;

import mk.ukim.finki.culturecompassdians.model.Way;
import mk.ukim.finki.culturecompassdians.service.WayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/way")
@Validated
public class WayController {

    private final WayService wayService;

    public WayController(WayService wayService) {
        this.wayService = wayService;
    }

    @GetMapping("/all")
    public String getAllWays(Model model){
        List<Way> allWays = wayService.findAllWays()
                .stream().limit(50).toList();
        model.addAttribute("ways", allWays);
        model.addAttribute("bodyContent", "ways");
        return "master-template";
    }
}
