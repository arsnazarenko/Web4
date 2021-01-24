package ru.itmo.students.springRest.service;
import org.springframework.stereotype.Service;
import ru.itmo.students.springRest.exception.RadiusValueInvalidException;
import ru.itmo.students.springRest.domain.Point;


@Service
public class AreaCheckService {
    public void evaluateHitResult(Point point) {
        Double x = point.getX();
        Double y = point.getY();
        Double r = point.getR();
        if (r <= 0) {
            throw new RadiusValueInvalidException(r);
        }
        Boolean result =  ((x >= -r && x <= 0 && y >= -r/2 && y <= 0) ||
                (x <= 0 && y >= 0 && (y - 2 * x - r <= 0) ||
                        (y <= 0 && x >= 0 && (x * x + y * y <= r/2 * r/2)))
        );
        point.setResult(result);

    }

}
