package mk.ukim.finki.culturecompassdians.repository;

import mk.ukim.finki.culturecompassdians.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query("SELECT n FROM Node n WHERE LOWER(n.name) LIKE %:text%")
    List<Node> findByName(@Param("text") String text);
    List<Node> findByCategory(String text);

    @Query("SELECT DISTINCT n.category FROM Node n")
    List<String> findAllCategories();
}
