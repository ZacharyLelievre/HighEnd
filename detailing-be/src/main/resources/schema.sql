DROP TABLE IF EXISTS services;
CREATE TABLE IF NOT EXISTS services (
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        service_id VARCHAR(36) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    time_required VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image_path VARCHAR(255)
    );


DROP TABLE IF EXISTS appointments;
CREATE TABLE IF NOT EXISTS appointments (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    appointment_id VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    service_id VARCHAR(36) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    employee_id VARCHAR(36) NOT NULL,
    employee_name VARCHAR(255) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    image_path VARCHAR(255)
);