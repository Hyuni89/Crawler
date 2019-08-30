# Crawler Problem

### How to Run
1. clone it
2. `./gradlew bootrun`


### API
`url : http://localhost:8080`


### Login
- URL  
`GET http://localhost:8080/login`
- Request
```json
{}
```
> Empty body
- Response  
```json
{
  "status": 200,
  "token": "ONdDQi%9vfI9anXfs38WK5adrUZODN#X"
}
```
> `status`: `200` is OK, `400` is Invalid Token, `401` is Invalid Category, `402` is Invalid Page, `450` is Expired Token  
> `token` is random string. Put it in request header when you request other API like `-H 'token: ONdDQi%9vfI9anXfs38WK5adrUZODN#X'`


### Feature
- URL  
`GET http://localhost:8080/{category}/feature/{page}`
- Request  
```json
{}
```
> Empty body
- Response  
```json
{
  "status": 200,
  "prev_url": "prev_url",
  "next_url": "next_url",
  "images": [
    "image_name action",
    "image_name action",
    "image_name action"
  ]
}
```
> `prev_url` is previous page in same category  
> `next_url` is next page in same category  
> `images` is image name and action. action has two options `SAVE`, `DELETE`  

> __if your token is expired__
> ```json
> {
>   "status": 450,
>   "score": 384.39
> }
> ```
> and same as below APIs


### Save
- URL  
`POST http://localhost:8080/{category}/save`  
- Request  
```json
{
  "images": [
    "image_name",
    "image_name"
  ]
}
```
> `images` is image name for save from `Feature` API  
- Response
```json
{
  "status": 200
}
```


### Delete
- URL  
`POST http://localhost:8080/{category}/delete`  
- Request  
```json
{
  "images": [
    "image_name",
    "image_name"
  ]
}
```
- Response
```json
{
  "status": 200
}
```


### Attribute
Each categories have own attribute. Some categories generate images more often than others. Some categories change image action more often than others.
