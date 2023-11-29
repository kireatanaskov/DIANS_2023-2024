package mk.ukim.finki.culturecompassdians.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
