CREATE TABLE IF NOT EXISTS vet (
  id  INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30)
) ;
CREATE INDEX vet_last_name ON vet (last_name);
  
CREATE TABLE IF NOT EXISTS speciality (
  id  INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
) ;
CREATE INDEX speciality_name ON speciality (name);

CREATE TABLE IF NOT EXISTS vet_speciality (
  vet_id INTEGER NOT NULL,
  specialty_id INTEGER NOT NULL
) ;
ALTER TABLE vet_speciality ADD CONSTRAINT fk_vet_speciality_vet FOREIGN KEY (vet_id) REFERENCES vet (id);
ALTER TABLE vet_speciality ADD CONSTRAINT fk_vet_speciality_speciality FOREIGN KEY (specialty_id) REFERENCES speciality (id);

CREATE TABLE IF NOT EXISTS pet_type (
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
) ;
CREATE INDEX pet_type_name ON pet_type (name);

CREATE TABLE IF NOT EXISTS owner (
  id INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  created_at DATE,
  phone VARCHAR(20)
) ;
CREATE INDEX owner_last_name ON owner (last_name);

CREATE TABLE IF NOT EXISTS pet (
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30),
  birthday_date DATE,
  owner_id INTEGER NOT NULL,
  pet_type_id INTEGER NOT NULL
) ;
ALTER TABLE pet ADD CONSTRAINT fk_pet_owner FOREIGN KEY (owner_id) REFERENCES owner (id);
ALTER TABLE pet ADD CONSTRAINT fk_pet_type FOREIGN KEY (pet_type_id) REFERENCES pet_type (id);
CREATE INDEX pet_name ON pet (name);

CREATE TABLE IF NOT EXISTS visit (
  id INTEGER IDENTITY PRIMARY KEY,
  visit_date DATE,
  description VARCHAR(255),
  pet_id INTEGER NOT NULL
);
ALTER TABLE visit ADD CONSTRAINT fk_visit_pet FOREIGN KEY (pet_id) REFERENCES pet (id);
CREATE INDEX visit_pet_id ON visit (pet_id);
