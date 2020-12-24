package ru.itmo.students.springRest.service;
import org.springframework.stereotype.Service;
import ru.itmo.students.springRest.domain.Point;
import ru.itmo.students.springRest.exceptions.InvalidDataException;


@Service
public class AreaCheckService {
    public Point getResultPoint(Point point) {
        Double x = point.getX();
        Double y = point.getY();
        Double r = point.getR();
        if (x == null || y == null || r == null) {
            throw new InvalidDataException();
        }
        Boolean result =  ((x >= -r && x <= 0 && y >= -r/2 && y <= 0) ||
                (x <= 0 && y >= 0 && (y - 2 * x - r <= 0) ||
                        (y <= 0 && x >= 0 && (x * x + y * y <= r/2 * r/2)))
        );

        point.setResult(result);
        return point;
    }

}
