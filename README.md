# bills
### To Start Project
1. From ./bills/backend `mvnw spring-boot:run`
2. From ./bills/frontend `npm run dev`

- Author: Andy Phan
- Name: Phan Family Expense Tracker
- Description: Full stack application to track personal expenses
- Stack: Java Spring Boot, MySQL, Docker
- Features: CRUD operations, username / password login, etc

## this is a personal project and i am open to collaboration

## February 21, 2024

Today, this project was started in earnest and I decided to begin with the database.
I had wanted to deploy an app on my Raspberry Pi 4 (8GB) for some time now and have finally found an issue solve.
Working with a 64 Bit ARM processor limited my database options but I was able to circumvent some of these limitations by using Docker.
I completed the [Docker Getting-Started](https://docs.docker.com/engine/reference/commandline/cli/) to familiarize myself with the platform and after much trial and error, I was able to deploy a single container with mysql.

_<ins>deploying docker</ins>_

1. cd to project directory
2. `docker compose up -d`\
   _note: Docker will deploy the single container on host-ip:3000:3306_

I was also able to expose my Raspberry Pi to the internet by port forwarding all requests to my public-ip:3000:3306 to my Raspberry Pi.
These instructions are specific to your own ISP and router/modem.

### Entity Relationship Diagram ERD - _subject to be edited as the project progresses_

| user     | expense | group      | user_group  | expense_group |
| :------- | :------ | :--------- | :---------- | :------------ |
| id pk    | id pk   | id pk      | group.id fk | group.id fk   |
| username | name    | group_name | user.id fk  | expense.id fk |
| password | amount  |            |             |               |
|          | date    |            |             |               |

## February 22, 2024

Created Github Repository.\
Wrote README.md.\
Started research on Java Spring Boot.\
Began setting up my dev environment for remote work: port forwarded ssh and mysql.\
Updated ERD.

<ins>Things To Consider For Security</ins>

1. Edit /etc/ssh/sshd_config -> +AllowGroups +AllowUsers
2. Port Knocking
3. grep "Failed Password" /var/log/auth.log

## February 23, 2024

Added Spring Boot - Spring Web, Spring Data JPA, and MySQL Driver.\
<ins>Keyterms</ins>\
Entity Model, Repository, Controller, and Application Class

## February 26, 2024

Refactored database API and constraints.\
Replaced all controller.java files with @RepositoryRestResource which creates all CRUD endpoints automatically.
Continuing research for Frontend development and creating association tables.

## February 27, 2024

Implement NextJS for Frontend client.

## February 28, 2024

Started the UI for AuthForm (Login and Registration).\
Created the endpoint for localhost:8080/api/auth/register.\
<ins>TODO:</ins>\
Certificate Authority (CA) for HTTPS\
Hypermedia as the engine of application state HATEOAS\
Test Harness

## February 29, 2024

Implementing Spring Security for Authentication and Authorization.\

<ins>Keyterms:</ins>\
Cross Site Request Forgery (CSRF) - Hijacking / Spoofing of HTTP requests\
Spring Security Principles: Authentication, Authorization, Principal, Authority, and Role\
Threadlocal - Store data that will be accessible only by a specific thread.\

<ins>Spring Security Flow:</ins>
1. User Enters Credentials
2. Authentication Filter / Authentication
3. Authentication Manager
4. Authentication Provider
5. User Details Service
6. Password Encoder
7. Security Context

## March 2nd, 2024
TODAY WAS A GREAT DAY!\
I've been diligently studying the Spring Security architecture and documentation. Issues and problems with implementation has been handled by working ChatGPT, exception handling, and trial and error.\

<ins>Current Dependencies</ins>
1. Java Persistence API - JPA
2. Spring Web
3. MySQL Driver
4. Rest Repositories
5. Spring Security
6. JWT

## March 3rd, 2024
