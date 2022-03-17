package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeConversion {

    public static LocalDateTime createLocalDateTime(LocalDate localDate, LocalTime localTime) {
        LocalDateTime newDateTime = localDate.atTime(localTime);
        return newDateTime;
    }

    public static ObservableList<LocalTime> createTimeList() {
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        for (int i = 0; i < 24; ++i) {
            LocalTime nextHour = LocalTime.of(i,0);
            times.add(nextHour);
            LocalTime nextHalfHour = LocalTime.of(i, 30);
            times.add(nextHalfHour);
        }
        return times;
    }
}
