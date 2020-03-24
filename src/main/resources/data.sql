INSERT INTO roles(id,name) VALUES(1,'ROLE_ADMIN');
INSERT INTO roles(id,name) VALUES(2,'ROLE_CLINIC');
INSERT INTO roles(id,name) VALUES(3,'ROLE_PATIENT');

INSERT INTO users(id,username,password,role_id) VALUES(123,'user1','$2a$10$ITvO27KCIWdSV7rMSennX.xZWpqzX0Mrgwr4C4PwqgeGW/eCTdSYe',3);
INSERT INTO users(id,username,password,role_id) VALUES(124,'clinic1','$2a$10$ITvO27KCIWdSV7rMSennX.xZWpqzX0Mrgwr4C4PwqgeGW/eCTdSYe',2);
INSERT INTO patients(id,name, user_id) VALUES(134, 'Thien Thao',123);
INSERT INTO clinics(id, name, address, phone, user_id) VALUES (1004, 'Nha Khoa Lan Anh', '34 Ky Dong Quan 3', '090219390123', 124);
INSERT INTO clinic_services(id, name, price, clinic_id) VALUES (1234, 'tram rang sieu xin', 300000, 1004);
INSERT INTO clinic_services(id, name, price, clinic_id) VALUES (1235, 'nho rang sieu xin', 500000, 1004);
INSERT INTO bookings(id, code, patient_id, status, service_id) VALUES(167, 'DC12392', 134, 'IN_PROGRESS', 1235);
INSERT INTO bookings(id, code, patient_id, status, service_id) VALUES(168, 'DC12393', 134, 'COMPLETED', 1234);
INSERT INTO bookings(id, code, patient_id, status, service_id) VALUES(169, 'DC12394',134, 'CANCELED', 1234);
# INSERT INTO roles(id,name) VALUES(2,'ROLE_CLINIC');
# INSERT INTO roles(id,name) VALUES(3,'ROLE_PATIENT');