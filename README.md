# Introduction 
TODO: Give a short introduction of your project. Let this section explain the objectives or the motivation behind this project. 

# Getting Started
TODO: Guide users through getting your code up and running on their own system. In this section you can talk about:
1.	Installation process
2.	Software dependencies
3.	Latest releases
4.	API references

# Build and Test
TODO: Describe and show how to build your code and run the tests. 

# Contribute
TODO: Explain how other users and developers can contribute to make your code better. 

If you want to learn more about creating good readme files then refer the following [guidelines](https://docs.microsoft.com/en-us/azure/devops/repos/git/create-a-readme?view=azure-devops). You can also seek inspiration from the below readme files:
- [ASP.NET Core](https://github.com/aspnet/Home)
- [Visual Studio Code](https://github.com/Microsoft/vscode)
- [Chakra Core](https://github.com/Microsoft/ChakraCore)


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