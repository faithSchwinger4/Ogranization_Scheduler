package helper;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppointmentQuery {

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {

        String sql = "SELECT * FROM Appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        return ;
    }
}
