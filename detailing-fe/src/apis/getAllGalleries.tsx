import { GalleryModel } from "../models/dtos/GalleryModel";
import axiosInstance from "./axiosInstance";

export async function getAllGalleries(): Promise<GalleryModel[]>{

    const response = await axiosInstance.get(
        'galleries', {
            responseType: 'json',

        });
    return response.data;
}

export{};