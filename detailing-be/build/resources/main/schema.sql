DROP TABLE IF EXISTS services;
CREATE TABLE IF NOT EXISTS services (
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        service_id VARCHAR(36) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    time_required VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image_path VARCHAR(255)
    );
