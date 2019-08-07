select
    idx as user_id,
    case
        when ID is null then 'NULL'
        when trim(ID) = '' then 'NULL'
        else ID
    end as national_id
from IdxUser