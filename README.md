# JavaCode_testTask
 test task for appliance JavaCode Comp.

Task is to create web-service, which includes:
1. DB Postgres
2. Back-End server on Spring Boot

To build this project, need to type in the command "**docker-compose build**". To get the project up: "**docker-compose up**". Need docker engine to work.
Docker compose consists of Postgres image, back-end image and PgAdmin image to gain access to BD through web.

Possible urls to play around the project:

localhost:8181/api/v1/wallets - get all wallets from bd table

localhost:8181/api/v1/wallets/{id} - get specific wallet from the bd table 

localhost:8181/api/v1/createwallet with JSON body {"amount": 555555} - creating wallet and saving it in bd

localhost:8181/api/v1/deletewallet/{id} - deletes specific wallet from bd table

localhost:8181/api/v1/wallet with JSON body {"valletId": "160e73ad-9db8-4dbc-a8a9-8607526ea4b1","operationType": "withdraw","amount": 222} - withdraws or deposits to the specific wallet
