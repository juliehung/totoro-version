create view "CleanRegister" AS
SELECT *
FROM Register
where RegNo
          in (
          select regB.RegNo
          from PatientSchedule patSch,
               Register regB
          where regB.RegNo = patSch.RegNo
      )

-- Create view IdxCleanRegister for remap regNo to our id
create view "IdxCleanRegister" as
select row_number() over (order by RegNo) as idx, *
from CleanRegister

-- Create view IdxPatNo for remap patNo to our id
create view "IdxPatNo" as
select row_number() over (order by ChangeDate) as idx, *
from Patients

-- Create view IdxUser(contain doctor id) for remap userNo to our id
create view "IdxUser" as
select row_number() over (order by UserNo) + 4 as idx, *
from Users

-- Must create view MiddleAppointment for it is reeeeeeeeeeeeealy efficiency
create view "MiddleAppointment" as (select musr.idx as docIdx, mpat.idx as patIdx, creg.idx as regIdx, creg.RegNo
                                    from IdxCleanRegister creg,
                                         IdxUser musr,
                                         IdxPatNo mpat
                                    where creg.ApplyDoctor = musr.UserNo
                                      and creg.PatNo = mpat.PatNo)

-- Create view CleanPatientSchedule for remove duplicated RegNo, NP records
create view "CleanPatientSchedule" as (select *
                                       from (select row_number() over ( partition by RegNo order by CheckinTime) as sort,
                                                    * from PatientSchedule patsch) ext_pat_sch
                                       where RegNo <> ''
                                         and PatNo <> 'NP'
                                         and sort = 1)

-- Create view DoctorMap for (idx mapping our id, UserNo mapping theirs id)
create view "DoctorMap" as (select idx, UserNo
                            from IdxUser)

-- Create view PatientMap for (idx mapping our id, PatNo mapping theirs id)
create view "PatientMap" as (select idx, PatNo
                             from IdxPatNo)

-- Create view
create view "NoDoctorIdxPatNo" as (select * from IdxPatNo where 1 = 1 and (
                                (LastDoc = '' and FirstDoc <> '') or
                                (LastDoc <> '' and FirstDoc = '') or
                                (LastDoc = '' and FirstDoc = '') or
                                (LastDoc is null) or
                                (FirstDoc is null)
                            ))