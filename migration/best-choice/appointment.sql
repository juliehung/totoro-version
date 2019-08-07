select
 c_pat_sch.PSID as id,
 case
     when IsCheckin = 1 THEN 'CONFIRM'
     else 'CANCEL'
 end as status,
 'NULL' as subject,
 'NULL' as note,
 c_pat_sch.PSTime as expected_arrival_time,
 'NULL' as required_treatment_time,
 'NULL' as microscope,
 'NULL' as base_floor,
 'NULL' as color_id,
 'NULL' as archived,
 'NULL' as contacted,
 ext.patIdx as patient_id,
 ext.regIdx as registration_id,
 ext.docIdx as doctor_user_id,
 'system' as created_by,
 current_timestamp as created_date,
 'system' as last_modified_by,
 current_timestamp as last_modified_date
from
    CleanPatientSchedule c_pat_sch,
    MiddleAppointment ext
where
      1=1
    and c_pat_sch.RegNo = ext.RegNo