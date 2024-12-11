import {useState, useEffect} from 'react';
import { CustomerModel } from './dtos/CustomerModel';
import axios from "axios";
import "./AllCustomers.css";

export default function AllCustomers(): JSX.Element {

    const [customers, setCustomers] = useState<CustomerModel[]>([]);

    useEffect(() => {
        const fetchCustomers = async (): Promise<void> => {
            try {
                const response = await axios.get("http://localhost:8080/api/customers");
                setCustomers(response.data);
            } catch (error) {
                console.error("Error fetching appointments:", error);
            }
        };

        fetchCustomers();
    }, []);

    return (
        <div className='container'>
        <div className='customerList'>

            <h2 style = {{textAlign: "left" }}>Customers</h2>
            <p style = {{textAlign: "left"}}>Manage customers</p>

            {customers.map(customer =>(
                <div className='customer' key={customer.customerId}>
                    <h2 style={{textAlign: "left"}}>{customer.customerFirstName} {customer.customerLastName}</h2>
                    <p>Email: {customer.customerEmailAddress}</p>
                    <p>Address: {customer.streetAddress}, {customer.city}, {customer.postalCode}, {customer.province}, {customer.country}</p>

                </div>


            ))}


        </div>
        </div>
    )
}
