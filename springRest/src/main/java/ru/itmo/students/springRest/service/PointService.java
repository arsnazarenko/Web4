package ru.itmo.students.springRest.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepo userRepo;

    public Collection<Point> getAll(String token) {
        User user = getUserFromToken(token);
        return pointRepo.findByUser(user);
    }

    public Point getOne(Long id, String token) {
        User user = getUserFromToken(token);
        return pointRepo.findByIdAndUser(id, user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Point create(Point point, String token) {
        User user = getUserFromToken(token);
        areaCheckService.evaluateHitResult(point);
        point.setUser(user);
        return pointRepo.save(point);
    }



    public Point update(Long id, Point point, String token) {
        User user = getUserFromToken(token);
        Point pointFromDb = pointRepo.findByIdAndUser(id, user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        areaCheckService.evaluateHitResult(point);
        BeanUtils.copyProperties(point, pointFromDb, "id", "user");
        pointRepo.save(pointFromDb);
        return pointRepo.save(pointFromDb);
    }


    public void delete(Long id, String token) {
        User user = getUserFromToken(token);
        pointRepo.delete(
                pointRepo.findByIdAndUser(id, user).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
    }


    private User getUserFromToken(String token) {
        String login = jwtProvider.getLoginFromToken(token.substring(7));
        User user = userRepo.findByLogin(login);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return user;
    }
}
