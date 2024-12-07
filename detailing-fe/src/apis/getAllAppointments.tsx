import axiosInstance from "./axiosInstance";
import {AppointmentModel} from "../models/dtos/AppointmentModel";

export async function getAllAppointments(): Promise<AppointmentModel[]> {
    const response = await axiosInstance.get(
        'appointments', {
            responseType: 'json',
        });
    return response.data;
}