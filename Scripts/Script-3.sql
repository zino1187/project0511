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
values(seq_pets.nextval,'ī�᷹��',3,'��');

INSERT INTO pets(pets_id, name, age,gender)
values(seq_pets.nextval,'�Ƴ��ܴ�',9,'��');

INSERT INTO pets(pets_id, name, age,gender)
values(seq_pets.nextval,'�̱��Ƴ�',3,'��');

INSERT INTO pets(pets_id, name, age,gender)
values(seq_pets.nextval,'���Ź�',5,'��');

COMMIT;












