package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.Node;

import java.io.IOException;
import java.util.List;

public interface CSVReaderService {

    void saveWays() throws IOException;

    void saveNodes() throws IOException;

    void updateNodes();

    void scrapeImagesForNodes(List<Node> nodes);
}
