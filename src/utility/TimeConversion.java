package utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeConversion {

    public static LocalDateTime createLocalDateTime(LocalDate localDate, LocalTime localTime) {
        LocalDateTime newDateTime = localDate.atTime(localTime);
        return newDateTime;
    }
}
