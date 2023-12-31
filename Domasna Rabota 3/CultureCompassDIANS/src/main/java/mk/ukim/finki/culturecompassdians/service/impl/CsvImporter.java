package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.model.Way;
import mk.ukim.finki.culturecompassdians.service.CSVReaderService;
import mk.ukim.finki.culturecompassdians.service.NodeService;
import mk.ukim.finki.culturecompassdians.service.WayService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvImporter implements CSVReaderService {

    private final NodeService nodeService;
    private final WayService wayService;
    private static final String NODES_PATH = "static/csv/nodes.csv";
    private static final String WAYS_PATH = "static/csv/ways.csv";

    public CsvImporter(NodeService nodeService, WayService wayService) {
        this.nodeService = nodeService;
        this.wayService = wayService;
    }


    // Skip first line (id, name, category)
    public void saveWays() throws IOException {
        ClassPathResource resource = new ClassPathResource(WAYS_PATH);
        InputStream is = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        reader.lines()
                .skip(1)
                .map(line -> line.split(","))
                .forEach(parts -> {
                    Long id = Long.valueOf(parts[0]);
                    String name = parts[1];
                    String category = parts[2];
                    Way way = new Way(id, name, category);
                    wayService.saveWay(way);
                });
        reader.close();
    }

    public void saveNodes() throws IOException {
        ClassPathResource resource = new ClassPathResource(NODES_PATH);
        InputStream is = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        reader.lines()
                .skip(1)
                .map(line -> line.split(","))
                .forEach(parts -> {
                    Long id = Long.valueOf(parts[0]);
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[2]);
                    String name = parts[3];
                    String category = parts[4];
                    Node node = new Node(id, name, latitude, longitude, category, 1.0);
                    nodeService.saveNode(node);
                });
        reader.close();
    }

    public void updateNodes() {
        List<Node> nodes = nodeService.findAllNodes();
        nodes.forEach(node -> {
            String OSM_URL = "https://www.openstreetmap.org/node/" + node.getId();
            String wikipediaLink = OpenStreetMapScraper.getWikipediaLink(OSM_URL);
            String firstParagraph = OpenStreetMapScraper.getFirstParagraph(wikipediaLink);
            node.setWikipediaData(!firstParagraph.isEmpty() ? firstParagraph : "");
        });

        nodeService.saveAll(nodes);
    }

    public void scrapeImagesForNodes(List<Node> nodes) {
        String URL = "https://www.bing.com/images/search?q=";

        nodes.forEach(node -> {
            String title = node.getName();
            String category = node.getCategory();
            String searchURL = URL.concat(title + "+" + category + "+mk");
            String imgSource = OpenStreetMapScraper.getImageLink(searchURL);
            node.setImageSource(imgSource);
        });

        nodeService.saveAll(nodes);
    }
}
