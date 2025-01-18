INSERT INTO services (id, service_id, service_name, time_required, price, image_path)
VALUES
    (1, 'a1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Car Wash', '30 minutes', 15.99, 'detailing-service-1.jpg'),
    (2, 'e2c91c38-72db-4a85-92a2-d123d6ebff15', 'Oil Change', '45 minutes', 35.50, 'detailing-service-1.jpg'),
    (3, 'f3a07e17-3f1d-4578-8c75-cd8c89f8c231', 'Interior Cleaning', '1 hour', 25.00, 'detailing-service-1.jpg'),
    (4, 'd4b25a88-88cf-46ea-8d93-31bb5e528a8f', 'Tire Replacement', '2 hours', 150.00, 'detailing-service-1.jpg'),
    (5, 'b5c64e99-9ac4-4f93-bb52-5c9ab7832e12', 'Brake Check', '45 minutes', 50.00, 'detailing-service-1.jpg'),
    (6, 'c6a87f2b-e3b5-4b8d-b48c-4824bb8d62e7', 'Engine Tuning', '3 hours', 200.75, 'detailing-service-1.jpg'),
    (7, 'a7d29e31-92fa-471f-9e42-321c79a6bf24', 'Battery Replacement', '1 hour', 85.25, 'detailing-service-1.jpg'),
    (8, 'b8e56f74-71d2-4d76-84f8-4458ba4f8f92', 'Wheel Alignment', '1.5 hours', 60.00, 'detailing-service-1.jpg'),
    (9, 'c9f98a15-5ecb-4979-a10e-bf3f6c31a1f6', 'Air Conditioning Service', '2 hours', 120.50, 'detailing-service-1.jpg'),
    (10, 'd0e12b65-4f9c-4a1b-832c-249eb1b6dc53', 'Headlight Restoration', '30 minutes', 20.99, 'detailing-service-1.jpg');


-- INSERT INTO appointments (id, appointment_id, customer_id, customer_name, service_id, service_name, employee_id, employee_name, appointment_date, appointment_time, appointment_end_time, status, image_path)
-- VALUES
--     (1, 'a1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'c1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'John Doe', 'a1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Car Wash', 'e1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Jane Smith', '2025-07-01', '10:00:00', '11:00:00', 'PENDING', 'detailing-service-1.jpg'),
--     (2, 'b1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'c1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'John Doe', 'b5c64e99-9ac4-4f93-bb52-5c9ab7832e12', 'Brake Check', 'e1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Jane Smith', '2025-07-02', '11:00:00','12:00:00', 'PENDING', 'detailing-service-1.jpg'),
--     (3, 'c1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'c1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'John Doe', 'c6a87f2b-e3b5-4b8d-b48c-4824bb8d62e7', 'Engine Tuning', 'e1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Jane Smith', '2025-07-03', '12:00:00', '13:00:00', 'PENDING', 'detailing-service-1.jpg');


INSERT INTO appointments(id, appointment_id, appointment_date, appointment_time, appointment_end_time, service_id, service_name, customer_id, customer_name, employee_id, employee_name, status, image_path)
VALUES
    (1, 'a1f14c90-ec5e-4f82-a9b7-2548a7325b34','2025-07-01', '10:00:00', '11:00:00', 'a1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Car Wash', 'c1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'John Doe', 'e1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Jane Smith', 'PENDING', 'detailing-service-1.jpg'),
    (2, 'b1f14c90-ec5e-4f82-a9b7-2548a7325b34','2025-07-02', '11:00:00', '12:00:00', 'b5c64e99-9ac4-4f93-bb52-5c9ab7832e12', 'Brake Check', 'c1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'John Doe', 'e1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Jane Smith', 'PENDING', 'detailing-service-1.jpg'),
    (3, 'a1f14c90-ec5e-4f82-a9b7-2548a7325b34','2025-07-03', '12:00:00', '13:00:00', 'c6a87f2b-e3b5-4b8d-b48c-4824bb8d62e7', 'Engine Tuning', 'c1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'John Doe', 'e1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Jane Smith', 'PENDING', 'detailing-service-1.jpg');




INSERT INTO galleries (gallery_id, description, image_url) VALUES
                                                               ('1b4e28ba-2fa1-11d2-883f-0016d3cca427', 'Interior Detailing', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('2c3f19cb-3fb2-12d3-984f-0016d3ccbc12', 'Exterior Waxing', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('3d4a20dc-4fc3-13e4-a94f-0016d3ccdd34', 'Paint Correction', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('4e5b31ed-5fd4-14f5-ba5f-0016d3ccee56', 'Engine Bay Cleaning', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('5f6c42fe-6fe5-15f6-cb6f-0016d3ccff78', 'Ceramic Coating', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('0ce12823-e016-4813-ab6b-092fca25a17b', 'Commercial Cleaning', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('a2b871c2-696c-4c7e-a8da-f7226aa3f648', 'Post-Construction Cleaning', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('0658e36b-4fc1-47c8-92cd-44d3bb865b90', 'Carpet Cleaning', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('1126789a-9cda-4374-a040-a3d4dc6187b6', 'Janitorial Services', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg'),
                                                               ('685612e3-0414-42fc-8312-01b40d0dd764', 'Disinfection and Sanitization Services', 'https://business.yelp.com/wp-content/uploads/2022/07/lp-ban-car-detailer-wiping-the-headlight-of-a-car.jpeg');

INSERT INTO employees (id, employee_id, first_name, last_name, position, email, phone, salary, image_path)
VALUES
    (1, 'e1f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Jane', 'Smith', 'Manager', 'jane.smith@example.com', '438 333 9999',75000.00,'profile.png'),
    (2, 'e2f14c90-ec5e-4f82-a9b7-2548a7325b34', 'John', 'Doe', 'Technician', 'john.doe@example.com', '438 333 9999',55000.00,'profile.png'),
    (3, 'e3f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Emily', 'Clark', 'Technician', 'emily.clark@example.com', '438 333 8888', 52000.00,'profile.png'),
    (4, 'e4f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Michael', 'Brown', 'Supervisor', 'michael.brown@example.com', '438 333 7777', 68000.00,'profile.png'),
    (5, 'e5f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Sarah', 'Davis', 'Technician', 'sarah.davis@example.com','438 333 6666', 51000.00,'profile.png'),
    (6, 'e6f14c90-ec5e-4f82-a9b7-2548a7325b34', 'David', 'Wilson', 'Customer Support', 'david.wilson@example.com','438 333 5555', 48000.00,'profile.png'),
    (7, 'e7f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Sophia', 'Taylor', 'Technician', 'sophia.taylor@example.com','438 333 4444', 53000.00,'profile.png'),
    (8, 'e8f14c90-ec5e-4f82-a9b7-2548a7325b34', 'James', 'Moore', 'Technician', 'james.moore@example.com', '438 333 3333',54000.00,'profile.png'),
    (9, 'e9f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Olivia', 'Anderson', 'Receptionist', 'olivia.anderson@example.com','438 333 2222', 42000.00,'profile.png'),
    (10, 'e0f14c90-ec5e-4f82-a9b7-2548a7325b34', 'Ethan', 'Jackson', 'Technician', 'ethan.jackson@example.com', '438 333 1111',53000.00,'profile.png');

INSERT INTO customers (customer_id, customer_first_name, customer_last_name, customer_email_address, street_address, city, postal_code, province, country)
VALUES
    ('auth0|6733c3d5b4ef3c052b0dd6f6', 'John', 'Doe', 'john.doe@example.com', '123 Main St', 'Toronto', 'M5A 1A1', 'Ontario', 'Canada'),
    ('d50f6f2d-cb85-428d-89e4-3f29db9db987', 'Jane', 'Smith', 'jane.smith@example.com', '456 Elm St', 'Vancouver', 'V6B 2B3', 'British Columbia', 'Canada'),
    ('c1b2ad74-3f57-4d52-8d25-767de1d4bb19', 'Alice', 'Johnson', 'alice.johnson@example.com', '789 Oak St', 'Montreal', 'H3A 1A4', 'Quebec', 'Canada'),
    ('f72c6438-b8bb-4328-8de8-f4a6dcb33742', 'Bob', 'Brown', 'bob.brown@example.com', '101 Pine St', 'Calgary', 'T2P 2N3', 'Alberta', 'Canada'),
    ('a5b9d2f8-14b8-4183-b1fa-c6d6dbf1a3b4', 'Charlie', 'Davis', 'charlie.davis@example.com', '202 Birch St', 'Ottawa', 'K1A 0B1', 'Ontario', 'Canada'),
    ('e5acb8d1-5670-4621-9d74-e345db80b396', 'David', 'Miller', 'david.miller@example.com', '303 Cedar St', 'Quebec City', 'G1R 1B2', 'Quebec', 'Canada'),
    ('f1adf1cd-84c4-4854-90d2-0a4c1bdf8a2c', 'Emily', 'Wilson', 'emily.wilson@example.com', '404 Maple St', 'Edmonton', 'T5J 2N6', 'Alberta', 'Canada'),
    ('d9ab8e70-b35d-4294-8b87-06c12faad8a9', 'Frank', 'Moore', 'frank.moore@example.com', '505 Fir St', 'Halifax', 'B3J 3M3', 'Nova Scotia', 'Canada'),
    ('b1a8b9f4-5ab7-40fb-8f68-b7e0d60178b0', 'Grace', 'Taylor', 'grace.taylor@example.com', '606 Pine Ave', 'Winnipeg', 'R3C 3B2', 'Manitoba', 'Canada'),
    ('88a989e0-b9d3-4728-9c3f-cd845a87baba', 'Henry', 'Anderson', 'henry.anderson@example.com', '707 Willow Rd', 'Saskatoon', 'S7K 1C2', 'Saskatchewan', 'Canada');



