set -e

webRunId=$(jq -r '.webRunId' "${VERSION_FILE}")
ApiUrl=https://api.github.com/repos/"${WEB_REPOSITORY}"/actions/runs/"${webRunId}"/artifacts
ArchiveUrl=$(curl -s -H "Accept: application/vnd.github.v3+json" -H "Authorization: token ${TOKEN}" "${ApiUrl}" | jq -r '.artifacts[0].archive_download_url')

echo "::group::Print Variables"
echo "webRunId=${webRunId}"
echo "ApiUrl=${ApiUrl}"
echo "ArchiveUrl=${ArchiveUrl}"
echo "::endgroup::"

echo "::group::Download web artifacts"
curl --location --output artifact.zip -H "Accept: application/vnd.github.v3+json" -H "Authorization: token ${TOKEN}" "${ArchiveUrl}"
mkdir -p "${GITHUB_WORKSPACE}"/web/build
unzip artifact.zip -d "${GITHUB_WORKSPACE}"/web/build
echo "::endgroup::"
