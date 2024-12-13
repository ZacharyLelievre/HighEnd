package com.example.highenddetailing.employeesubdomain.datalayer;


import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {

    @Test
    void testEqualsAndHashCode() {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setFirst_name("Jane");
        employee1.setLast_name("Smith");
        employee1.setPosition("Manager");
        employee1.setEmail("jane.smith@example.com");
        employee1.setSalary(75000.00);
        employee1.setImagePath("/images/employee1.jpg");

        Employee employee2 = new Employee();
        employee2.setId(1);
        employee2.setFirst_name("Jane");
        employee2.setLast_name("Smith");
        employee2.setPosition("Manager");
        employee2.setEmail("jane.smith@example.com");
        employee2.setSalary(75000.00);
        employee2.setImagePath("/images/employee1.jpg");

        assertEquals(employee1, employee2);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testSettersAndGetters() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirst_name("Jane");
        employee.setLast_name("Smith");
        employee.setPosition("Manager");
        employee.setEmail("jane.smith@example.com");
        employee.setSalary(75000.00);
        employee.setImagePath("/images/employee1.jpg");

        assertEquals(1, employee.getId());
        assertEquals("Jane", employee.getFirst_name());
        assertEquals("Smith", employee.getLast_name());
        assertEquals("Manager", employee.getPosition());
        assertEquals("jane.smith@example.com", employee.getEmail());
        assertEquals(75000.00, employee.getSalary());
        assertEquals("/images/employee1.jpg", employee.getImagePath());
    }

    @Test
    void testCanEqual() {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setFirst_name("Jane");
        employee1.setLast_name("Smith");

        Employee employee2 = new Employee();
        employee2.setId(1);
        employee2.setFirst_name("Jane");
        employee2.setLast_name("Smith");

        assertThat(employee1.equals(employee2)).isTrue();
        assertThat(employee1.equals(new Object())).isFalse();
    }

    @Test
    void testConstructorWithAllArgs() {
        Employee employee = new Employee(1, "Jane", "Smith", "Manager", "jane.smith@example.com", 75000.00, "/images/employee1.jpg");

        assertEquals(1, employee.getId());
        assertEquals("Jane", employee.getFirst_name());
        assertEquals("Smith", employee.getLast_name());
        assertEquals("Manager", employee.getPosition());
        assertEquals("jane.smith@example.com", employee.getEmail());
        assertEquals(75000.00, employee.getSalary());
        assertEquals("/images/employee1.jpg", employee.getImagePath());
    }

    @Test
    void testEmployeeBuilder() {
        Employee employee = Employee.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Smith")
                .position("Manager")
                .email("jane.smith@example.com")
                .salary(75000.00)
                .imagePath("/images/employee1.jpg")
                .build();

        assertEquals(1, employee.getId());
        assertEquals("Jane", employee.getFirst_name());
        assertEquals("Smith", employee.getLast_name());
        assertEquals("Manager", employee.getPosition());
        assertEquals("jane.smith@example.com", employee.getEmail());
        assertEquals(75000.00, employee.getSalary());
        assertEquals("/images/employee1.jpg", employee.getImagePath());
    }

    @Test
    void testEquals() {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setFirst_name("Jane");
        employee1.setLast_name("Smith");
        employee1.setPosition("Manager");

        Employee employee2 = new Employee();
        employee2.setId(1);
        employee2.setFirst_name("Jane");
        employee2.setLast_name("Smith");
        employee2.setPosition("Manager");

        assertThat(employee1).isEqualTo(employee2);
    }

    @Test
    void testHashCode() {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setFirst_name("Jane");
        employee1.setLast_name("Smith");
        employee1.setPosition("Manager");

        Employee employee2 = new Employee();
        employee2.setId(1);
        employee2.setFirst_name("Jane");
        employee2.setLast_name("Smith");
        employee2.setPosition("Manager");

        assertThat(employee1.hashCode()).isEqualTo(employee2.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirst_name("Jane");
        employee.setLast_name("Smith");
        employee.setPosition("Manager");
        employee.setEmail("jane.smith@example.com");
        employee.setSalary(75000.00);
        employee.setImagePath("/images/employee1.jpg");

        assertEquals(1, employee.getId());
        assertEquals("Jane", employee.getFirst_name());
        assertEquals("Smith", employee.getLast_name());
        assertEquals("Manager", employee.getPosition());
        assertEquals("jane.smith@example.com", employee.getEmail());
        assertEquals(75000.00, employee.getSalary());
        assertEquals("/images/employee1.jpg", employee.getImagePath());
    }
}