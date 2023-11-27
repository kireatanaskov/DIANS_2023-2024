package mk.ukim.finki.culturecompassdians.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface CSVImportService {

    void importCsv(String filePath) throws IOException;

}
