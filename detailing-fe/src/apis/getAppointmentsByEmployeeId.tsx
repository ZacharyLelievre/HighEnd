import axiosInstance from "./axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";

export async function getAppointmentsByEmployeeId(employeeId: string): Promise<AppointmentModel[]> {
    const response = await axiosInstance.get(`appointments/employee/${employeeId}`);
    return response.data;
}