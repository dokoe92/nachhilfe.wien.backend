# nachhilfe.wien.backend
##Rest-Endpoints

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

# 






