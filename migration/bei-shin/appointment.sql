select row_number() over (order by "預約日期,C,7") as id,
       'CONFIRM'         as status,
       'NULL'            as subject,
       'NULL'            as note,
       case
           when "預約時間,C,4" < 1000 then to_timestamp(concat(cast("預約日期,C,7" + 19110000 as varchar), 0, cast("預約時間,C,4" as varchar)), 'YYYYMMDDHH24MI')
           else to_timestamp(concat(cast("預約日期,C,7" + 19110000 as varchar), cast("預約時間,C,4" as varchar)), 'YYYYMMDDHH24MI')
       end as expected_arrival_time,
       'NULL'            as required_treatment_time,
       'NULL'            as microscope,
       'NULL'            as base_floor,
       'NULL'            as color_id,
       'NULL'            as archived,
       'NULL'            as contacted,
       pat."病歷編號,C,9"        as patient_id,
       'NULL'        as registration_id,
       doc.idx        as doctor_user_id,
       'system'          as created_by,
       current_timestamp as created_date,
       'system'          as last_modified_by,
       current_timestamp as last_modified_date
from appointment apt,
     idx_doctor doc,
     beishin."public".patient pat
where 1 = 1
  and apt."醫師代號,C,3" = doc."醫師代號,C,3"
  and apt."病歷編號,C,9" = pat."病歷編號,C,9"
  and to_timestamp(cast("預約日期,C,7" + 19110000 as varchar), 'YYYYMMDD') >= current_date
