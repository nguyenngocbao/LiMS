MERGE INTO `role` KEY(id) VALUES (1,'ADMIN'),(2,'TEACHER'),(3,'USER');
MERGE INTO `user` (id, account_non_expired,account_non_locked,credentials_non_expired,enabled,password,username,email,path_images,full_name) 
KEY(id) VALUES (1,1,1,1,1,'$2a$10$v4w9Ag.9pQPM9a1XywfAjeOPqCsBVDiAkxNM18XY76WJEpIgQXWNu','usernormal','user@tma.com.vn', '', 'usernormal')
,(2,1,1,1,1,'$2a$10$/j3zu.aWXXuBmTKCZgGX9O2xs8Ho7S3c2tGCoySEpBOqvBHMtCvKG','admin','admin@tma.com.vn', '','admin');
MERGE INTO `user_roles` KEY(user_id, role_id) VALUES (2,1),(1,3);

MERGE INTO `category` KEY (id, name) VALUES (1, 'Truyen');