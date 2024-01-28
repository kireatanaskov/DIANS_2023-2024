package mk.ukim.finki.culturecompassdians.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

@Data
@Entity
@Table(name = "NODES")
@NoArgsConstructor
@AllArgsConstructor
public class Node implements Serializable {
    @Id
    private Long id;

    @NotNull
    private String name;
    private double latitude;
    private double longitude;
    private String category;
    private double stars;
    private int numStars=0;
    private double rating;

    @Column(length = 4000)
    private String wikipediaData;

    @Column(length = 4000)
    private String imageSource = null;

    public Node(Long id, String name, double latitude, double longitude, String category, double stars) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.stars = stars;
        this.numStars++;
        this.rating = getRating();
    }

    public double getRating() {
        if (numStars == 0){
            return 0.0;
        }
        return stars / numStars;
    }

}
