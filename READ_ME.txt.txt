Title: Organization Scheduler

Purpose: This application is intended to function as a scheduling desktop application for a global company. It connects to a MySQL database that holds company appointment, user, contact, customer, and location data. This application allows the user to update, add, and delete both customers and appointments, view contact schedules, and view other customer data reports.

Author: Faith Schwinger

Contact Information: fschwi2@student.wgu.edu

Student Application Version: 1.0

Date: March 28, 2022

IDE: IntelliJ IDEA 2021.3.2 (Community Edition)

JDK Version: Java SE 17.0.1

JavaFX Version: JavaFX-SDK-17.0.1

Directions: To use this application, the user must first enter a valid username and password combination into the login screen. Upon a valid entry, the user will be directed to a main menu. There will also be a pop-up alert notifying if there is an upcoming appointment in the next 15 minutes or not.

This main menu has four buttons. Each button goes to a new screen with a specific function as explained below.

The first button directs the user to the main screen for viewing customer information. From this page there are buttons allowing a user to create new customers, update existing ones, or delete customers and their appointments.

The second button directs the user to the main screen for viewing appointment information. From this page there are buttons allowing a user to create new appointments and update or delete existing ones. 

The third button directs the user to a screen where the company's contacts schedules can be viewed. Select a contact name from the combobox at the top and the table will show all of that contact's appointments.

The fourth button directs the user to a screen where two different customer reports can be viewed. For the first report, select an appointment type and a month from each combobox and the total number of appointments with that type occuring in that month will be displayed to the screen. For the second report, select a month from the combobox on the lower half of the screen and a number will be printed to the right of it showing the percentage of customers in the database that have scheduled appointments in that month.

Each of these four pages has a "return to main menu" button, allowing the user to go back to the main screen and choose between these four options at any point in time.

Additional Report for A3f: The additional report I chose to display can be found by clicking the fourth button on the main menu "customer reports." It shows the user the percentage of customers in the database that have scheduled appointments in a month selected in the combobox. This report can allow the company to see how often their customers are scheduling appointments and if they have any months with low customer engagement. It also can help the company keep track of this percentage as they work to improve the percentage and therefore improve customer engagement.

MySQL Connector Driver Version: mysql-connector-java-8.0.25