package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppointmentResponseModelTest {

    @Test
    void testAppointmentResponseModelBuilder() {
        // Arrange
        String appointmentId = "A001";
        String customerId = "C001";
        String customerName = "John Doe"; // New field
        String serviceId = "S001";
        String serviceName = "Car Wash"; // New field
        String employeeId = "E001";
        String employeeName = "Jane Smith"; // New field
        String appointmentDate = "2021-12-31";
        String appointmentTime = "10:00 AM";
        Status status = Status.PENDING;
        String imagePath = "/images/detailing.jpg";

        // Act
        AppointmentResponseModel responseModel = AppointmentResponseModel.builder()
                .appointmentId(appointmentId)
                .customerId(customerId)
                .customerName(customerName) // New field
                .serviceId(serviceId)
                .serviceName(serviceName) // New field
                .employeeId(employeeId)
                .employeeName(employeeName) // New field
                .appointmentDate(appointmentDate)
                .appointmentTime(appointmentTime)
                .status(Status.PENDING)
                .imagePath(imagePath)
                .build();

        // Assert
        assertEquals(responseModel.getAppointmentId(), appointmentId);
        assertEquals(responseModel.getCustomerId(), customerId);
        assertEquals(responseModel.getCustomerName(), customerName); // New field
        assertEquals(responseModel.getServiceId(), serviceId);
        assertEquals(responseModel.getServiceName(), serviceName); // New field
        assertEquals(responseModel.getEmployeeId(), employeeId);
        assertEquals(responseModel.getEmployeeName(), employeeName); // New field
        assertEquals(responseModel.getAppointmentDate(), appointmentDate);
        assertEquals(responseModel.getAppointmentTime(), appointmentTime);
        assertEquals(responseModel.getStatus(), status);
        assertEquals(responseModel.getImagePath(), imagePath);
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        AppointmentResponseModel responseModel = new AppointmentResponseModel();

        // Assert
        assertThat(responseModel.getAppointmentId()).isNull();
        assertThat(responseModel.getCustomerId()).isNull();
        assertThat(responseModel.getCustomerName()).isNull(); // New field
        assertThat(responseModel.getServiceId()).isNull();
        assertThat(responseModel.getServiceName()).isNull(); // New field
        assertThat(responseModel.getEmployeeId()).isNull();
        assertThat(responseModel.getEmployeeName()).isNull(); // New field
        assertThat(responseModel.getAppointmentDate()).isNull();
        assertThat(responseModel.getAppointmentTime()).isNull();
        assertThat(responseModel.getStatus()).isNull();
        assertThat(responseModel.getImagePath()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String appointmentId = "A001";
        String appointmentDate = "2021-12-31";
        String appointmentTime = "10:00 AM";
        String appointmentEndTime = "11:00 AM";
        String serviceId = "S001";
        String serviceName = "Car Wash"; // New field
        String customerId = "C001";
        String customerName = "John Doe"; // New field
        String employeeId = "E001";
        String employeeName = "Jane Smith"; // New field
        Status status = Status.PENDING;
        String imagePath = "/images/detailing.jpg";

        // Act
        AppointmentResponseModel responseModel = new AppointmentResponseModel(appointmentId, appointmentDate, appointmentTime,appointmentEndTime, serviceId, serviceName, customerId, customerName, employeeId, employeeName, Status.PENDING, imagePath);

        // Assert
        assertEquals(responseModel.getAppointmentId(), appointmentId);
        assertEquals(responseModel.getAppointmentDate(), appointmentDate);
        assertEquals(responseModel.getAppointmentTime(), appointmentTime);
        assertEquals(responseModel.getServiceId(), serviceId);
        assertEquals(responseModel.getServiceName(), serviceName); // New field
        assertEquals(responseModel.getCustomerId(), customerId);
        assertEquals(responseModel.getCustomerName(), customerName); // New field
        assertEquals(responseModel.getEmployeeId(), employeeId);
        assertEquals(responseModel.getEmployeeName(), employeeName); // New field
        assertEquals(responseModel.getStatus(), status);
        assertEquals(responseModel.getImagePath(), imagePath);
    }

    @Test
    void testSetters() {
        // Arrange
        AppointmentResponseModel model = new AppointmentResponseModel();

        // Act
        model.setAppointmentId("A001");
        model.setCustomerId("C001");
        model.setCustomerName("John Doe"); // New field
        model.setServiceId("S001");
        model.setServiceName("Car Wash"); // New field
        model.setEmployeeId("E001");
        model.setEmployeeName("Jane Smith"); // New field
        model.setAppointmentDate("2021-12-31");
        model.setAppointmentTime("10:00 AM");
        model.setStatus(Status.PENDING);
        model.setImagePath("/images/detailing.jpg");

        // Assert
        assertThat(model.getCustomerName()).isEqualTo("John Doe"); // New field
        assertThat(model.getServiceName()).isEqualTo("Car Wash"); // New field
        assertThat(model.getEmployeeName()).isEqualTo("Jane Smith"); // New field
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        AppointmentResponseModel model1 = new AppointmentResponseModel("A001", "2021-12-31", "10:00 AM","11:00 AM", "S001", "Car Wash", "C001", "John Doe", "E001", "Jane Smith", Status.PENDING, "/images/detailing.jpg");
        AppointmentResponseModel model2 = new AppointmentResponseModel("A001", "2021-12-31", "10:00 AM","11:00 AM", "S001", "Car Wash", "C001", "John Doe", "E001", "Jane Smith", Status.PENDING, "/images/detailing.jpg");
        AppointmentResponseModel model3 = new AppointmentResponseModel("A002", "2022-01-01", "11:00 AM", "12:00 AM", "S002", "Brake Check", "C002", "Mary Jane", "E002", "John Doe", Status.CONFIRMED, "/images/cleaning.jpg");

        // Act & Assert
        assertThat(model1).isEqualTo(model2);
        assertThat(model1.hashCode()).isEqualTo(model2.hashCode());
        assertThat(model1).isNotEqualTo(model3);
        assertThat(model1.hashCode()).isNotEqualTo(model3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        AppointmentResponseModel.AppointmentResponseModelBuilder builder = AppointmentResponseModel.builder();
        builder.appointmentId("A001")
                .customerId("C001")
                .customerName("John Doe") // New field
                .serviceId("S001")
                .serviceName("Car Wash") // New field
                .employeeId("E001")
                .employeeName("Jane Smith") // New field
                .appointmentDate("2021-12-31")
                .appointmentTime("10:00 AM")
                .status(Status.PENDING)
                .imagePath("/images/detailing.jpg");

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "customerName=John Doe", // New field
                "serviceName=Car Wash", // New field
                "employeeName=Jane Smith" // New field
        );
    }

    @Test
    void testCanEqual() {
        AppointmentResponseModel model1 = new AppointmentResponseModel("A001", "2021-12-31", "10:00 AM", "11:00 AM", "S001", "Car Wash", "C001", "John Doe", "E001", "Jane Smith", Status.PENDING, "/images/detailing.jpg");
        AppointmentResponseModel model2 = new AppointmentResponseModel("A001", "2021-12-31", "10:00 AM", "11:00 AM", "S001", "Car Wash", "C001", "John Doe", "E001", "Jane Smith", Status.PENDING, "/images/detailing.jpg");

        assertThat(model1.equals(model2)).isTrue();
        assertThat(model1.equals(new Object())).isFalse();
    }
    @Test
    void testBuild() {
        // Arrange
        AppointmentResponseModel.AppointmentResponseModelBuilder builder = AppointmentResponseModel.builder();
        builder.appointmentId("A001")
                .customerId("C001")
                .customerName("John Doe") // New field
                .serviceId("S001")
                .serviceName("Car Wash") // New field
                .employeeId("E001")
                .employeeName("Jane Smith") // New field
                .appointmentDate("2021-12-31")
                .appointmentTime("10:00 AM")
                .status(Status.PENDING)
                .imagePath("/images/detailing.jpg");

        // Act
        AppointmentResponseModel model = builder.build();

        // Assert
        assertThat(model.getAppointmentId()).isEqualTo("A001");
        assertThat(model.getCustomerId()).isEqualTo("C001");
        assertThat(model.getCustomerName()).isEqualTo("John Doe"); // New field
        assertThat(model.getServiceId()).isEqualTo("S001");
        assertThat(model.getServiceName()).isEqualTo("Car Wash"); // New field
        assertThat(model.getEmployeeId()).isEqualTo("E001");
        assertThat(model.getEmployeeName()).isEqualTo("Jane Smith"); // New field
        assertThat(model.getAppointmentDate()).isEqualTo("2021-12-31");
        assertThat(model.getAppointmentTime()).isEqualTo("10:00 AM");
        assertThat(model.getStatus()).isEqualTo(Status.PENDING);
        assertThat(model.getImagePath()).isEqualTo("/images/detailing.jpg");
    }
}