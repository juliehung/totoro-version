select
    idx as user_id,
    case
        when "身份證號,C,10" is null then 'NULL'
        when trim("身份證號,C,10") = '' then 'NULL'
        else "身份證號,C,10"
    end as national_id
from idx_doctor