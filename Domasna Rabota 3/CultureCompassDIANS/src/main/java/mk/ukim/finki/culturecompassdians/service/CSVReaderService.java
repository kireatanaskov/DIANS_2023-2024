package mk.ukim.finki.culturecompassdians.service;

import java.io.IOException;

public interface CSVReaderService {

    void saveWays() throws IOException;

    void saveNodes() throws IOException;

    void updateNodes();
}
