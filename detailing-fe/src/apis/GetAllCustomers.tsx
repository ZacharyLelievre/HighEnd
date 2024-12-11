import { CustomerModel } from "../models/dtos/CustomerModel";
import axiosInstance from "./axiosInstance";

export async function getAllCustomers(): Promise<CustomerModel[]>{

    const response = await axiosInstance.get(
        'customers', {
            responseType: 'json',
        }
    );
    return response.data
}