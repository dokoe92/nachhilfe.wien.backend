# nachhilfe.wien.backend
## Rest-Endpoints

# /auth
```
Content-Type: application/json

Request:
{
  "email": String
  "password": String
}

Response:
{
  "id": Long,
  "accessToken": String,
  "newMessage": Boolean,
  "type": String(USERTYPE),
  "email": String,
  "firstName": String,
  "lastName": String
}

# /user/createStudent
```
Content-Type: application/json

{
  "firstName": "Schüler'",
  "lastName": "Hansi",
  "birthdate": "1992-04-25",
  "profile": {
    "userName": "hansl",
    "password": "12345",
    "email": "hans@hansi.at",
    "active": true
  }
}





