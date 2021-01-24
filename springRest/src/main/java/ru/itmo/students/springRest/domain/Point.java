package ru.itmo.students.springRest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_sequence")
    @SequenceGenerator(name = "point_sequence", initialValue = 1, allocationSize = 1)
    private Long id;
    @NotNull(message = "{x.not.null}")
    private Double x;
    @NotNull(message = "{y.not.null}")
    private Double y;
    @NotNull(message = "{r.not.null}")
    private Double r;

    private Boolean result;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
