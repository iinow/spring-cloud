# Config Client

Config 파일이 변경되면 /actuator/refresh 로 한다.
 
	curl -X POST http://localhost:port/actuator/refresh