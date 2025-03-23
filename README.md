Assesment 2 
Student information system

-- task 1

create database sisdb;
use sisdb ;
create table students (student_id int not null primary key auto_increment,
first_name varchar(50) not null,
last_name varchar(50) not null,
date_of_birth date not null,
email varchar(40) not null unique ,
phone_number varchar(15) not null unique )  ;


create table teachers (teacher_id int not null primary key auto_increment ,
first_name  varchar(50) not null,
last_name varchar(50) not null,
email varchar(40) not null unique) ;


create table courses (course_id int not null primary key auto_increment ,
course_name varchar(50) not null,
credits int not null,
teacher_id int not null,
foreign key (teacher_id) references teachers(teacher_id)  on delete cascade);

create table enrollments(
enrollment_id int not null primary key auto_increment ,
student_id int not null,
course_id int not null,
enrollment_date date not null,
foreign key (student_id) references students(student_id) on delete cascade,
foreign key (course_id) references courses(course_id) on delete cascade);

create table payments (enrollment_id int not null primary key auto_increment ,
student_id int not null,
amount decimal(10,2) not null,
payment_date date not null,
foreign key (student_id) references students(student_id) on delete cascade);

ALTER TABLE payments 
RENAME COLUMN enrollment_id TO payment_id;

INSERT INTO students (first_name, last_name, date_of_birth, email, phone_number)  
VALUES    
('Subars', 'Dumba', '1990-12-05', 'subashbumber.78@gmail.com', '4567891230'),
('Amit', 'Sharma', '1995-07-12', 'amit.sharma95@email.com', '9876543210'),  
('Priya', 'Reddy', '1998-03-25', 'priya.reddy98@email.com', '8765432109'),  
('John', 'Doe', '2000-11-08', 'john.doe2000@email.com', '7654321098'),  
('Emily', 'Clark', '1993-05-15', 'emily.clark93@email.com', '6543210987'),  
('Raj', 'Patel', '1997-09-30', 'raj.patel97@email.com', '5432109876'),  
('Sophia', 'Martinez', '1996-02-20', 'sophia.martinez96@email.com', '4321098765'),  
('Michael', 'Brown', '1994-08-10', 'michael.brown94@email.com', '3210987654'),  
('Linda', 'Wilson', '1999-12-22', 'linda.wilson99@email.com', '2109876543'),  
('David', 'Lee', '1992-06-18', 'david.lee92@email.com', '1098765432'),  
('Sarah', 'Taylor', '2001-04-05', 'sarah.taylor01@email.com', '0987654321');  

INSERT INTO teachers (first_name, last_name, email) VALUES
('John', 'Smith', 'john.smith@email.com'),
('Alice', 'Johnson', 'alice.johnson@email.com'),
('Robert', 'Williams', 'robert.williams@email.com'),
('Emma', 'Brown', 'emma.brown@email.com'),
('David', 'Jones', 'david.jones@email.com'),
('Sophia', 'Garcia', 'sophia.garcia@email.com'),
('Michael', 'Martinez', 'michael.martinez@email.com'),
('Olivia', 'Hernandez', 'olivia.hernandez@email.com'),
('Daniel', 'Lopez', 'daniel.lopez@email.com'),
('Emily', 'Gonzalez', 'emily.gonzalez@email.com');


INSERT INTO courses (course_name, credits, teacher_id) VALUES
('Mathematics', 3, 1),
('Physics', 4, 2),
('Chemistry', 3, 3),
('Biology', 4, 4),
('History', 3, 5),
('Computer Science', 4, 6),
('English Literature', 3, 7),
('Economics', 3, 8),
('Psychology', 3, 9),
('Business Studies', 3, 10);

INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES
(1, 1, '2024-01-10'),
(2, 2, '2024-02-15'),
(3, 3, '2024-03-12'),
(4, 4, '2024-04-05'),
(5, 5, '2024-05-20'),
(6, 6, '2024-06-25'),
(7, 7, '2024-07-30'),
(8, 8, '2024-08-18'),
(9, 9, '2024-09-22'),
(10, 10, '2024-10-05');


INSERT INTO payments (student_id, amount, payment_date) VALUES
(1, 500.00, '2024-01-15'),
(2, 700.00, '2024-02-20'),
(3, 600.00, '2024-03-18'),
(4, 800.00, '2024-04-10'),
(5, 750.00, '2024-05-25'),
(6, 900.00, '2024-06-30'),
(7, 650.00, '2024-07-15'),
(8, 850.00, '2024-08-20'),
(9, 720.00, '2024-09-28'),
(10, 1000.00, '2024-10-10');




-- task2



insert into students ( first_name , last_name ,  date_of_birth , email , phone_number) 
values
('john','doe','1995-08-15',' john.doe@example.com','1234567890');

insert into enrollments (student_id , course_id , enrollment_date)
values ( 1,4,curdate());

update teachers
set email = 'johndoe90876@gmail.com'
where teacher_id = 2;

delete from enrollments
where student_id = 1 and course_id = 4;

update courses
set teacher_id  = 3
where course_id = 5;

delete from students
where student_id =11;

update payments 
set amount = 600
where payment_id  =7;

-- task 3

select s.student_id , concat(s.first_name,'',s.last_name) as student_name, sum(p.amount)as amount_sum
from  students s
join payments p on s.student_id = p.student_id
where s.student_id = 4
group by s.student_id , payment_id ;

select c.course_id , c.course_name , count(e.student_id) as count_students
from courses c
join enrollments e on c.course_id = e.course_id
group by c.course_id , c.course_name ;

select s.student_id , concat(s.first_name,'',s.last_name) as student_name
from  students s
left join enrollments e on s.student_id = e.student_id
where  e.enrollment_id is null
group by s.student_id;
 
 select s.first_name,s.last_name ,c.course_name
from  students s
join enrollments e on s.student_id = e.student_id
join courses c on c.course_id = e.course_id
;
 
  select concat(t.first_name,' ',t.last_name) as teacher ,c.course_name
from  teachers t
join courses c on c.teacher_id = t.teacher_id
;
 
 select s.student_id , concat(s.first_name,'',s.last_name) as student_name, p.amount
from  students s
left join payments p on s.student_id = p.student_id
where p.amount is null
 ;
 
 select c.course_id , c.course_name 
from courses c
left join enrollments e on c.course_id = e.course_id
where e.enrollment_id is null
;
SELECT DISTINCT e1.student_id , s.first_name, s.last_name
FROM enrollments e1
JOIN enrollments e2 
ON e1.student_id = e2.student_id 
AND e1.course_id <> e2.course_id  -- Ensure they are different courses
JOIN students s 
ON e1.student_id = s.student_id;

select * from enrollments;

  select concat(t.first_name,' ',t.last_name) as teacher 
from  teachers t
join courses c on c.teacher_id = t.teacher_id
where course_id is null
;
select * from courses;
select * from teachers;



-- task 4



select c.course_id , c.course_name , count(s.student_id) as s_count  from students s
left join enrollments e on s.student_id = e.student_id
left join courses c on c.course_id = e.course_id 
group by course_id , course_name
having s_count = (select avg(s_count) from(select count(e2 .student_id ) as s_count from enrollments e2 group by e2.course_id  )as av_students)  ORDER BY course_id ASC;

select concat(s.first_name, ' ' , s.last_name) as student_name , max(p.amount)as max_amount from students s
left join payments p on s.student_id = p.student_id
group by s.student_id 
having max_amount = (select max(p2.amount) from payments p2)  ;

INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES
(1, 1, '2024-01-15');


SELECT COUNT(s.student_id) AS student_count, c.course_id, c.course_name
FROM students s
LEFT JOIN enrollments e ON s.student_id = e.student_id
LEFT JOIN courses c ON c.course_id = e.course_id
GROUP BY c.course_id, c.course_name
HAVING student_count = (
    SELECT AVG(student_count)
    FROM (
        SELECT COUNT(e2.student_id) AS student_count
        FROM enrollments e2
        GROUP BY e2.course_id
    ) AS avg_students
)
ORDER BY student_count ASC;  -- Sorting in Ascending Order




SELECT 
    t.teacher_id,
    CONCAT(t.first_name, ' ', t.last_name) AS teacher_name,
    SUM(
        (SELECT SUM(p.amount) 
         FROM payments p 
         WHERE p.student_id IN (
             SELECT e.student_id 
             FROM enrollments e 
             WHERE e.course_id = c.course_id
         )
        )
    ) AS total_payments
FROM teachers t
JOIN courses c ON t.teacher_id = c.teacher_id
GROUP BY t.teacher_id, teacher_name;

SELECT s.student_id, CONCAT(s.first_name, ' ', s.last_name) AS student_name
FROM students s
WHERE (
    SELECT COUNT(DISTINCT e.course_id)
    FROM enrollments e
    WHERE e.student_id = s.student_id
) = (
    SELECT COUNT(DISTINCT c.course_id)
    FROM courses c
);


SELECT 
    t.teacher_id,
    CONCAT(t.first_name, ' ', t.last_name) AS teacher_name
FROM teachers t
LEFT JOIN (
    SELECT DISTINCT teacher_id FROM courses
) AS c ON t.teacher_id = c.teacher_id
WHERE c.teacher_id IS NULL;

    
SELECT 
    c.course_id, c.course_name 
FROM courses c
LEFT JOIN (
    SELECT DISTINCT course_id FROM enrollments) as e ON c.course_id = e.course_id
WHERE e.course_id is null;

SELECT 
    s.student_id, 
    CONCAT(s.first_name, ' ', s.last_name) AS student_name, 
    c.course_id, 
    c.course_name, 
    (SELECT SUM(p.amount) 
     FROM payments p 
     WHERE p.student_id = s.student_id 
     AND p.student_id IN 
         (SELECT e.student_id FROM enrollments e WHERE e.course_id = c.course_id)
    ) AS total_payment
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN courses c ON e.course_id = c.course_id
GROUP BY s.student_id, c.course_id, c.course_name;


SELECT student_id, student_name 
FROM ( 
    SELECT s.student_id, 
           CONCAT(s.first_name, ' ', s.last_name) AS student_name, 
           (SELECT COUNT(*) FROM payments p WHERE p.student_id = s.student_id) AS payment_count
    FROM students s
) AS student_payments 
WHERE payment_count > 1;


SELECT student_id, student_name, total_payment 
FROM ( 
    SELECT s.student_id, 
           CONCAT(s.first_name, ' ', s.last_name) AS student_name, 
           (SELECT SUM(p.amount) FROM payments p WHERE p.student_id = s.student_id) AS total_payment
    FROM students s
) AS student_total;

SELECT course_id, course_name, student_count 
FROM ( 
    SELECT c.course_id, 
           c.course_name, 
           (SELECT COUNT(e.student_id) FROM enrollments e WHERE e.course_id = c.course_id) AS student_count
    FROM courses c
) AS course_enrollment;

SELECT student_id, student_name, avg_payment 
FROM ( 
    SELECT s.student_id, 
           CONCAT(s.first_name, ' ', s.last_name) AS student_name, 
           (SELECT AVG(p.amount) FROM payments p WHERE p.student_id = s.student_id) AS avg_payment
    FROM students s
) AS student_avg;



