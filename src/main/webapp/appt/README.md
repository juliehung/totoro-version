Why this folder exist
===
- We want to split totoro-web and totoro-admin into different `git` but build/start to the same `war` file. Therefore, we download and put static `totoro-web` into 
`totoro-admin/src/main/webapp/appt`. However, in local build stage, it will cause errors by `totoro-admin/webpack/webpack.common.js` 
    ```
    ...
    { from: './src/main/webapp/appt/', to: 'appt' }
    ...
    ```
  There is several way to solve it. Finally, for uncoupling(解耦合), decide to just add a empty folder for it. But empty folder will not be accept by `git` more other maintainer 
  will confused by empty folder.  

Still want to run totoro-web and totoro-admin together
---
- Clone/Build `totoro-web` and put static web resources into `totoro-admin/src/main/webapp/appt/`(Not include build/ folder but all its child)
