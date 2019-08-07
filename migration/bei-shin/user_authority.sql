select idx as user_id, 'ROLE_DOCTOR' as authority_name from idx_doctor where "職務別,C,8" like '%醫師%'
union
select idx as user_id, 'ROLE_ASSISTANT' as authority_name from idx_doctor where "職務別,C,8" = '護士'