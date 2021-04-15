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

## Test with completed(in clinic) data
Add `no-liquibase` in `SPRING_PROFILES_ACTIVE`

Build
---
- 有時候 build 遇到找不到 `Treatment_` 這種情形，你可以做以下處理
    - 檢查 IDE, build tool 是否有開啟 annotation processor
    - 檢查是否 compile error 是否有提示，除了缺少這類的檔案所造成的錯誤，因為 gradle build 的過程中，假設 code 裡面有造成 compile fail 的因素，就不會執行 annotation proceesor。
    
Deploy local machine with SMS service
---
### Preparation
1. 建立一個 service account 且擁有 cloud storage, function caller 的權限(ref [here](https://gitlab.com/dentall/totoro-admin/-/wikis/GCP/Create-service-account))
2. 產生該 service account 對應的 credential.json，並放置於 將運作這些服務的機器上指定位置，並設定環境變數 `GOOGLE_APPLICATION_CREDENTIALS=指定位置`。
    ```bash
    # credential.json 即是上述 wiki 最後步驟產生的 json ，名字則依照下載的結果為主，當然你也可以改成你想要的名稱
    export GOOGLE_APPLICATION_CREDENTIALS=/home/totoro-admin/credential.json
   
    # or
   
    GOOGLE_APPLICATION_CREDENTIALS=/home/totoro-admin/dentall-saas.json java -jar ...審略
    ``` 
3. 至 firebase firestore `clinic-dev` 集合，新增一組資料如下， name 需要匹配當初命名的規則且一至
    ```
    {
      name: "dev",
      sms: {
        activated: true,
        remaining: 0,
        total: 0,
        vendor: "TwSMS"
      }
    }
    ``` 

Mockito
---
-  下中斷點時，不能下在 Mockito 的元件底下，會 exception

Liquibase
---
- 進入點為 `main/resources/config/liquibase/master.xml`。
- `2020/08/03` 之後的紀錄，不分類。僅依時間續加入異動於 `main/resources/config/liquibase/master_timeline.xml`
- `2020/08/03` 之前的紀錄，會在 `main/resources/config/liquibase/master_<type>.xml` 依照操作類型紀錄實際操作內容。

如何操作 Liquibase data migration
1. 確認需要加入的操作，並把該操作(a.k.a changeSet)，寫在 `main/resources/config/liquibase/changelog/<year>/<monty>/<year-month-modification-type>.xml`
2. 將上面的檔案位置加回進入點 `main/resources/config/liquibase/master_timeline.xml`。
3. 測試 migration 是否能正常運作。終端機在 project root 下執行 `./gradlew liquibaseUpdate` 便會依序執行 migration。詳細執行對象之設定 `build.gradle` 的 `liquibase` 設定段可以找到，
預設值為 `totoro:totoro@localhost:5432/totoro` 。

Java8 lambda
---
- Stream map 假設對象是 array 裡面有可能有 null ([Object1, null, Object2])，若做類似這種的操作就會產生 null point exception
```
array.stream()
    .map(Some::getId)
```

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
- 專科，只能在更新 user 的時候才能被增加，新增時不支援，且僅在 `/api/v2` 支援。
- TreatmentProcedure，由於設計失誤，導致此像表示，使用者自行增加的項目，有可能為 `診療` 或 `診察`。
- First/Last doctor，改由 disposal 下 appointment 中 第一跟最新一個預約醫生
- Nhi tx/medicine 是完整匯入，來源自健保局公開資料

Nhi rule check
---
- 開發，當有新的類型的健保需要被增加時，需新增 `NhiRuleCheckScript<健保代碼群組>` 且實作，以及 `NhiRuleCheckScriptType` 增加新的 enum 讓 resource 可以找到正確對應的 `NhiRuleCheckScript<健保代碼群組>`


Image
---
- Size, Gcp 支援 original, Host 支援 original, median
- `/api/images/{id}/thumbnails` 用來取得可用的 url
- `/api/images/host` 只在 `img-host` 下可用，具有風險可以存取檔案的 api。能取得其他存在於機器本身的檔案

Metrics
---
- 由於以內建整合於 application ， 可以藉由 http://some.how.domain:someport/management/metrics 以取得，且需要帶入 bearer token。
<br/>For Example:
    ```bash
    # Authorization 的 token 須自行取得
    curl --location --request GET 'http://his.dentall.io/cp/management/metrics' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3Rvcm91c2VyIiwiYXV0aCI6IlJPTEVfQURNSU4iLCJleHAiOjE2MzY2OTkzNzB9.r94Sxqrh4eL5dQoqRAMyYtHAwhpmL2ujjk7HcEZH8G0Sz' \
    --header 'Cookie: GCLB=CP3pgqjoi6rW7gE'
    ```
- 若要輸出至 log 可以調整 application-prod.yaml 的 jhipster.metrics.logs.enabled = true


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
