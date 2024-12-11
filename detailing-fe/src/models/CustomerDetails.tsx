import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {CustomerModel} from "./dtos/CustomerModel";
import axios from "axios";


export default function CustomerDetails(): JSX.Element {
    const {customerId} = useParams<{customerId: string}>();
    const [customer, setCustomer] = useState<CustomerModel | null>(null);

    useEffect(() => {
        const fetchCustomerDetails = async (): Promise<void> => {
            try {
                const response = await axios.get(`http://localhost:8080/api/customers/${customerId}`);
                setCustomer(response.data);
            } catch (error) {
                console.error("Error fetching customer details: ", error)
            }
        };
        fetchCustomerDetails()
    }, [customerId]);

    if (!customer) {
        return <div>Loading...</div>
    }

    return (
        <p>
            {customer.customerFirstName}
        </p>
    )
}