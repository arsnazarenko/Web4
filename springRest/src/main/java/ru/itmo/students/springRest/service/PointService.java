package ru.itmo.students.springRest.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.students.springRest.config.jwt.JwtProvider;
import ru.itmo.students.springRest.domain.Point;
import ru.itmo.students.springRest.domain.User;
import ru.itmo.students.springRest.repo.PointRepo;
import ru.itmo.students.springRest.repo.UserRepo;

import javax.validation.Valid;
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
        return pointRepo.findByIdAndUser(id, user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Point create(Point point, String login) {
        User user = userRepo.findByLogin(login);
        areaCheckService.evaluateHitResult(point);
        point.setUser(user);
        return pointRepo.save(point);
    }



    public Point update(Long id, Point point, String login) {
        User user = userRepo.findByLogin(login);
        Point pointFromDb = pointRepo.findByIdAndUser(id, user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        areaCheckService.evaluateHitResult(point);
        BeanUtils.copyProperties(point, pointFromDb, "id", "user");
        pointRepo.save(pointFromDb);
        return pointRepo.save(pointFromDb);
    }


    public void delete(Long id, String login) {
        User user = userRepo.findByLogin(login);
        pointRepo.delete(
                pointRepo.findByIdAndUser(id, user).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
    }



}
