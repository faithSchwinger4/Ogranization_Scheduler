package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** This class holds methods used to convert time objects and create an observable list of times. */
public class TimeHelper {

    /** This function creates a localDateTime object from a localDate and localTime object.
     * @param localDate the localDate object
     * @param localTime the localTime object
     * @return the localDate and localTime as one complete localDateTime object*/
    public static LocalDateTime createLocalDateTime(LocalDate localDate, LocalTime localTime) {
        LocalDateTime newDateTime = localDate.atTime(localTime);
        return newDateTime;
    }

    /** This function creates Local Time objects at 30 minute increments over a period of 24 hours. It then places each
     * of these objects in an ObservableList that is returned by the function.
     * @return an ObservableList of LocalTime objects over a 24-hour period in 30 minutes increments*/
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
