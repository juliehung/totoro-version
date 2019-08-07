select
creg.idx as id,
'FINISH' as status,
ext.CheckinTime as arrival_time,
'NULL' as jhi_type,
'NULL' as on_site,
'NULL' as no_card,
'NULL' as accounting_id,
'system' as created_by,
current_timestamp as created_date,
'system' as last_modified_by,
current_timestamp as last_modified_date
from
     IdxCleanRegister creg,
(
    select
     patSch.RegNo,
     patSch.CheckinTime
    from
        CleanPatientSchedule patSch,
        MiddleAppointment ext
    where
          1=1
        and patSch.PatNo != 'NP'
        and patSch.RegNo != ''
        and patSch.IsCheckin = 1
        and patSch.RegNo = ext.RegNo
    ) ext
where creg.RegNo = ext.RegNo