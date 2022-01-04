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
RunNhiRuleTest=false
BucketName="totoro-admin-build"
if [[ "${TagName}" =~ ^stg.+$ ]]; then
    VersionFile=stg.version.json
    DeployPort=8085
    Notify=true
    GcpRelease=true
    FirebaseRelease=true
    RunNhiRuleTest=true
elif [[ "${TagName}" =~ ^ms.+$ ]]; then
    VersionFile=stg.version.json
    DeployPort=8088
elif [[ "${TagName}" =~ ^nhi.+$ ]]; then
    VersionFile=stg.version.json
    DeployPort=8087
    Notify=true
    GcpRelease=true
    RunNhiRuleTest=true
elif [[ "${TagName}" =~ ^dev.+$ ]]; then
    VersionFile=dev.version.json
    DeployPort=8082
    WarName=totoro-dev.war
elif [[ "${TagName}" =~ ^[0-9]+.[0-9]+.[0-9]+.*$ ]]; then
    VersionFile=rel.version.json
    DevDeploy=false
    GcpRelease=true
    FirebaseRelease=true
    RunNhiRuleTest=true
fi
# tag should be like milestone-1.33-wat_ever_you_want_except_hypen
# e.g.
# O - milestone-1.33-1234.12334.41
# O - milestone-1.33-1234
# O - milestone-1.33-abcd123.123
# x - milestone-1.33-123-ab
milestone=$(echo ${TagName} | awk '{split($1, a, "-"); printf "%s-%s", a[1], a[2];}')
echo "::set-output name=tag-name::${TagName}"
echo "::set-output name=tag-timestamp::${TagTimestamp}"
echo "::set-output name=war-name::${WarName}"
echo "::set-output name=version-file::${VersionFile}"
echo "::set-output name=deploy-port::${DeployPort}"
echo "::set-output name=notify::${Notify}"
echo "::set-output name=dev-deploy::${DevDeploy}"
echo "::set-output name=gcp-release::${GcpRelease}"
echo "::set-output name=firebase-release::${FirebaseRelease}"
echo "::set-output name=run-nhi-rule-test::${RunNhiRuleTest}"
echo "::set-output name=bucket-name::${BucketName}"
echo "::set-output name=milestone::${milestone}"
