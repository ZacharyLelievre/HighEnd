import {AvailabilityModel} from "./AvailabilityModel";

export interface EmployeeModel {
  employeeId: string;
  first_name: string;
  last_name: string;
  position: string;
  email: string;
  phone: string;
  salary: number;
  imagePath: string;
  availability: AvailabilityModel[];
}
