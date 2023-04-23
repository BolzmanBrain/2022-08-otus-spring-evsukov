package ru.otus.spring.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomHealthIndicator implements HealthIndicator {
    private final Random random = new Random();
    @Override
    public Health health() {
        int statusIndicator = random.nextInt();

        if (statusIndicator % 3 == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Ретроградный меркурий")
                    .build();
        } else if (statusIndicator % 3 == 1) {
            return Health.down()
                    .status(Status.OUT_OF_SERVICE)
                    .withDetail("message", "Ещё чуть-чуть, и жизнь удалась")
                    .build();
        } else {
            return Health.up()
                    .status(Status.UP)
                    .withDetail("message", "Астрологи объявили неделю без происшествий на проде")
                    .build();
        }
    }
}
