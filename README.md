# Spring 认证服务器

- [code方式获取token(可实现单点登陆)](#Code方式获取token)
    - [获取code](#获取code)
    - [通过code获取token](#通过code获取token)
- [通过用户名/密码方式获取token](#通过用户名/密码方式获取token)
- [检查token](#检查token)
- [刷新token](#刷新token)
- [注销](#注销)
- [测试数据](#测试数据)
- [容器化](#容器化)


## Code方式获取token

### 获取code

`GET` 请求，其中state为可选参数:

```
http://localhost:12222/oauth/authorize?response_type=code&client_id=client&redirect_uri=https%3A%2F%2Fwww.baidu.com&state=123
```

登录成功后跳转到URL:

```
https://www.baidu.com/?code=annqpj&state=123
```

### 通过code获取token

`POST` 请求(其中Authorization为client:secret的base64加密):

```
curl -X POST \
  http://localhost:12222/oauth/token \
  -H 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=authorization_code&redirect_uri=https%3A%2F%2Fwww.baidu.com&code=annqpj'
```

成功后返回:

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjYwNzk0MjEsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6IjM3ODhiMGYzLTE1MDktNDQ5Yi04Y2ZiLWE0YzdiY2Q5OWU1MSIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbImFwcCJdfQ.EWv_K3h0MYf6y8obSkEPJN1qUT09vWkdm5Osi0WoWbc",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsiYXBwIl0sImF0aSI6IjM3ODhiMGYzLTE1MDktNDQ5Yi04Y2ZiLWE0YzdiY2Q5OWU1MSIsImV4cCI6MTU2NjY3NzAyMSwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiJhMmU0NWYzNi0xMGFiLTQwZWYtYWYzYi04ZDc4N2Y4ODRmZjAiLCJjbGllbnRfaWQiOiJjbGllbnQifQ.hvtqlzCKP8EUFdlEQCHcFXwsUnofP65_E6cDwn4Px8E",
    "expires_in": 7115,
    "scope": "app",
    "jti": "3788b0f3-1509-449b-8cfb-a4c7bcd99e51"
}
```

code错误返回(状态码为400):

```json
{
    "error": "invalid_grant",
    "error_description": "Invalid authorization code: annqpj123"
}
```

## 通过用户名/密码方式获取token

`POST` 请求(其中Authorization为client:secret的base64加密):

```
curl -X POST \
  http://localhost:12222/oauth/token \
  -H 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&username=user&password=1'
```

成功后返回:

```
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjYwNzk0MjEsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6IjM3ODhiMGYzLTE1MDktNDQ5Yi04Y2ZiLWE0YzdiY2Q5OWU1MSIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbImFwcCJdfQ.EWv_K3h0MYf6y8obSkEPJN1qUT09vWkdm5Osi0WoWbc",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsiYXBwIl0sImF0aSI6IjM3ODhiMGYzLTE1MDktNDQ5Yi04Y2ZiLWE0YzdiY2Q5OWU1MSIsImV4cCI6MTU2NjY3NzAyMSwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiJhMmU0NWYzNi0xMGFiLTQwZWYtYWYzYi04ZDc4N2Y4ODRmZjAiLCJjbGllbnRfaWQiOiJjbGllbnQifQ.hvtqlzCKP8EUFdlEQCHcFXwsUnofP65_E6cDwn4Px8E",
    "expires_in": 6349,
    "scope": "app",
    "jti": "3788b0f3-1509-449b-8cfb-a4c7bcd99e51"
}
```

用户名或密码错误返回(状态码为400):

```
{
    "error": "invalid_grant",
    "error_description": "用户名或密码错误"
}
```

## 检查token

`GET` 请求(其中Authorization为client:secret的base64加密)，可以获取权限信息，如果开启了jwt则可以直接从token中获取，当然token也会变长:

```
curl -X GET \
  'http://localhost:12222/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjYwNzk0MjEsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6IjM3ODhiMGYzLTE1MDktNDQ5Yi04Y2ZiLWE0YzdiY2Q5OWU1MSIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbImFwcCJdfQ.EWv_K3h0MYf6y8obSkEPJN1qUT09vWkdm5Osi0WoWbc' \
  -H 'Authorization: Basic Y2xpZW50OnNlY3JldA=='
```

成功后返回:

```json
{
    "user_name": "user",
    "scope": [
        "app"
    ],
    "active": true,
    "exp": 1566079421,
    "authorities": [
        "USER"
    ],
    "jti": "3788b0f3-1509-449b-8cfb-a4c7bcd99e51",
    "client_id": "client"
}
```

token错误返回(状态码为400):

```json
{
    "error": "invalid_token",
    "error_description": "Token was not recognised"
}
```

## 刷新token

`POST` 请求:

```
curl -X POST \
  http://localhost:12222/oauth/token \
  -H 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=refresh_token&refresh_token=6fc140f2-6cec-4d72-91ec-724fae528245'
```

成功后返回，注意refresh_token也会改变，但是之前的refresh_token仍然可以使用:

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjYwODEzNzcsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6IjE3OTZjODE5LTFjMDItNDQ2Ni05ZmJlLTFiMmE1MTU5N2UyYSIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbImFwcCJdfQ.mtX5aqOhafUGV2ZIA2trKmM61FEqHLsaY42LnZZrIRQ",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsiYXBwIl0sImF0aSI6IjE3OTZjODE5LTFjMDItNDQ2Ni05ZmJlLTFiMmE1MTU5N2UyYSIsImV4cCI6MTU2NjY3ODY0MSwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiI3MjllZjQzMC1mOTk4LTQ1YjgtYjViMC0xYWQzMDdmNzc1NzEiLCJjbGllbnRfaWQiOiJjbGllbnQifQ.XQeq0H7efxKkSc3iwwyzVoC_aNbHWOmAXfwrGhcdf8k",
    "expires_in": 7199,
    "scope": "app",
    "jti": "1796c819-1c02-4466-9fbe-1b2a51597e2a"
}
```

refresh_token错误返回(状态码为400):

```json
{
    "error": "invalid_grant",
    "error_description": "Invalid refresh token: 6fc140f2-6cec-4d72-91ec-724fae528245123"
}
```

## 注销

referer为注销成功后跳转页面URL。

由于Spring OAuth2默认机制，注销之后不会清空缓存中的token，token过期时间不会刷新。
所以想要在注销之后销毁token的需要自己做一定的修改。

`GET` 请求:

```
http://localhost:12222/oauth/logout?referer=https%3A%2F%2Fwww.baidu.com
```

## 测试数据

测试脚本 [test.sql](test.sql)

默认用户：

- user:1
- admin:1

测试client：

- client:secret

## 容器化

最小化例子:

```yaml
version: "3"
services:
  oauth:
    image: registry.cn-beijing.aliyuncs.com/codeforfun/oauth:1.0.1
    ports:
      - "8080:8080"
  mysql:
    image: mysql:5.7.27
    environment:
      MYSQL_ROOT_PASSWORD: root
```

加入redis作为缓存:

```yaml
version: "3"
services:
  oauth:
    image: registry.cn-beijing.aliyuncs.com/codeforfun/oauth:1.0.1
    environment:
      REDIS_ENABLE: 'true'
    ports:
      - "8080:8080"
  mysql:
    image: mysql:5.7.27
    environment:
      MYSQL_ROOT_PASSWORD: root
  redis:
    image: redis:5.0.5-alpine
```

配置项(环境变量):

选项 | 说明
---|---
SPRING_APPLICATION_NAME | spring.application.name，默认 oauth
SERVER_PORT | 容器内应用端口号，默认8080
DB_USERNAME | 数据库用户名，默认root
DB_PASSWORD | 数据库密码，默认root
DB_HOST | 数据库host，默认mysql
DB_PORT | 数据库port，默认3306
DB_NAME | 数据库名称，默认oauth，没有会自动创建
REDIS_ENABLE | 是否开启redis，默认 'false'，注意加单引号
REDIS_HOST | redis host，默认redis
REDIS_PORT | redis port，默认6379
REDIS_PASSWORD | redis密码，默认为空
REDIS_CACHE_TIMEOUT | 缓存超时时间，单位为分钟，默认120分钟

