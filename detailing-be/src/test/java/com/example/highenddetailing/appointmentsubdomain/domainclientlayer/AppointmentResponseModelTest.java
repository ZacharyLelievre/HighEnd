package com.example.highenddetailing.appointmentsubdomain.domainclientlayer;

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
        String serviceId = "S001";
        String employeeId = "E001";
        String appointmentDate = "2021-12-31";
        String appointmentTime = "10:00 AM";
        String status = "Pending";
        String imagePath = "/images/detailing.jpg";

        // Act
        AppointmentResponseModel responseModel = AppointmentResponseModel.builder()
                .appointmentId(appointmentId)
                .customerId(customerId)
                .serviceId(serviceId)
                .employeeId(employeeId)
                .appointmentDate(appointmentDate)
                .appointmentTime(appointmentTime)
                .status(status)
                .imagePath(imagePath)
                .build();

        // Assert
        assertEquals(responseModel.getAppointmentId(), appointmentId);
        assertEquals(responseModel.getCustomerId(), customerId);
        assertEquals(responseModel.getServiceId(), serviceId);
        assertEquals(responseModel.getEmployeeId(), employeeId);
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
        assertThat(responseModel.getServiceId()).isNull();
        assertThat(responseModel.getEmployeeId()).isNull();
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
        String serviceId = "S001";
        String customerId = "C001";
        String employeeId = "E001";
        String status = "Pending";
        String imagePath = "/images/detailing.jpg";

        // Act
        AppointmentResponseModel responseModel = new AppointmentResponseModel(appointmentId, appointmentDate, appointmentTime,serviceId,customerId,employeeId, status, imagePath);

        // Assert
        assertEquals(responseModel.getAppointmentId(), appointmentId);
        assertEquals(responseModel.getAppointmentDate(), appointmentDate);
        assertEquals(responseModel.getAppointmentTime(), appointmentTime);
        assertEquals(responseModel.getServiceId(), serviceId);
        assertEquals(responseModel.getCustomerId(), customerId);
        assertEquals(responseModel.getEmployeeId(), employeeId);
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
        model.setServiceId("S001");
        model.setEmployeeId("E001");
        model.setAppointmentDate("2021-12-31");
        model.setAppointmentTime("10:00 AM");
        model.setStatus("Pending");
        model.setImagePath("/images/detailing.jpg");

        // Assert
        assertThat(model.getAppointmentId()).isEqualTo("A001");
        assertThat(model.getCustomerId()).isEqualTo("C001");
        assertThat(model.getServiceId()).isEqualTo("S001");
        assertThat(model.getEmployeeId()).isEqualTo("E001");
        assertThat(model.getAppointmentDate()).isEqualTo("2021-12-31");
        assertThat(model.getAppointmentTime()).isEqualTo("10:00 AM");
        assertThat(model.getStatus()).isEqualTo("Pending");
        assertThat(model.getImagePath()).isEqualTo("/images/detailing.jpg");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        AppointmentResponseModel model1 = new AppointmentResponseModel("A001", "C001", "S001", "E001", "2021-12-31", "10:00 AM", "Pending", "/images/detailing.jpg");
        AppointmentResponseModel model2 = new AppointmentResponseModel("A001", "C001", "S001", "E001", "2021-12-31", "10:00 AM", "Pending", "/images/detailing.jpg");
        AppointmentResponseModel model3 = new AppointmentResponseModel("A002", "C002", "S002", "E002", "2022-01-01", "11:00 AM", "Completed", "/images/cleaning.jpg");

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
                .serviceId("S001")
                .employeeId("E001")
                .appointmentDate("2021-12-31")
                .appointmentTime("10:00 AM")
                .status("Pending")
                .imagePath("/images/detailing.jpg");

        // Act
        String toStringResult = builder.toString();

        // Assert
        assertThat(toStringResult).contains(
                "appointmentId=A001",
                "customerId=C001",
                "serviceId=S001",
                "employeeId=E001",
                "appointmentDate=2021-12-31",
                "appointmentTime=10:00 AM",
                "status=Pending",
                "imagePath=/images/detailing.jpg"
        );
    }

    @Test
    void testCanEqual() {
        // Arrange
        AppointmentResponseModel model1 = new AppointmentResponseModel("A001", "C001", "S001", "E001", "2021-12-31", "10:00 AM", "Pending", "/images/detailing.jpg");
        AppointmentResponseModel model2 = new AppointmentResponseModel("A001", "C001", "S001", "E001", "2021-12-31", "10:00 AM", "Pending", "/images/detailing.jpg");

        // Act & Assert
        assertThat(model1.equals(model2)).isTrue();
        assertThat(model1.equals(new Object())).isFalse();
    }

    @Test
    void testBuild() {
        // Arrange
        AppointmentResponseModel.AppointmentResponseModelBuilder builder = AppointmentResponseModel.builder();
        builder.appointmentId("A001")
                .customerId("C001")
                .serviceId("S001")
                .employeeId("E001")
                .appointmentDate("2021-12-31")
                .appointmentTime("10:00 AM")
                .status("Pending")
                .imagePath("/images/detailing.jpg");

        // Act
        AppointmentResponseModel model = builder.build();

        // Assert
        assertThat(model.getAppointmentId()).isEqualTo("A001");
        assertThat(model.getCustomerId()).isEqualTo("C001");
        assertThat(model.getServiceId()).isEqualTo("S001");
        assertThat(model.getEmployeeId()).isEqualTo("E001");
        assertThat(model.getAppointmentDate()).isEqualTo("2021-12-31");
        assertThat(model.getAppointmentTime()).isEqualTo("10:00 AM");
        assertThat(model.getStatus()).isEqualTo("Pending");
        assertThat(model.getImagePath()).isEqualTo("/images/detailing.jpg");
    }
}