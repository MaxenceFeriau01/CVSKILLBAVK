# ENVIRONNEMENTS

```
ALLOWED_ORIGINS=http://localhost:3000
APP_LOGGING_LEVEL=DEBUG
CUSTOM_DB_HOST=localhost
CUSTOM_DB_NAME=entreprendre_ensemble
CUSTOM_DB_PASSWORD=root
CUSTOM_DB_PORT=5432
CUSTOM_DB_SHOW=true
CUSTOM_DB_USER=postgres
CUSTOM_MAIL_AUTH=true
CUSTOM_MAIL_DEBUG=true
CUSTOM_MAIL_ENABLE=true
CUSTOM_MAIL_FROM=noreply-easylog@test.decathlon.com
CUSTOM_MAIL_HOST=localhost
CUSTOM_MAIL_PORT=1025
CUSTOM_MAIL_PROTOCOL=smtp
CUSTOM_MAIL_PWD=AsPC+JH5+z22Ee5xTzu1BEfY/EBUq4QarNB7IdxWCzMq
CUSTOM_MAIL_REPLYTO=noreply-easylog@test.decathlon.com
CUSTOM_MAIL_TLS=true
CUSTOM_MAIL_USER=AKIA4SAWCTS5S3O4ULNM
FRONT_URL=http://localhost:3000
GLOBAL_LOGGING_LEVEL=WARN
PERMIT_ALL=/v2/api-docs, /configuration/ui, /swagger-resources/**, /configuration/security, /swagger-ui.html, /webjars/**, /files/*,/ping/
SWAGGER_CONTEXT_PATH=/
```

## Docker 

# DockeFile 

- Create image : ```docker build -t entreprendre-ensemble-back .```
- Run image : ```docker run --rm -it -p 80:80 entreprendre-ensemble-back```