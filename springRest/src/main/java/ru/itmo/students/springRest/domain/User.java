package ru.itmo.students.springRest.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "usr")
@Entity
@Data
@EqualsAndHashCode(of = {"id", "login"})
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_sequence")
    @SequenceGenerator(name = "usr_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    private String login;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;


//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private Set<Point> userPoints;

}
