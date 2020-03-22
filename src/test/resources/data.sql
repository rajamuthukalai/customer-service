DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  id IDENTITY AUTO_INCREMENT  PRIMARY KEY,
  firstname VARCHAR(250) NOT NULL,
  lastname VARCHAR(250) NOT NULL
);

INSERT INTO customer (firstname, lastname) VALUES ('Raja', 'Muthukalai');
--INSERT INTO customer (firstname, lastname) VALUES ('Sharmila', 'Periyaswamy');
--INSERT INTO customer (firstname, lastname) VALUES ('Sannutha', 'Raja');