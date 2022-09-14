## Books

### Get All Books
**Request**:
```
GET /books HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
[]
```
###### success
```text
200 - Ok
```
```json
[
  {
    "id": 1,
    "summary": "The story about Roberto...",
    "numberOfPages": 460,
    "releaseDate": "1998-05-23",
    "registrationDate": "2022-06-20",
    "title": "Roberto",
    "author": "Ricardo Torres",
    "publisher": "Porrua",
    "edition": "First edition"
  }
]
```


### Get Book
**Request**:
```
GET /books/{bookId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "The story about Roberto...",
  "numberOfPages": 460,
  "releaseDate": "1998-05-23",
  "registrationDate": "2022-06-20",
  "title": "Roberto",
  "author": "Ricardo Torres",
  "publisher": "Porrua",
  "edition": "First edition"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Book with id 23 does not exist
}
```


### Create Book
**Request**:
```
POST /books HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
201 - Created
```
```json
{
  "id": 1,
  "summary": "The story about Roberto...",
  "numberOfPages": 460,
  "releaseDate": "1998-05-23",
  "registrationDate": "2022-06-20",
  "title": "Roberto",
  "author": "Ricardo Torres",
  "publisher": "Porrua",
  "edition": "First edition"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Update Book
**Request**:
```
PUT /books/{bookId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "The story about Roberto...",
  "numberOfPages": 460,
  "releaseDate": "1998-05-23",
  "registrationDate": "2022-06-20",
  "title": "Roberto",
  "author": "Ricardo Torres",
  "publisher": "Porrua",
  "edition": "First edition"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Book with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Delete Book
**Request**:
```
DELETE /books/{bookId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "The story about Roberto...",
  "numberOfPages": 460,
  "releaseDate": "1998-05-23",
  "registrationDate": "2022-06-20",
  "title": "Roberto",
  "author": "Ricardo Torres",
  "publisher": "Porrua",
  "edition": "First edition"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Book with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


***


## Magazines

### Get All Magazines
**Request**:
```
GET /magazines HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
[]
```
###### success
```text
200 - Ok
```
```json
[
  {
    "id": 1,
    "summary": "Magazine that shows how...",
    "numberOfPages": 30,
    "releaseDate": "2022-06-20",
    "registrationDate": "2022-06-20",
    "title": "People",
    "publisher": "People"
  }
]
```


### Get Magazine
**Request**:
```
GET /magazines/{magazineId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Magazine that shows how...",
  "numberOfPages": 30,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "People",
  "publisher": "People"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Magazine with id 23 does not exist
}
```


### Create Magazine
**Request**:
```
POST /magazines HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
201 - Created
```
```json
{
  "id": 1,
  "summary": "Magazine that shows how...",
  "numberOfPages": 30,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "People",
  "publisher": "People"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Update Magazine
**Request**:
```
PUT /magazines/{magazineId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Magazine that shows how...",
  "numberOfPages": 30,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "People",
  "publisher": "People"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Magazine with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Delete Magazine
**Request**:
```
DELETE /magazines/{magazineId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Magazine that shows how...",
  "numberOfPages": 30,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "People",
  "publisher": "People"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Magazine with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


***


## Newspapers

### Get All Newspapers
**Request**:
```
GET /newspapers HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
[]
```
###### success
```text
200 - Ok
```
```json
[
  {
    "id": 1,
    "summary": "Newspaper that shows how...",
    "numberOfPages": 20,
    "releaseDate": "2022-06-20",
    "registrationDate": "2022-06-20",
    "title": "Occidental",
    "publisher": "Occidental"
  }
]
```


### Get Newspaper
**Request**:
```
GET /newspapers/{newspaperId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Newspaper that shows how...",
  "numberOfPages": 20,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "Occidental",
  "publisher": "Occidental"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Newspaper with id 23 does not exist
}
```


### Create Newspaper
**Request**:
```
POST /newspapers HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
201 - Created
```
```json
{
  "id": 1,
  "summary": "Newspaper that shows how...",
  "numberOfPages": 20,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "Occidental",
  "publisher": "Occidental"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Update Newspaper
**Request**:
```
PUT /newspapers/{newspaperId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Newspaper that shows how...",
  "numberOfPages": 20,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "Occidental",
  "publisher": "Occidental"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Newspaper with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Delete Newspaper
**Request**:
```
DELETE /newspapers/{newspaperId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Newspaper that shows how...",
  "numberOfPages": 20,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "title": "Occidental",
  "publisher": "Occidental"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Newspaper with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


***


## Letters

### Get All Letters
**Request**:
```
GET /letters HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
[]
```
###### success
```text
200 - Ok
```
```json
[
  {
    "id": 1,
    "summary": "Letter that shows how...",
    "numberOfPages": 3,
    "releaseDate": "2022-06-20",
    "registrationDate": "2022-06-20",
    "author": "Carlos Perez"
  }
]
```


### Get Letter
**Request**:
```
GET /letters/{letterId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Letter that shows how...",
  "numberOfPages": 3,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "author": "Carlos Perez"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Letter with id 23 does not exist
}
```


### Create Letter
**Request**:
```
POST /letters HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
201 - Created
```
```json
{
  "id": 1,
  "summary": "Letter that shows how...",
  "numberOfPages": 3,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "author": "Carlos Perez"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Update Letter
**Request**:
```
PUT /letters/{letterId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Letter that shows how...",
  "numberOfPages": 3,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "author": "Carlos Perez"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Letter with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```


### Delete Letter
**Request**:
```
DELETE /letters/{letterId} HTTP/1.1
Content-Type: application/json
<DataPacket>
```
**Response**
###### success
```text
200 - Ok
```
```json
{
  "id": 1,
  "summary": "Letter that shows how...",
  "numberOfPages": 3,
  "releaseDate": "2022-06-20",
  "registrationDate": "2022-06-20",
  "author": "Carlos Perez"
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
Body: {
    Letter with id 23 does not exist
}
```
###### bad_request
```text
HTTP 400
Content-Length: 0
```