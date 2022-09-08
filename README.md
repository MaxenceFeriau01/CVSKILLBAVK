# ENVIRONNEMENTS

```
    "ALLOWED_METHODS": "GET,POST,PUT,PATCH,DELETE,OPTIONS",
    "ALLOWED_ORIGINS": "*",
    "APP_LOGGING_LEVEL": "DEBUG",
    "CUSTOM_CRON_MAIL": "-",
    "CUSTOM_DB_HOST": "localhost",
    "CUSTOM_DB_NAME": "entreprendre_ensemble",
    "CUSTOM_DB_PASSWORD": "root",
    "CUSTOM_DB_PORT": "5432",
    "CUSTOM_DB_SHOW": "true",
    "CUSTOM_DB_USER": "postgres",
    "CUSTOM_MAIL_AUTH": "true",
    "CUSTOM_MAIL_DEBUG": "true",
    "CUSTOM_MAIL_ENABLE": "true",
    "CUSTOM_MAIL_FROM": "noreply-easylog@test.decathlon.com",
    "CUSTOM_MAIL_HOST": "localhost",
    "CUSTOM_MAIL_PORT": "1025",
    "CUSTOM_MAIL_PROTOCOL": "smtp",
    "CUSTOM_MAIL_PWD": "AsPC+JH5+z22Ee5xTzu1BEfY/EBUq4QarNB7IdxWCzMq",
    "CUSTOM_MAIL_REPLYTO": "noreply-dkstage@entreprendre.ensemble.com",
    "CUSTOM_MAIL_TLS": "true",
    "CUSTOM_MAIL_USER": "AKIA4SAWCTS5S3O4ULNM",
    "FRONT_URL": "http://localhost/",
    "GLOBAL_LOGGING_LEVEL": "WARN",
    "SWAGGER_CONTEXT_PATH": "api/"
```

## Docker 

# DockeFile 

- Create image : ```docker build -t entreprendre-ensemble-back .```
- Run image : ```docker run --rm -it -p 80:80 entreprendre-ensemble-back```


## Flyway
- Source : https://www.baeldung.com/database-migrations-with-flyway

- spring.jpa.hibernate.ddl-auto=none **MUST BE** set to none for production

Flyway adheres to the following naming convention for migration scripts:

```<Prefix><Version>__<Description>.sql```

Where:
```
<Prefix> – The default prefix is V, which we can change in the above configuration file using the flyway.sqlMigrationPrefix property.
<Version> – Migration version number. Major and minor versions may be separated by an underscore. The migration version should always start with 1.
<Description> – Textual description of the migration. A double underscore separates the description from the version numbers.
```
Example: V1_1_0__my_first_migration.sql