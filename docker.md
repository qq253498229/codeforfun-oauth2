
源码地址: 

[Github仓库](https://github.com/qq253498229/codeforfun-oauth2)

[码云仓库](https://gitee.com/consolelog/codeforfun-oauth2)

阿里云仓库镜像: `registry.cn-beijing.aliyuncs.com/codeforfun/oauth:1.0.1`

官方仓库镜像: `codeforfun/oauth:1.0.1`

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

