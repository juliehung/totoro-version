FROM python:3.7.1-stretch

WORKDIR /usr/src/app

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

COPY mock_data_dynamic.py ./
COPY wait-for-it.sh ./
