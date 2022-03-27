package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import model.User;
import utility.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    public Label deletedAppointmentConfirmation;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentId;
    @FXML
    private TableColumn<Appointment, String> title;
    @FXML
    private TableColumn<Appointment, String> description;
    @FXML
    private TableColumn<Appointment, String> location;
    @FXML
    private TableColumn<Appointment, String> contact;
    @FXML
    private TableColumn<Appointment, String> type;
    @FXML
    private TableColumn<Appointment, String> startDateAndTime;
    @FXML
    private TableColumn<Appointment, String> endDateAndTime;
    @FXML
    private TableColumn<Appointment, Integer> customerId;
    @FXML
    private TableColumn<Appointment, Integer> userId;

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;}

    public static void setCurrentUser(User currentUser) {
        AppointmentsController.currentUser = currentUser;}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> allAppointments = null;
        try {
            allAppointments = AppointmentQuery.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // get observable list of all appointments
        appointmentTable.setItems(allAppointments);

        //fill each column
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        // fill and format time columns
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        startDateAndTime.setCellValueFactory(appointment ->
                new SimpleStringProperty(appointment.getValue().getStart().format(formatter)));
        endDateAndTime.setCellValueFactory(appointment ->
                new SimpleStringProperty(appointment.getValue().getEnd().format(formatter)));

        appointmentTable.getColumns().addAll(appointmentId);
        appointmentTable.getSortOrder().add(appointmentId);
        appointmentTable.sort();
    }

    public void onActionReturnToMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        AddAppointmentController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionUpdateAppointment(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        UpdateAppointmentController.setAppointment(selectedAppointment);

        UpdateAppointmentController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Update Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        AppointmentQuery.delete(selectedAppointment.getAppointmentId());

        // get observable list of all appointments
        try {
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentTable.getSortOrder().add(appointmentId);
        appointmentTable.sort();

        deletedAppointmentConfirmation.setText("Appointment " + selectedAppointment.getAppointmentId() + " of type \"" +
                selectedAppointment.getType() + "\" was canceled.");
    }

    public void onActionDisplayAllAppointments(ActionEvent actionEvent) {
        // get observable list of all appointments
        try {
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionDisplayAppointmentsThisMonth(ActionEvent actionEvent) throws SQLException {
        // get observable list of appts this month
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        ObservableList<Appointment> appointmentsThisMonth = FXCollections.observableArrayList();
        Month currentMonth = LocalDate.now().getMonth();

        for (Appointment appointment : allAppointments) {
            if (appointment.getStart().toLocalDate().getMonth().equals(currentMonth)) {
                appointmentsThisMonth.add(appointment);
            }
        }

        appointmentTable.setItems(appointmentsThisMonth);
        appointmentTable.getSortOrder().add(appointmentId);
        appointmentTable.sort();
    }

    public void onActionDisplayAppointmentsThisWeek(ActionEvent actionEvent) throws SQLException {
        // get observable list of appts this week
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        ObservableList<Appointment> appointmentsThisWeek = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        int currentWeekday = currentDate.getDayOfWeek().getValue();
        LocalDate firstDayOfWeek = currentDate.minusDays(1); //moves by 1 day because we don't have a <= for localdate values, only .isBefore or .isAfter
        LocalDate lastDayOfWeek = currentDate.plusDays(1);

        for (int i = 0, j = 6; i < 7; i++, j--) {
            if ((currentWeekday % 7) == i) {
                firstDayOfWeek = currentDate.minusDays(i);
                lastDayOfWeek = currentDate.plusDays(j);
            }
        }

        for(Appointment appointment : allAppointments) {
            if (firstDayOfWeek.isBefore(appointment.getStart().toLocalDate())
                    && appointment.getStart().toLocalDate().isBefore(lastDayOfWeek)) {
                appointmentsThisWeek.add(appointment);
            }
        }

        appointmentTable.setItems(appointmentsThisWeek);
        appointmentTable.getSortOrder().add(appointmentId);
        appointmentTable.sort();
    }
}
