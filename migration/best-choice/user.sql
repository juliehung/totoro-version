select 1 as id, 'system' as login, '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG' as password_hash,'System' first_name, 'System' as last_name, 'system@localhost' as email, 'NULL' as image_url, 'true' as activated, 'en' as lang_key, 'NULL' as activation_key, 'NULL' as reset_key, 'system' as created_by, current_timestamp as created_date, 'NULL' as reset_date, 'system' as last_modified_by, current_timestamp as last_modified_date
union
select 2 as id, 'anonymoususer' as login, '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG' as password_hash,'Anonymous' first_name, 'User' as last_name, 'anonymous@localhost' as email, 'NULL' as image_url, 'true' as activated, 'en' as lang_key, 'NULL' as activation_key, 'NULL' as reset_key, 'system' as created_by, current_timestamp as created_date, 'NULL' as reset_date, 'system' as last_modified_by, current_timestamp as last_modified_date
union
select 3 as id, 'admin' as login, '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC' as password_hash,'Administrator' first_name, 'Administrator' as last_name, 'admin@localhost' as email, 'NULL' as image_url, 'true' as activated, 'en' as lang_key, 'NULL' as activation_key, 'NULL' as reset_key, 'system' as created_by, current_timestamp as created_date, 'NULL' as reset_date, 'system' as last_modified_by, current_timestamp as last_modified_date
union
select 4 as id, 'user' as login, '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K' as password_hash,'User' first_name, 'User' as last_name, 'user@localhost' as email, 'NULL' as image_url, 'true' as activated, 'en' as lang_key, 'NULL' as activation_key, 'NULL' as reset_key, 'system' as created_by, current_timestamp as created_date, 'NULL' as reset_date, 'system' as last_modified_by, current_timestamp as last_modified_date
union
select
idx as id,
UserNo as login,
'$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K' as password_hash,
UserName as first_name,
UserName as last_name,
'NULL' as email,
'NULL' as image_url,
'true' as activated,
'en' as lang_key,
'NULL' as activation_key,
'NULL' as reset_key,
'system' as created_by,
current_timestamp as created_date,
'NULL' as reset_date,
'system' as last_modified_by,
current_timestamp as last_modified_date
from IdxUser