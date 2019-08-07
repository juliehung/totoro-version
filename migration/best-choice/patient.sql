select
patno.idx as id,
PatName as name,
case
    when patno.Moble1 <> '' and patno.Moble1  is not null THEN patno.Moble1
    when patno.Moble2 <> '' and patno.Moble2 is not null THEN patno.Moble2
    when patno.TelH <> '' and patno.TelH is not null THEN patno.TelH
    when patno.TelC <> '' and patno.TelC is not null THEN patno.TelC
    else '0900000000'
end as phone,
case Sex
    when '男' then 'MALE'
    when '女' then 'FEMALE'
    else 'OTHER'
end as gender,
Birth as birth,
case
    when trim(ID) <> '' then ID
    else 'A000000000'
end as national_id,
case len(patno.idx)
    when 1 then '0000' + cast(patno.idx as varchar)
    when 2 then '000' + cast(patno.idx as varchar)
    when 3 then '00' + cast(patno.idx as varchar)
    when 4 then '0' + cast(patno.idx as varchar)
    when 5 then cast(patno.idx as varchar)
    else '00000'
end as medical_id,
case
    when trim(Addr1) <> '' and trim(Addr2) <> '' then trim(Addr1) + trim(Addr2)
    when trim(Addr1) <> '' and trim(Addr2) = '' then trim(Addr1)
    when trim(Addr1) = '' and trim(Addr2) <> '' then trim(Addr2)
    else 'NULL'
end as address,
case
    when Email is null then 'NULL'
    when trim(Email) = '' then 'NULL'
    else trim(Email)
end as email,
case
    when Blood is null then 'NULL'
    when trim(Blood) = '' then 'NULL'
    else trim(Blood)
end as blood,
case
    when CardId is null then 'NULL'
    when trim(CardId) = '' then 'NULL'
    else trim(CardId)
end as card_id,
case
    when VIP is null then 'NULL'
    when trim(VIP) = '' then 'NULL'
    else trim(VIP)
end as vip,
case
    when EmHuman is null then 'NULL'
    when trim(EmHuman) = '' then 'NULL'
    else trim(EmHuman)
end as emergency_name,
case
    when EmPhone is null then 'NULL'
    when trim(EmPhone) = '' then 'NULL'
    else trim(EmPhone)
end as emergency_phone,
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
cast(idxuser1.idx as varchar) as last_doctor_user_id,
cast(idxuser2.idx as varchar) as first_doctor_user_id,
'system' as created_by,
current_timestamp as created_date,
'system' as last_modified_by,
current_timestamp as last_modified_date,
'NULL' as emergency_address,
case
    when EmRelationShip like '%父%' then 'parent'
    when EmRelationShip like '%爸%' then 'parent'
    when EmRelationShip like '%母%' then 'parent'
    when EmRelationShip like '%媽%' then 'parent'
    when EmRelationShip like '%兒子%' then 'child'
    when EmRelationShip like '%女兒%' then 'child'
    when EmRelationShip like '%妻子%' then 'spouse'
    when EmRelationShip like '%老婆%' then 'spouse'
    when EmRelationShip like '%丈夫%' then 'spouse'
    when EmRelationShip like '%老公%' then 'spouse'
    when EmRelationShip like '%配偶%' then 'spouse'
    else 'friend'
end as emergency_relationship, -- check frontend use code or name to save data
'NULL' as main_notice_channel,
'NULL' as career,
'NULL' as marriage
from
     IdxPatNo patno,
     DoctorMap idxuser1,
     DoctorMap idxuser2
where
      1=1
    and patno.LastDoc = idxuser1.UserNo
    and patno.FirstDoc = idxuser2.UserNo
union all
select
patno.idx as id,
PatName as name,
case
    when patno.Moble1 <> '' and patno.Moble1  is not null THEN patno.Moble1
    when patno.Moble2 <> '' and patno.Moble2 is not null THEN patno.Moble2
    when patno.TelH <> '' and patno.TelH is not null THEN patno.TelH
    when patno.TelC <> '' and patno.TelC is not null THEN patno.TelC
    else '0900000000'
end as phone,
case Sex
    when '男' then 'MALE'
    when '女' then 'FEMALE'
    else 'OTHER'
end as gender,
Birth as birth,
case
    when trim(ID) <> '' then ID
    else 'A000000000'
end as national_id,
case len(patno.idx)
    when 1 then '0000' + cast(patno.idx as varchar)
    when 2 then '000' + cast(patno.idx as varchar)
    when 3 then '00' + cast(patno.idx as varchar)
    when 4 then '0' + cast(patno.idx as varchar)
    when 5 then cast(patno.idx as varchar)
    else '00000'
end as medical_id,
case
    when trim(Addr1) <> '' and trim(Addr2) <> '' then trim(Addr1) + trim(Addr2)
    when trim(Addr1) <> '' and trim(Addr2) = '' then trim(Addr1)
    when trim(Addr1) = '' and trim(Addr2) <> '' then trim(Addr2)
    else 'NULL'
end as address,
case
    when Email is null then 'NULL'
    when trim(Email) = '' then 'NULL'
    else trim(Email)
end as email,
case
    when Blood is null then 'NULL'
    when trim(Blood) = '' then 'NULL'
    else trim(Blood)
end as blood,
case
    when CardId is null then 'NULL'
    when trim(CardId) = '' then 'NULL'
    else trim(CardId)
end as card_id,
case
    when VIP is null then 'NULL'
    when trim(VIP) = '' then 'NULL'
    else trim(VIP)
end as vip,
case
    when EmHuman is null then 'NULL'
    when trim(EmHuman) = '' then 'NULL'
    else trim(EmHuman)
end as emergency_name,
case
    when EmPhone is null then 'NULL'
    when trim(EmPhone) = '' then 'NULL'
    else trim(EmPhone)
end as emergency_phone,
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
'NULL' as last_doctor_user_id,
'NULL' as first_doctor_user_id,
'system' as created_by,
current_timestamp as created_date,
'system' as last_modified_by,
current_timestamp as last_modified_date,
'NULL' as emergency_address,
case
    when EmRelationShip like '%父%' then 'parent'
    when EmRelationShip like '%爸%' then 'parent'
    when EmRelationShip like '%母%' then 'parent'
    when EmRelationShip like '%媽%' then 'parent'
    when EmRelationShip like '%兒子%' then 'child'
    when EmRelationShip like '%女兒%' then 'child'
    when EmRelationShip like '%妻子%' then 'spouse'
    when EmRelationShip like '%老婆%' then 'spouse'
    when EmRelationShip like '%丈夫%' then 'spouse'
    when EmRelationShip like '%老公%' then 'spouse'
    when EmRelationShip like '%配偶%' then 'spouse'
    else 'friend'
end as emergency_relationship, -- check frontend use code or name to save data
'NULL' as main_notice_channel,
'NULL' as career,
'NULL' as marriage
from
    NoDoctorIdxPatNo patno