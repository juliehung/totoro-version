TOTORO
===
This application was generated using JHipster 5.4.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.4.0](https://www.jhipster.tech/documentation-archive/v5.4.0).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    npm install

We use npm scripts and [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./gradlew
    npm start

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### Service workers

Service workers are commented by default, to enable them please uncomment the following code.

* The service worker registering script in index.html

```html
<script>
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker
        .register('./service-worker.js')
        .then(function() { console.log('Service Worker Registered'); });
    }
</script>
```

Note: workbox creates the respective service worker and dynamically generate the `service-worker.js`

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

    npm install --save --save-exact leaflet

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

    npm install --save-dev --save-exact @types/leaflet

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Note: there are still few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].



## Building for production

To optimize the totoro application for production, run:

    ./gradlew -Pprod clean bootWar

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar build/libs/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./gradlew test

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    npm test



For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Then, run a Sonar analysis:

```
./gradlew -Pprod clean test sonarqube
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

    docker-compose -f src/main/docker/postgresql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/postgresql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./gradlew bootWar -Pprod jibDockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

## Enable Google Calendar API

To enable google calendar api, you need a service account which can be registered from Google API console. After creating a service account, download the corresponding secret file and put it in the same folder with the war file.

## Launch totoro-admin with Docker on local env. for frontend
### requirement
install Docker
- [Mac](https://docs.docker.com/docker-for-mac/install/)
- [Windows](https://docs.docker.com/docker-for-windows/install/)

login to **GitLab’s Container Registry**
```
docker login registry.gitlab.com
```

### build & run image
#### clone repo.
```
git clone https://gitlab.com/dentall/totoro-admin.git
```

#### start Docker container
```
docker-compose -f src/main/docker/postgresql.yml -f src/main/docker/app-dev.yml up -d
```

#### list containers
```
docker ps -a
```

#### fetch the logs of a container
```
docker logs -f totoro-admin
```

#### run a command in a running container
```
docker exec -it totoro-admin sh
/ # ...
/ # exit
```

#### cleanup
```
docker-compose -f src/main/docker/postgresql.yml -f src/main/docker/app-dev.yml down --rmi all
```

## Launch totoro-admin on local env. for developer
### with data
please add **mock** profile

### with Docker (recommend)
#### start
```
docker-compose -f src/main/docker/postgresql.yml up -d
```

#### stop
```
docker-compose -f src/main/docker/postgresql.yml down
```

### with Embedded Postgres
#### dev
```
SPRING_PROFILES_ACTIVE=dev,embedded-postgres TZ=UTC ./gradlew clean bootRun
```
##### windows
```
# powershell
$env:SPRING_PROFILES_ACTIVE="dev,embedded-postgres"; $env:TZ="UTC"; .\gradlew.bat clean bootRun
```

#### test
```
SPRING_PROFILES_ACTIVE=embedded-postgres TZ=UTC ./gradlew clean test
```
##### windows
```
# powershell
$env:SPRING_PROFILES_ACTIVE="embedded-postgres"; $env:TZ="UTC"; .\gradlew.bat clean test
```

#### cleanup
##### mac
`lsof -i tcp:5432` check postgres pid, `kill -9 <pid>`, and delete the folder `/var/folders/.../postgresql-embed-<xxx>`

##### windows
```
# powershell
Powershell.exe -File .\embedded-postgres-cleanup.ps1
```

## English version (with data)
### Postgres with Docker
```
SPRING_PROFILES_ACTIVE=dev,en ./gradlew clean bootRun
# or
$env:SPRING_PROFILES_ACTIVE="dev,en"; .\gradlew.bat clean bootRun
```

### Embedded Postgres
```
SPRING_PROFILES_ACTIVE=dev,embedded-postgres,en TZ=UTC ./gradlew clean bootRun
# or
$env:SPRING_PROFILES_ACTIVE="dev,embedded-postgres,en"; $env:TZ="UTC"; .\gradlew.bat clean bootRun
```

## CP test server (with data)
```
# start
docker-compose -f src/main/docker/postgresql.yml -f src/main/docker/app-dev.yml -f src/main/docker/app-cp.yml up -d

# cleanup
docker-compose -f src/main/docker/postgresql.yml -f src/main/docker/app-dev.yml -f src/main/docker/app-cp.yml down --rmi all
```

## deploy to GAE
```
./gradlew clean appengineDeploy -Pgae -Pprod
```

## Test with completed(in clinic) data
Add `no-liquibase` in `SPRING_PROFILES_ACTIVE`

Spring JPA, Select data with Projection
---
- 由於 projection 目前不支援 jsonb 這種形態的資料，且加入此項後會導致 treatmentProcedure_Id 這種 nested 
類型的功能失效，因此此需利用 EsPL 去取得該資料參考 [here](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.interfaces.open)

Spring JPA, Pagination
---
- Duplicated data while using pagination， Spring 會將 Pagination 這一行為，轉換至對等的原生 SQL ，並使用到 limit, offset 這兩個語法。而在 Postgres 中，這兩個語法，若不搭配 order by ，
則會出現不可預期的問題。因此 在這邊加上客製化的 default 值，且 inject 回 對應的函式中。當前端有給予 sort 在 query string 時，則會取代 default 值。

Spring Security, Login
---
- DomainUserDetailsService 會將帳號轉製成小寫，在這種情境下，db 若因轉換，或其他原因導致，login 包含大寫，在這裡則會有找不到 user 而導致登入失敗。

Business Logic
---
- 自動生成次月月申報列表，由 `NhiMonthDeclarationTasks.createYearMonth` 的 annotation 註記來控制(cron)。
- 影像，必須設定 Java 變數 `spring-active-profiles`，環境變數 `IMAGE_BASIC_FOLDER_PATH`。而檔案儲存的實體位置，可能在本機，可能在雲端，由 `spring-active-profiles` 決定；
位置取決於 `IMAGE_BASIC_FOLDER_PATH` 。其他細節由本專案 Gitlab Wiki 提供。

[JHipster Homepage and latest documentation]: https://www.jhipster.tech
[JHipster 5.4.0 archive]: https://www.jhipster.tech/documentation-archive/v5.4.0

[Using JHipster in development]: https://www.jhipster.tech/documentation-archive/v5.4.0/development/
[Using Docker and Docker-Compose]: https://www.jhipster.tech/documentation-archive/v5.4.0/docker-compose
[Using JHipster in production]: https://www.jhipster.tech/documentation-archive/v5.4.0/production/
[Running tests page]: https://www.jhipster.tech/documentation-archive/v5.4.0/running-tests/
[Code quality page]: https://www.jhipster.tech/documentation-archive/v5.4.0/code-quality/
[Setting up Continuous Integration]: https://www.jhipster.tech/documentation-archive/v5.4.0/setting-up-ci/


[Node.js]: https://nodejs.org/
[Yarn]: https://yarnpkg.org/
[Webpack]: https://webpack.github.io/
[Angular CLI]: https://cli.angular.io/
[BrowserSync]: http://www.browsersync.io/
[Jest]: https://facebook.github.io/jest/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
[Leaflet]: http://leafletjs.com/
[DefinitelyTyped]: http://definitelytyped.org/
