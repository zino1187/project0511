DROP TABLE pets;

CREATE TABLE pets(
	pets_id NUMBER PRIMARY KEY 
	, name varchar(20)
	, age NUMBER 
	, gender varchar(4)
);

CREATE SEQUENCE seq_pets
INCREMENT BY 1
START WITH 1;

INSERT INTO pets(pets_id, name, age,gender)
values(seq_pets.nextval,'카멜레온',3,'여');

INSERT INTO pets(pets_id, name, age,gender)
values(seq_pets.nextval,'아나콘다',9,'남');

INSERT INTO pets(pets_id, name, age,gender)
values(seq_pets.nextval,'이구아나',3,'여');

INSERT INTO pets(pets_id, name, age,gender)
values(seq_pets.nextval,'독거미',5,'여');

COMMIT;












