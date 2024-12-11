package com.example.highenddetailing.empployeessubdomain.mapperlayer;

import com.example.highenddetailing.empployeessubdomain.datalayer.Employee;
import com.example.highenddetailing.empployeessubdomain.presentationlayer.EmployeeResponseModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-11T13:31:28-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class EmployeeResponseMapperImpl implements EmployeeResponseMapper {

    @Override
    public EmployeeResponseModel entityToResponseModel(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeResponseModel.EmployeeResponseModelBuilder employeeResponseModel = EmployeeResponseModel.builder();

        employeeResponseModel.employeeId( employee.getEmployeeIdentifier().getEmployeeId() );
        employeeResponseModel.first_name( employee.getFirst_name() );
        employeeResponseModel.last_name( employee.getLast_name() );
        employeeResponseModel.position( employee.getPosition() );
        employeeResponseModel.email( employee.getEmail() );
        employeeResponseModel.salary( employee.getSalary() );

        return employeeResponseModel.build();
    }

    @Override
    public List<EmployeeResponseModel> entityListToResponseModel(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeResponseModel> list = new ArrayList<EmployeeResponseModel>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( entityToResponseModel( employee ) );
        }

        return list;
    }
}
