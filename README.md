# nachhilfe.wien.backend
## Rest-Endpoints

### /auth
```
POST
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
```

### /user/createStudent
**ÜBERARBEITEN!**
```


POST
Content-Type: application/json

Request:
{
  "firstName": String,
  "lastName": String,
  "birthdate": YYYY-MM-DD,
  "profile": {
    "userName": String,
    "password": String,
    "email": String,
    "active": Boolean
  }
}

Response:
{
  "id": Long,
  "userType": String,
  "firstName": String,
  "lastName": String,
  "birthdate": YYYY-MM-DD,
  "description": String,
  "profile": {
    "id": Long,
    "userName": String,
    "password": String,
    "email": String,
    "imageBase64": String,
    "description": String,
    "active": true,
    "averageRatingScore": Double
  },
  "coachings": [],
  "feedback": []
}
```

### /user/createTeacher
**Überarbeiten!**
```
POST
Content-Type: appilcation/json

Request:
{
  "firstName": String,
  "lastName": String,
  "birthdate": YYYY-MM-DD,
  "profile": {
    "userName": String,
    "password": String,
    "email": String,
    "active": Boolean,
    "description": String
  }
}

Response:
{
  "id": Long,
  "userType": String,
  "firstName": String,
  "lastName": String,
  "birthdate": YYYY-MM-DD,
  "description": String,
  "profile": {
    "id": Long,
    "userName": String,
    "password": String,
    "email": String,
    "imageBase64": null,
    "description": String,
    "active": Boolean,
    "averageRatingScore": Double
  },
  "coachings": [],
  "feedback": [],
  "disctricts": String(DISTRICTS)
}
```

### /conversation/{userId1}/{userId2}
```
POST
Content-Type: application/json

Response
{
  "conversationId": Long,
  "users": [
    {
      "id": Long,
      "userType": String(USERTYPE),
      "firstName": String,
      "lastName": String
    },
    {
      "id": Long,
      "userType": String(USERTYPE),
      "firstName": String,
      "lastName": String
    }
  ],
  "messages": []
}
```

### /message/sendmMessage/{conversationId}
```
POST
Content-Type: application/json

Request:
{
  "title": String,
  "content": String,
  "senderId": Long
}

Response:
{
  "messageId": Long,
  "timeStamp": "2023-06-06T10:44:39.8131627",
  "conversationId": Long,
  "title": String,
  "content": String,
  "senderId": Long
}
```

### /coaching/{userId}
```
POST
Content-Type: application/json

Request:
{
  "coachings": [
    {
      "subject": STRING(SUBJECT),
      "level": STRING,
      "rate": DOUBLE,
      "active": BOOLEAN
    }
  ]
}

Response:

{
  "userId": Long,
  "coachings": [
    {
      "id": Long,
      "subject":  STRING(SUBJECT),
      "level": STRING,
      "rate": Double,
      "active": Boolean,
      "userId": Long
    }
  ]
}
```

### /teacher
**ÜBERARBEITEN!**
```
GET
Array of all Teachers
```

### /user/{userId}
**ÜBERARBEITEN!**
```
GET
Information of specific user
```

### /teacher/updateDistricts/{teacherId}
```
PUT
Content-Type: application/json

Request:
{
  "districts": ["DISTRICT_1010", "DISTRICT_1030"]   DISTRICT_1010 bis DISTRICT_1230 als Enum
}

Response:
{
  "teacherId": Long,
  "districts": [
    "DISTRICT_1030",
    "DISTRICT_1010"
  ]
}








