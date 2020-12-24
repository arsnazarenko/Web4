package ru.itmo.students.springRest.controller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itmo.students.springRest.domain.Point;
import ru.itmo.students.springRest.exceptions.NotFoundException;
import ru.itmo.students.springRest.repo.PointRepo;
import ru.itmo.students.springRest.service.AreaCheckService;

import java.util.List;

@RestController
@RequestMapping("point")
@CrossOrigin(origins = "http://localhost:4200")
public class PointController {
    private final PointRepo pointRepo;
    private final AreaCheckService areaCheckService;

    @Autowired
    public PointController(AreaCheckService areaCheckService, PointRepo pointRepo) {
        this.areaCheckService = areaCheckService;
        this.pointRepo = pointRepo;
    }


    @GetMapping
    public List<Point> list() {
        return pointRepo.findAll();
    }

    @GetMapping("{id}")
    public Point getOne(
            @PathVariable("id") Long id
    ) {
        return pointRepo.findById(id).orElseThrow(NotFoundException::new);
    }


    @PostMapping
    public Point create(@RequestBody Point point) {
        Point resultPoint = areaCheckService.getResultPoint(point);
        return pointRepo.save(resultPoint);
    }

    // Для проверки:
    //fetch('point', {method: 'POST', headers: {'Content-type': 'application/json'}, body: JSON.stringify({x: 12, y: 4, r: 23})}).then(result => console.log(result));

    @PutMapping("{id}")
    public Point update(
            @PathVariable("id") Long id,
            @RequestBody Point point
    ) {
        Point pointFromDb = pointRepo.findById(id).orElseThrow(NotFoundException::new);
        Point resultPoint = areaCheckService.getResultPoint(point);
        BeanUtils.copyProperties(resultPoint, pointFromDb, "id");
        return pointRepo.save(pointFromDb);
    }

    // Для проверки:
    // fetch('point/4', {method: 'PUT', headers: {'Content-type': 'application/json'}, body: JSON.stringify({x: 12, y: 4, r: 23})}).then(result => console.log(result));


    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Point point)     //Spring сам получает объект point по его id
    {
        pointRepo.delete(point);
    }

    //Для проверки:
    // fetch('point/4', {method: 'DELETE'}).then(result => console.log(result))
}
