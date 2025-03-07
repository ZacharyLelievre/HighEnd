CREATE TABLE IF NOT EXISTS services (
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        service_id VARCHAR(36) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    time_required VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image_path VARCHAR(255)
    );


CREATE TABLE IF NOT EXISTS appointments (
                                            id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            appointment_id VARCHAR(36) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    appointment_end_time TIME NOT NULL,
    service_id VARCHAR(36) NOT NULL,
    service_name VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    customer_name VARCHAR(50) NOT NULL,
    employee_id VARCHAR(36) NOT NULL,
    employee_name VARCHAR(36) NOT NULL,
    status VARCHAR(10),
    image_path VARCHAR(50)

    );

CREATE TABLE IF NOT EXISTS galleries (
                                         id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         gallery_id VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL
    );




CREATE TABLE IF NOT EXISTS employees (
    employee_id VARCHAR(36) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    position VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(32) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    image_path VARCHAR(255),
    PRIMARY KEY (employee_id)
    );

-- Recreate employee_availability with FK on employees(employee_id)
CREATE TABLE IF NOT EXISTS employee_availability (
                                                     id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                     employee_id VARCHAR(36) NOT NULL,                 -- This matches the PK in employees
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS customers(
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        customer_id VARCHAR(50) UNIQUE,
    customer_first_name VARCHAR(50),
    customer_last_name VARCHAR(50),
    customer_email_address VARCHAR(50),
    street_address VARCHAR (50),
    city VARCHAR (50),
    postal_code VARCHAR (9),
    province VARCHAR (50),
    country VARCHAR (50)
    );
CREATE TABLE IF NOT EXISTS employee_invites (
                                                id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                invite_token VARCHAR(255) NOT NULL,
                                                expires_at DATETIME NOT NULL
);
CREATE TABLE IF NOT EXISTS promotions (
                                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          service_id_fk INTEGER,
                                          old_price DECIMAL(10,2),
    new_price DECIMAL(10,2),
    discount_message VARCHAR(255),
    FOREIGN KEY (service_id_fk) REFERENCES services(id)
    );

