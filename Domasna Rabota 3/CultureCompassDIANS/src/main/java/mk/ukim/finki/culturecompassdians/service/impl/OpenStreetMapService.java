package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.model.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OpenStreetMapService {
    private static final String OPENSTREETMAP_API_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

    public Node getNodeInfo(String nodeName, String category) {

        if (isNumeric(nodeName)) {
            throw new NotFoundException("Node name cannot be a number");
        }

        String apiUrl = OPENSTREETMAP_API_URL + nodeName + "&countrycodes=MK";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity(apiUrl, Map[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null && responseEntity.getBody().length > 0) {
            Map<String, Object> firstResult = responseEntity.getBody()[0];

            Long id = Long.parseLong(firstResult.get("osm_id").toString());
            double longitude = Double.parseDouble(firstResult.get("lon").toString());
            double latitude = Double.parseDouble(firstResult.get("lat").toString());

            return new Node(id, nodeName, latitude, longitude, category);
        } else {
            throw new NotFoundException("Node not found for name: " + nodeName);
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
