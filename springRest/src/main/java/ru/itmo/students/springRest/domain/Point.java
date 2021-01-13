package ru.itmo.students.springRest.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table
@Data
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_sequence")
    @SequenceGenerator(name = "point_sequence", initialValue = 1, allocationSize = 1)
    private Long id;
    @NotNull(message = "X filed cannot be null")
    private Double x;
    @NotNull(message = "Y filed cannot be null")
    private Double y;
    @NotNull(message = "R filed cannot be null")
    private Double r;
    @NotNull(message = "Result filed cannot be null")
    private Boolean result;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(id, point.id) &&
                Objects.equals(x, point.x) &&
                Objects.equals(y, point.y) &&
                Objects.equals(r, point.r) &&
                Objects.equals(result, point.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, r, result);
    }
}
