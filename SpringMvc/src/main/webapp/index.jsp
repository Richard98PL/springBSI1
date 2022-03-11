<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Password Wallet</title>
   </head>
   <body>
      <div>
         <h3>Password Wallet</h3>
         <p>
            <a href="registerForm">Register</a><br />
            <a href="loginForm">Login</a><br />
         </p>
      </div>
   </body>
</html>

<!-- comment
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(30) NULL,
  `password_hash` VARCHAR(512) NULL,
  `salt` VARCHAR(20) NULL,
  `isPasswordKeptAsHash` TINYINT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-->

<!-- comment
   CREATE TABLE `password` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `password_hash` VARCHAR(256) NULL,
  `web_address` VARCHAR(256) NULL,
  `description` VARCHAR(256) NULL,
  `login` VARCHAR(30) NULL,
  `id_user` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_id_user`
    FOREIGN KEY (`id_user`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
-->

<!-- 

select * from `user` left outer join `password` on `user`.`id` = `password`.`id_user`;

-->

