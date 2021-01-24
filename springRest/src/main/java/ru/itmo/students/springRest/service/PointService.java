package ru.itmo.students.springRest.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.students.springRest.exception.ResourceNotFoundException;
import ru.itmo.students.springRest.domain.Point;
import ru.itmo.students.springRest.domain.User;
import ru.itmo.students.springRest.repo.PointRepo;
import ru.itmo.students.springRest.repo.UserRepo;

import java.util.Collection;
@Service
public class PointService {

    @Autowired
    private PointRepo pointRepo;
    @Autowired
    private AreaCheckService areaCheckService;
    @Autowired
    private UserRepo userRepo;

    public Collection<Point> getAll(String login) {
        User user = userRepo.findByLogin(login);
        return pointRepo.findByUser(user);
    }

    public Point getOne(Long id, String login) {
        User user = userRepo.findByLogin(login);
        return pointRepo.findByIdAndUser(id, user).orElseThrow(ResourceNotFoundException::new);
    }

    public Point create(Point point, String login) {
        User user = userRepo.findByLogin(login);
        areaCheckService.evaluateHitResult(point);
        point.setUser(user);
        return pointRepo.save(point);
    }



    public Point update(Long id, Point point, String login) {
        User user = userRepo.findByLogin(login);
        Point pointFromDb = pointRepo.findByIdAndUser(id, user).orElseThrow(ResourceNotFoundException::new);
        areaCheckService.evaluateHitResult(point);
        BeanUtils.copyProperties(point, pointFromDb, "id", "user");
        pointRepo.save(pointFromDb);
        return pointRepo.save(pointFromDb);
    }


    public void delete(Long id, String login) {
        User user = userRepo.findByLogin(login);
        pointRepo.delete(
                pointRepo.findByIdAndUser(id, user).
                        orElseThrow(ResourceNotFoundException::new)
        );
    }



}
