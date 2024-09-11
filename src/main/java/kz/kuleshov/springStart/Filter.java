package kz.kuleshov.springStart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Long price;
    private String color;
    private double volume;
    private int year;
    private String brand;
}
