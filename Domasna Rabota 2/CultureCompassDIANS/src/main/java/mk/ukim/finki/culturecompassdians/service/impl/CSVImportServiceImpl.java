package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.TestPoint;
import mk.ukim.finki.culturecompassdians.repository.TestPointRepository;
import mk.ukim.finki.culturecompassdians.service.CSVImportService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CSVImportServiceImpl implements CSVImportService {

    private final TestPointRepository pointRepository;

    public CSVImportServiceImpl(TestPointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public void importCsv(String filePath) throws IOException {
        try (CSVParser parser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT)) {
            parser.forEach(point -> {
                String name = point.get("name");
                Long id = Long.valueOf(point.get("id"));
                Long latitude = Long.valueOf(point.get("latitude"));
                Long longitude = Long.valueOf(point.get("longitude"));
                String category = point.get("category");

                TestPoint newPoint = new TestPoint(id, name, latitude, longitude, category);
                pointRepository.save(newPoint);
            });
        }
    }
}
