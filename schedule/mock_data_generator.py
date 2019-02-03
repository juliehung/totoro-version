# utf-8
import pandas as pd
import random
import string
import re
import numpy as np
from datetime import datetime

def update_time(current_datetime, date_change):
    if current_datetime != "NULL":
        date = current_datetime[:10]
        return (str(datetime.strptime(date, '%Y-%m-%d').date() + date_change) + current_datetime[10:])
    else:
        return ("NULL")

def to_int(rid):
    if rid != "NULL":
        return(int(rid))
    else:
        return("NULL")

def update_time_df(path, colname, date_change):
    df = pd.read_csv(path, sep=";")
    df.fillna(value="NULL", inplace=True)
    update_datetime = [update_time(current_datetime, date_change) for current_datetime in df[colname]]
    df[colname] = update_datetime
    return (df)

base_date = datetime.strptime('2019-02-01', '%Y-%m-%d')
now = datetime.now()
now = now.strftime("%Y-%m-%d")
date_change = datetime.strptime(now,'%Y-%m-%d') - base_date

# read original csv file and update dates
path = "src/main/resources/config/liquibase/"
appointments_filename = "appointments.csv"
appointments = update_time_df(path+appointments_filename, "expected_arrival_time", date_change)
registrations_filename = "registrations.csv"
registrations = update_time_df(path+registrations_filename, "arrival_time", date_change)

# udpate csv file
appointments.registration_id = [to_int(rid) for rid in appointments.registration_id]
appointments.to_csv(path+appointments_filename, sep=";", index = False)
registrations.to_csv(path+registrations_filename, sep=";", index = False)
print("Finish update csv file")
