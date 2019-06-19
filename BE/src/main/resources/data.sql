LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT IGNORE INTO `role` VALUES (1,'ADMIN'),(2,'TEACHER'),(3,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*INSERT IGNORE INTO `user` VALUES (1,'','','','','C','','$2a$10$v4w9Ag.9pQPM9a1XywfAjeOPqCsBVDiAkxNM18XY76WJEpIgQXWNu','test'),(2,'','','','Java','','$2a$10$/j3zu.aWXXuBmTKCZgGX9O2xs8Ho7S3c2tGCoySEpBOqvBHMtCvKG','admin');*/
INSERT IGNORE INTO `user`(id, account_non_expired,account_non_locked,credentials_non_expired,enabled,password,username,email,path_images, full_name)
VALUES (1,'','','','','$2a$10$v4w9Ag.9pQPM9a1XywfAjeOPqCsBVDiAkxNM18XY76WJEpIgQXWNu','test','test@gmail.com', '', 'Nguyen Van A')
,(2,'','','','','$2a$10$/j3zu.aWXXuBmTKCZgGX9O2xs8Ho7S3c2tGCoySEpBOqvBHMtCvKG','admin','admin@gmail.com', '',  'Tran Thi B'),
(3,'','','','','$2a$10$/j3zu.aWXXuBmTKCZgGX9O2xs8Ho7S3c2tGCoySEpBOqvBHMtCvKG','nngocbao','nngocbao@gmail.com', '',  'Nguyen Ngoc Bao');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT IGNORE INTO `user_roles` VALUES (2,1),(1,2),(3,3);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
