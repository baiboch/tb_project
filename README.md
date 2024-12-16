# Test task data converter, logger and payment withdrew API

docker build -t tb_project:latest .
docker run -p 9091:9091 --name tb_project_container tb_project:latest

mvn clean package
docker-compose up --build

## Setup:

#### - From the root project dir tb_project/
#### - execute: mvn clean package 
#### - execute: docker-compose up --build 

api generate log from xml request 

http://localhost:9091/tb/api/xml

curl -X POST http://localhost:9091/tb/api/xml \
-H "Content-Type: application/xml" \
-d '<?xml version="1.0" encoding="UTF-8" ?>
<Data>
<Method>
<Name>Order</Name>
<Type>Services</Type>
<Assembly>ServiceRepository, Version=1.0.0.1, Culture=neutral,
PublicKeyToken=null</Assembly>
</Method>
<Process>
<Name>scheduler.exe</Name>
<Id>185232</Id>
<Start>
<Epoch>1464709722277</Epoch>
<Date>2016-05-31T12:07:42.2771759+03:00</Date>
</Start>
</Process>
<Layer>DailyScheduler</Layer>
<Creation>
<Epoch>444</Epoch>
<Date>44</Date>
</Creation>
<Type>Global</Type>
</Data>'

Batch log files generate endpoint

curl -X POST "http://localhost:9091/tb/api/logs?fileName=Global_2024-12-16.log"

You can find the logs in 

/app/output_logs/batch_logs

and 

/app/output_logs/

directories

Example:

# tail -f Global_2024-12-16.log
TotalRecords: 4
{"Data":{"Method":{"Name":"Order","Type":"Services","Assembly":"ServiceRepository, Version=1.0.0.1, Culture=neutral, PublicKeyToken=null"},"Process":{"Name":"scheduler.exe","Id":"185232","Start":{"Epoch":"1464709722277","Date":"2016-05-31T12:07:42.2771759+03:00"}},"Layer":"DailyScheduler","Creation":{"Epoch":"444","Date":"44"},"Type":"Global"}}
{"Data":{"Method":{"Name":"Order","Type":"Services","Assembly":"ServiceRepository, Version=1.0.0.1, Culture=neutral, PublicKeyToken=null"},"Process":{"Name":"scheduler.exe","Id":"185232","Start":{"Epoch":"1464709722277","Date":"2016-05-31T12:07:42.2771759+03:00"}},"Layer":"DailyScheduler","Creation":{"Epoch":"444","Date":"44"},"Type":"Global"}}
{"Data":{"Method":{"Name":"Order","Type":"Services","Assembly":"ServiceRepository, Version=1.0.0.1, Culture=neutral, PublicKeyToken=null"},"Process":{"Name":"scheduler.exe","Id":"185232","Start":{"Epoch":"1464709722277","Date":"2016-05-31T12:07:42.2771759+03:00"}},"Layer":"DailyScheduler","Creation":{"Epoch":"444","Date":"44"},"Type":"Global"}}
{"Data":{"Method":{"Name":"Order","Type":"Services","Assembly":"ServiceRepository, Version=1.0.0.1, Culture=neutral, PublicKeyToken=null"},"Process":{"Name":"scheduler.exe","Id":"185232","Start":{"Epoch":"1464709722277","Date":"2016-05-31T12:07:42.2771759+03:00"}},"Layer":"DailyScheduler","Creation":{"Epoch":"444","Date":"44"},"Type":"Global"}}
^C
# ls
Global_2024-12-16.log  batch_logs
# cd batch_logs
# ls
Global_2024-12-16.log-0001.log  Global_2024-12-16.log-0002.log
# ls
Global_2024-12-16.log-0001.log  Global_2024-12-16.log-0002.log  Global_2024-12-16.log-0003.log  Global_2024-12-16.log-0004.log
# 

# Payments

Add new user

curl -X POST "http://localhost:9091/tb/api/auth/adduser" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your_token_here" \
-d '{
"username": "test",
"password": "pass"
}'

Login

curl -X POST "http://localhost:9091/tb/api/auth/login" \
-H "Content-Type: application/json" \
-d '{
"username": "test",
"password": "pass"
}'

Deduct amount 

curl -X POST "http://localhost:9091/tb/api/payment/process" \
-H "Content-Type: application/json" \
-d '{
"currency": "USD"
}'
