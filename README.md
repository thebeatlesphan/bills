# bills
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
2. ```docker compose up -d```\
_note: Docker will deploy the single container on host-ip:3000:3306_


I was also able to expose my Raspberry Pi to the internet by port forwarding all requests to my public-ip:3000:3306 to my Raspberry Pi.
These instructions are specific to your own ISP and router/modem.

### Entity Relationship Diagram ERD - _subject to be edited as the project progresses_
| user      | expense  | group    | user_group | expense_group |
|:----------|:---------|:---------|:-----------|:--------------|
|id pk      |id pk     |id pk     |group.id fk |group.id fk    |
|username pk|name      |group_name|user.id fk  |expense.id fk  |
|password   |amount    |          |            |               |
|           |date      |          |            |               |

## February 22, 2024
Created Github Repository.\
Wrote README.md.\
Started research on Java Spring Boot.\
Began setting up my dev environment for remote work: port forwarded ssh and mysql.\
Updated ERD.\

<ins>Things To Consider For Security</ins>
1. Edit /etc/ssh/sshd_config -> +AllowGroups +AllowUsers
2. Port Knocking
3. grep "Failed Password" /var/log/auth.log

