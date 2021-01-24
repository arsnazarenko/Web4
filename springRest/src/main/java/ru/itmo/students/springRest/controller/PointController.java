package ru.itmo.students.springRest.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;

import org.springframework.web.bind.annotation.*;
import ru.itmo.students.springRest.domain.Point;
import ru.itmo.students.springRest.service.PointService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;


@RestController
@RequestMapping("point")
public class PointController {


    @Autowired
    private PointService pointService;


    @GetMapping
    public Collection<Point> getAll(Principal principal) {
        return pointService.getAll(principal.getName());
    }


    @GetMapping("{id}")
    public Point getOne(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        return pointService.getOne(id, principal.getName());
    }


    @PostMapping
    public Point create(@Valid @RequestBody Point point, Principal principal) {
        return pointService.create(point, principal.getName());
    }


    // Для проверки:
    //fetch('point', {method: 'POST', headers: {'Content-type': 'application/json'}, body: JSON.stringify({x: 12, y: 4, r: 23})}).then(result => console.log(result));

    @PutMapping("{id}")
    @Secured("ROLE_ADMIN")
    public Point update(
            @PathVariable("id") Long id,
            @Valid @RequestBody Point point,
            Principal principal
    ) {
        return pointService.update(id, point, principal.getName());
    }


    // Для проверки:
    // fetch('point/4', {method: 'PUT', headers: {'Content-type': 'application/json'}, body: JSON.stringify({x: 12, y: 4, r: 23})}).then(result => console.log(result));

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable("id") Long id, Principal principal) {
        pointService.delete(id, principal.getName());
    }


}
