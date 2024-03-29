package mk.ukim.finki.culturecompassdians.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

@Data
@Entity
@Table(name = "WAYS")
@NoArgsConstructor
@AllArgsConstructor
public class Way implements Serializable {
    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String category;
}
