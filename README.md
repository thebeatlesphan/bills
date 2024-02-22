# bills
- Author: Andy Phan
- Name: Phan Family Expense Tracker
- Description: Full stack application to track personal expenses
- Stack: Java Spring Boot, MySQL, Docker
- Features: CRUD operations, username / password login, etc

## this is a personal project and i am open to collaboration
## February 21, 2024
The project was started in earnest today and I decided to start with setting up the database.
I had wanted to deploy a finished app out of my home while utilizing my Raspberry Pi 4 (8GB).
Limitations with the 64 Bit ARM processor limited my options on which database I could deploy.
Using Docker, I was able to circumvent some of these limitations but also manage these services more easily.
I completed the [Docker Getting-Started](https://docs.docker.com/engine/reference/commandline/cli/) to familiarize myself with the platform.
After much trial and error, I was able to deploy a single container with mysql.

_<ins>deploying docker</ins>_
1. cd to project directory
2. ```docker compose up -d```\
_note: Docker will deploy the single container on host-ip:3306_


I was able to expose my Raspberry Pi to the internet by port forwarding all requests to my public-ip:3306 to my Raspberry Pi.
These instructions are specific to your own ISP and router/modem.

### Entity Relationship Diagram ERD) - _subject to be edited as the project progresses_
| user      | expense  | group          |
|:---------:|:---------|:---------------|
|username pk|id pk     |id pk           |
|name       |name      |group_name      |
|password   |price     |user.username fk|
|           |date      |expense.id fk   |

## February 22, 2024
Created Github Repository.\
Writing README.md.\
Started research on Java Spring Boot.

