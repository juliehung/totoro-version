# utf-8
import pandas as pd
import random
import string
import psycopg2
import re
import numpy as np
from datetime import datetime
import json


def execute_sql(sql_statement,item_value,item_id):
    conn = psycopg2.connect(host="totoro-app", port="5435", dbname="/build/h2db/db/totoro",user="totoro",password="totoro")
    cur = conn.cursor()
    cur.execute(sql_statement,(item_value,item_id))
    print(cur.rowcount)
    # Commit the changes to the database
    conn.commit()
    # Close communication with the PostgreSQL database
    cur.close()
def update_time(current_datetime,date_change):
    if not isinstance(current_datetime,float):
        date = current_datetime[:10]
        return (str(datetime.strptime(date,'%Y-%m-%d').date() + date_change )+current_datetime[10:])
    else:
        return("NULL")
def update_time_df(path,colname,date_change):    
    appointments = pd.read_csv(path,sep=";")
    update_datetime = [update_time(current_datetime,date_change) for current_datetime in appointments[colname]]
    appointments[colname] = update_datetime
    return(appointments)

# read current update date from json
with open('current_update_date.json', 'r') as f:
    data = json.load(f)
record = data['current_update_date']
current_update_datetime = datetime.strptime(record, '%Y-%m-%d')
now = datetime.now()
now = now.strftime("%Y-%m-%d")
date_change = datetime.strptime(now,'%Y-%m-%d') - current_update_datetime


# read original csv file and update dates
path = "/usr/src/app/data/"
filename = "appointments.csv"
appointments = update_time_df(path+filename,"expected_arrival_time",date_change)
filename = "registrations.csv"
registrations = update_time_df(path+filename,"arrival_time",date_change)


# real time update to sql
SQL = """
UPDATE APPOINTMENT
SET EXPECTED_ARRIVAL_TIME = %s
WHERE ID = %s
"""

new_expected_arrival_time = appointments['expected_arrival_time']
for i in range(0,len(appointments)):
    if new_expected_arrival_time[i] != "NULL":
        print("Update Appointment:",i+1)
        sql = execute_sql(SQL,new_expected_arrival_time[i],i+1)

SQL = """
UPDATE REGISTRATION
SET ARRIVAL_TIME = %s
WHERE ID = %s
"""
new_arrival_time = registrations['arrival_time']
for i in range(0,len(registrations)):
    if new_expected_arrival_time[i] != "NULL":
        print("Update Registration:",i+1)
        sql = execute_sql(SQL,new_arrival_time[i],i+1)

print("Finish update in SQL")

# udpate csv file
appointments.to_csv(path+"appointments.csv",sep=";",index = False)
registrations.to_csv(path+"registrations.csv",sep=";",index = False)
print("Finish update csv file")

# update current update date
current_update_date = {"current_update_date": now}
with open('current_update_date.json', 'w') as outfile:
    json.dump(current_update_date, outfile)
print("Finish update current update date")

