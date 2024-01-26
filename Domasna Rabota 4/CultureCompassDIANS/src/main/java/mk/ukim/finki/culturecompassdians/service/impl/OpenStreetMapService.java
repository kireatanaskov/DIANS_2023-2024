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

@Service
public class OpenStreetMapService {
    private static final String OPENSTREETMAP_API_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";
    private static final double MK_MIN_LATITUDE = 40.8560;
    private static final double MK_MAX_LATITUDE = 42.3583;
    private static final double MK_MIN_LONGITUDE = 20.4632;
    private static final double MK_MAX_LONGITUDE = 23.0345;
    private final NodeRepository nodeRepository;

    public OpenStreetMapService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node getNodeInfo(String nodeName) {
        validateNodeName(nodeName);

        String apiUrl = OPENSTREETMAP_API_URL + nodeName;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity(apiUrl, Map[].class);

        if (isValidResponse(responseEntity)) {
            Map<String, Object> firstResult = responseEntity.getBody()[0];
            Node node = createNodeFromResult(nodeName, firstResult);
            validateNode(node);
            return node;
        } else {
            throw new NotFoundException("Node not found for name: " + nodeName);
        }
    }

    private void validateNodeName(String nodeName) {
        if (isNumeric(nodeName)) {
            throw new InvalidNameForNode("Node name cannot be a number");
        }
    }

    private boolean isNumeric(String str) {
        return str.trim().matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isValidResponse(ResponseEntity<Map[]> responseEntity) {
        return responseEntity.getStatusCode().is2xxSuccessful() &&
                responseEntity.getBody() != null &&
                responseEntity.getBody().length > 0;
    }

    private Node createNodeFromResult(String nodeName, Map<String, Object> result) {
        Long id = Long.parseLong(result.get("osm_id").toString());
        double longitude = Double.parseDouble(result.get("lon").toString());
        double latitude = Double.parseDouble(result.get("lat").toString());
        String category = result.get("type").toString();

        return new Node(id, nodeName, latitude, longitude, category, 1.0);
    }

    private void validateNode(Node node) {
        if (nodeExists(node.getId())) {
            throw new NodeAlreadyExistsException("Node with name " + node.getName() + " already exists");
        }

        if (!isWithinNorthMacedonia(node.getLatitude(), node.getLongitude())) {
            throw new InvalidCoordinatesException("Coordinates are not within the borders of North Macedonia");
        }
    }

    private boolean nodeExists(Long nodeId) {
        return nodeRepository.existsById(nodeId);
    }

    private boolean isWithinNorthMacedonia(double latitude, double longitude) {
        return latitude >= MK_MIN_LATITUDE && latitude <= MK_MAX_LATITUDE &&
                longitude >= MK_MIN_LONGITUDE && longitude <= MK_MAX_LONGITUDE;
    }
}
