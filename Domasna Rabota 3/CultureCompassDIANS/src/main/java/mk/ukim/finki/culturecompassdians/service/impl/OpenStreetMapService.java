package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.Node;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidCoordinatesException;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidNameForNode;
import mk.ukim.finki.culturecompassdians.model.exception.NodeAlreadyExistsException;
import mk.ukim.finki.culturecompassdians.model.exception.NotFoundException;
import mk.ukim.finki.culturecompassdians.repository.NodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class OpenStreetMapService {
    private static final String OPENSTREETMAP_API_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";
    private static final double mkMinLatitude = 40.8560;
    private static final double mkMaxLatitude = 42.3583;
    private static final double mkMinLongitude = 20.4632;
    private static final double mkMaxLongitude = 23.0345;
    private final NodeRepository nodeRepository;

    public OpenStreetMapService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node getNodeInfo(String nodeName) {

        if (isNumeric(nodeName)) {
            throw new InvalidNameForNode("Node name cannot be a number");
        }

        String apiUrl = OPENSTREETMAP_API_URL + nodeName;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity(apiUrl, Map[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null && responseEntity.getBody().length > 0) {
            Map<String, Object> firstResult = responseEntity.getBody()[0];

            Long id = Long.parseLong(firstResult.get("osm_id").toString());
            if (nodeExists(id)) {
                throw new NodeAlreadyExistsException("Node with name " + nodeName + " already exists");
            }
            double longitude = Double.parseDouble(firstResult.get("lon").toString());
            double latitude = Double.parseDouble(firstResult.get("lat").toString());

            if (!isWithinNorthMacedonia(latitude, longitude)) {
                throw new InvalidCoordinatesException("Coordinates are not within the borders of North Macedonia");
            }

            String category = firstResult.get("type").toString();


            return new Node(id, nodeName, latitude, longitude, category,1.0);
        } else {
            throw new NotFoundException("Node not found for name: " + nodeName);
        }
    }

    private boolean isNumeric(String str) {
        String trimmedStr = str.trim();
        return trimmedStr.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean nodeExists(Long nodeId) {
        return nodeRepository.existsById(nodeId);
    }

    private boolean isWithinNorthMacedonia(double latitude, double longitude) {
        return latitude >= mkMinLatitude && latitude <= mkMaxLatitude &&
                longitude >= mkMinLongitude && longitude <= mkMaxLongitude;
    }

}
