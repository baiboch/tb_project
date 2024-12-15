# Test task data converter, logger and payment withdrew API

mvn clean package

docker build -t tb_project:latest .
docker run -p 9091:9091 --name tb_project_container tb_project:latest

docker-compose up --build
