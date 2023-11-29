package mk.ukim.finki.culturecompassdians.web.controller;

import mk.ukim.finki.culturecompassdians.model.TestPoint;
import mk.ukim.finki.culturecompassdians.service.TestPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin(origins = "*")
public class TestPointController {

    private final TestPointService pointService;

    public TestPointController(TestPointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/all")
    public List<TestPoint> getAllPoints() {
        return pointService.findAllPoints();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestPoint> getPointById(@PathVariable Long id) {
        return pointService.findPointById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search-category")
    public List<TestPoint> getByCategory(@RequestParam String text) {
        return pointService.findByCategory(text);
    }
}
