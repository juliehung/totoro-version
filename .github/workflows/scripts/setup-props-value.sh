Sha=$(echo "${GITHUB_SHA}" | cut -c 1-8)
TagName=$(echo "${GITHUB_REF}" | cut -f 3 -d /)
TagTimestamp=$(date --iso-8601=minutes)
WarName="totoro-${TagName}-${Sha}.war"
VersionFile=""
DeployPort=""
Notify=false
DevDeploy=true
GcpRelease=false
FirebaseRelease=false
if [[ "${TagName}" =~ ^stg.+$ ]]; then
    VersionFile=stg.version.json
    DeployPort=8085
    Notify=true
    GcpRelease=true
    FirebaseRelease=true
elif [[ "${TagName}" =~ ^ms.+$ ]]; then
    VersionFile=stg.version.json
    DeployPort=8088
elif [[ "${TagName}" =~ ^nhi.+$ ]]; then
    VersionFile=stg.version.json
    DeployPort=8087
    Notify=true
    GcpRelease=true
elif [[ "${TagName}" =~ ^dev.+$ ]]; then
    VersionFile=dev.version.json
    DeployPort=8082
    WarName=totoro-dev.war
elif [[ "${TagName}" =~ ^[0-9]+.[0-9]+.[0-9]+.*$ ]]; then
    VersionFile=rel.version.json
    DevDeploy=false
    GcpRelease=true
    FirebaseRelease=true
else
    exit 1
fi
echo "::set-output name=tag-name::${TagName}"
echo "::set-output name=tag-timestamp::${TagTimestamp}"
echo "::set-output name=war-name::${WarName}"
echo "::set-output name=version-file::${VersionFile}"
echo "::set-output name=deploy-port::${DeployPort}"
echo "::set-output name=notify::${Notify}"
echo "::set-output name=dev-deploy::${DevDeploy}"
echo "::set-output name=gcp-release::${GcpRelease}"
echo "::set-output name=firebase-release::${FirebaseRelease}"
