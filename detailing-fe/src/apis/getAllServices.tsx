import { ServiceModel } from "../models/dtos/ServiceModel";
import axiosInstance from "./axiosInstance";

export async function getAllServices(): Promise<ServiceModel[]> {

    const response = await axiosInstance.get(
        'services', {
            responseType: 'json',
        });
    return response.data;
}