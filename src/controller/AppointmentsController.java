package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** This class launches the Appointments screen and contains functions for user interaction with the screen. */
public class AppointmentsController implements Initializable {

    /** This label is where the deleted appointment information and confirmation are printed to. It is also where error
     * statements are printed to. */
    public Label deletedAppointmentConfirmation;

    /** This table is where the appointments are all displayed. */
    @FXML
    private TableView<Appointment> appointmentTable;

    /** This column is where the appointmentIds are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, Integer> appointmentId;

    /** This column is where the titles are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, String> title;

    /** This column is where the descriptions are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, String> description;

    /** This column is where the locations are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, String> location;

    /** This column is where the contacts are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, String> contact;

    /** This column is where the types are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, String> type;

    /** This column is where the start dates and times are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, String> startDateAndTime;

    /** This column is where the end dates and times are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, String> endDateAndTime;

    /** This column is where the customerIds are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, Integer> customerId;

    /** This column is where the userIds are listed for each appointment. */
    @FXML
    private TableColumn<Appointment, Integer> userId;

    /** Stores the current user's information as a User object. This allows the Appointment screen to pass through the
     * current user's information. */
    private static User currentUser;


    /** This function sets the current user information. It allows the previous screen to pass through the information
     * for the current user.
     * @param currentUser is the User object representing the current user logged into the application */
    public static void setCurrentUser(User currentUser) {
        AppointmentsController.currentUser = currentUser;}


    /** This method initializes the screen by filling the table with all the appointments from the database. It sets the
     * cellValueFactories for each column, the format for the date and time in those columns, and sorts the table by
     * the appointmentId column. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // use a query to get all the appointments in the database
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

        appointmentTable.getSortOrder().add(appointmentId);
        appointmentTable.sort();
    }

    /** This method returns the user to the MainMenu screen without affecting any of the appointments. */
    public void onActionReturnToMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /** This method launches the AddAppointment screen when the "Add" button is pressed. */
    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        AddAppointmentController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /** This method launches the UpdateAppointment screen when the "Update" button is pressed and sends the selected
     * appointment from the table to the UpdateAppointment screen by setting a static appointment member in that class.
     * If an appointment hasn't been selected from the appointment table then an error message will print to the screen
     * so the user knows they forgot to select an appointment to update and remains on the Appointments screen. */
    public void onActionUpdateAppointment(ActionEvent actionEvent) throws IOException {
        try{
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
        catch(Exception e) {
            deletedAppointmentConfirmation.setText("ERROR: Please select an appointment to update.");
        }
    }

    /** This method deletes an appointment that was selected from the table from the database. It then displays the
     * confirmation of and the specifics for the appointment that was "canceled".It then updates the appointment table
     * to display only the existing appointments left in the database. */
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

    /** This function displays all the appointments in the database in the appointments table when the user selects the
     * "All Time" radio button. */
    public void onActionDisplayAllAppointments(ActionEvent actionEvent) {
        // get observable list of all appointments
        try {
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This function displays only the appointments in the database that are occurring in the current month in the
     * appointments table when the user selects the "This Month" radio button. */
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

    /** This function displays only the appointments in the database that are occurring in the current week in the
     * appointments table when the user selects the "This Week" radio button. */
    public void onActionDisplayAppointmentsThisWeek(ActionEvent actionEvent) throws SQLException {
        // get observable list of appointments this week
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        ObservableList<Appointment> appointmentsThisWeek = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        int currentWeekday = currentDate.getDayOfWeek().getValue();
        // code below moves each day by 1 day because we don't have a <= for localDate values, only .isBefore or .isAfter
        LocalDate firstDayOfWeek = currentDate.minusDays(1);
        LocalDate lastDayOfWeek = currentDate.plusDays(1);

        // for loop sets the correct day before the first and day after the last day of the current week using a for loop
        for (int i = 0, j = 6; i < 7; i++, j--) {
            if ((currentWeekday % 7) == i) {
                firstDayOfWeek = currentDate.minusDays(i);
                lastDayOfWeek = currentDate.plusDays(j);
            }
        }

        /* for loop uses the day before the first day of the week and the day after the last day of the week to
         * determine if each date falls between those two days. If it does then it's added to the list of appointments
         * in the current week. */
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
