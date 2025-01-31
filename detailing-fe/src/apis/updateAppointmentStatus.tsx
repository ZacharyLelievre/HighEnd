import axiosInstance from "./axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";

export async function updateAppointmentStatus(
    id: string,
    newStatus: string
): Promise<AppointmentModel> {
    const response = await axiosInstance.put(`appointments/${id}/status`, {
        status: newStatus
    });
    return response.data; // single updated appointment
}
