// src/apis/getAppointmentsByDate.ts
import axiosInstance from "./axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";

export async function getAppointmentsByDate(
  date: string,
): Promise<AppointmentModel[]> {
  const response = await axiosInstance.get(
    `appointments/date-appointments?date=${date}`,
  );
  return response.data;
}
