package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.TestPoint;
import mk.ukim.finki.culturecompassdians.service.TestPointService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

@Component
public class CsvImporter implements CommandLineRunner {

    private final TestPointService pointService;

    public CsvImporter(TestPointService pointService) {
        this.pointService = pointService;
    }

    // Read Nodes from csv file on startup
    // TODO: Refactor into 2 functions for nodes and ways each
    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("static/csv/nodes.csv");
        InputStream is = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        reader.lines()
                .skip(1)
                .forEach(line -> {
                    String[] parts = line.split(",");
                    Long id = Long.valueOf(parts[0]);
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[2]);
                    String name = parts[3];
                    String category = parts[4];
                    TestPoint node = new TestPoint(id, name, latitude, longitude, category);
                    pointService.savePoint(node);
                });

        reader.close();
    }
}
