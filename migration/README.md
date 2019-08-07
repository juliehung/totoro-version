Migration from different system
===

General Steps
---
1. Create source db and import source data into it
1. Operate every SQLs under `totoro-admin/migration/<source-system-name>`(create_view should be operate at first place)
1. Export SQLs' result to `totoro-admin/migration/target-data-csv`
1. Test migration
    ```
    // -PrunList=<liquibase-active-name> indicate which active should be run. The configuration is record in `build.gradle`
    $ ./gradlew liquibaseUpdate -PrunList=migration
    ```
1. If test is doing well. Then you are good to go to do migration. Otherwise, tuning the SQLs.

If doing migration from Best-choice...
---
- Preparation can be find [here][cp-wiki]
- Operate `user_authority.sql` need to modify in different clinic. Every clinic has different operation behavior and there is no column for use to distinguish which one is doctor 
or assistant.

If doing migration from Bei-Shin...
---
- It's using mtfk DOS DBF file. Currently there is no FREE and easy way to import it into modern dbms. Thus, if you are using M$, you can open it by `Excel`. If you use MacOS,
 unfortunately, you need to download `OpenOffice` to open it. Final, manually import csv into any dbms you'd like to(recommend `PostgreSQL`).
- Main data will add `.DBF` as extension(not .Dbf or other mutants)
- We need to find and transform `Cooper/patient.DBF`, `Cooper/doctor.DBF(jhi_user)`, `Cooper/pregist.DBF` into .csv
- The rest operation just like general steps.

Liquibase xml structure
---
It will include all development stage changes and execute it. After, import all *.csv from `migration/target-data-csv` 

Advance
---
- Maybe we can use Java to implement auto migration. There is a library can load DBF meanwhile no matter it help use load it into memory or db. After that we can use JPA load data 
into db. And run migration procedures.

CAUSIONS
---
- It working for `FIRST MIGRATION` or `TEST ON LOCAL MEACHINE`.
- Every clinic may have different operation behavior. It will cause different level of data pollution. Thus, even sql can genereate data for migration. But still need to test was 
there any lost and important data exist, or not.
- Make sure you can clearly distinguish `source files` and `target files`
- If you want to use gradle's liquibase tasks. Make sure your db is good to go. No matter you are going to use local application, docker, embedded-db, or something else. check 
it's url is working well. 

[cp-wiki]: https://gitlab.com/dentall/totoro-admin/wikis/Development/Dump-data-form-mssql-with-docker(CP)
