-- Create Idx for doctor
create view "idx_doctor" as (select row_number() over (order by "離職日期,C,7") + 4 as idx, * from doctor);

-- Clean patient(remove 999999999 aka 初診病患)
delete from patient where "病歷編號,C,9" = 999999999;

-- Desc: Create ROC date as 1 row integer value table for quick access as a WHERE clause.
-- Comments:
--  1. Current date minus 1911 years
--  1. Format to 'YYYYMMDD'
--  1. Remove first zero(because of 2019 will be transform into 0108)
--  1. Cast date as integer for easy comparison
create view "roc_today_date" as select cast(substring(to_char(current_date - interval '1911 years', 'YYYYMMDD'), 2) as integer) as current_date_roc

-- Clean appoinment
