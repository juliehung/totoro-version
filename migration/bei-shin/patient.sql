select
"病歷編號,C,9" as id,
"病患姓名,C,20" as name,
case
    when "行動電話,C,14" <> '' and "行動電話,C,14" is not null THEN "行動電話,C,14"
    when "住宅電話,C,14" <> '' and "住宅電話,C,14" is not null THEN "住宅電話,C,14"
    when "公司電話,C,14" <> '' and "公司電話,C,14" is not null THEN "公司電話,C,14"
    else '0900000000'
end as phone,
case "性別,C,2"
    when '男' then 'MALE'
    when '女' then 'FEMALE'
    else 'OTHER'
end as gender,
case
    when "出生日期,C,7" is null then '1900-01-01'
    else to_date(cast("出生日期,C,7" + 19110000 as varchar), 'YYYYMMDD')
end as birth ,
case
    when trim("身份證號,C,10") <> '' then "身份證號,C,10"
    else 'A000000000'
end as national_id,
case
    when "病歷編號,C,9" < 10 then concat('0000', cast("病歷編號,C,9" as varchar))
    when "病歷編號,C,9" >= 10 and "病歷編號,C,9" < 100 then concat('000', cast("病歷編號,C,9" as varchar))
    when "病歷編號,C,9" >= 100 and "病歷編號,C,9" < 1000 then concat('00', cast("病歷編號,C,9" as varchar))
    when "病歷編號,C,9" >= 1000 and "病歷編號,C,9" < 10000 then concat('0', cast("病歷編號,C,9" as varchar))
    when "病歷編號,C,9" >= 10000 and "病歷編號,C,9" < 100000 then cast("病歷編號,C,9" as varchar)
    else '00000'
end as medical_id,
case
    when "地址,C,50" is null then 'NULL'
    when trim("地址,C,50") = '' then 'NULL'
    else "地址,C,50"
end as address,
case
    when "EMAIL,C,40" is null then 'NULL'
    when trim( "EMAIL,C,40") = '' then 'NULL'
    else trim( "EMAIL,C,40")
end as email,
'NULL' as blood,
'NULL' as card_id,
'NULL' as vip,
'NULL' as emergency_name,
'NULL' as emergency_phone,
'NULL' as delete_date,
'NULL' as scaling,
'NULL' as line_id,
'NULL' as fb_id,
'NULL' as note,
'NULL' as clinic_note,
'NULL' as write_ic_time,
'NULL' as avatar,
'NULL' as avatar_content_type,
'NULL' as new_patient,
'NULL' as questionnaire_id,
'NULL' as introducer_id,
'NULL' as patient_identity_id,
"主治醫師,C,3" as last_doctor_user_id,
"主治醫師,C,3" as first_doctor_user_id,
'system' as created_by,
current_timestamp as created_date,
'system' as last_modified_by,
current_timestamp as last_modified_date,
'NULL' as emergency_address,
'NULL' as emergency_relationship, -- check frontend use code or name to save data
'NULL' as main_notice_channel,
'NULL' as career,
'NULL' as marriage
from
     patient pat