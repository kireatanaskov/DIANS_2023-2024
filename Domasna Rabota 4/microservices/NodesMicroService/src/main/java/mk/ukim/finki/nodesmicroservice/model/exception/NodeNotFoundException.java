package mk.ukim.finki.nodesmicroservice.model.exception;

public class NodeNotFoundException extends RuntimeException {
    public NodeNotFoundException(Long id) {
        super(String.format("Node with id %d not found", id));
    }
}
