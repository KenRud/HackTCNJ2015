#curl -X GET -H "Content-Type: application/json" -d '{"name":"Dan Seminara", "password":"DS"}' http://localhost:6969/create_account;
#curl -X GET -H "Content-Type: application/json" -d '{"name":"Mitch Kobil", "password":"MK"}' http://localhost:6969/create_account;
#curl -X GET -H "Content-Type: application/json" -d '{"name":"Kenny Ruddick", "password":"KR"}' http://localhost:6969/create_account;
#curl -X GET -H "Content-Type: application/json" -d '{"name":"Derek Duchesne", "password":"DD"}' http://localhost:6969/create_account;
curl -X GET -H "Content-Type: application/json" -d '{"name1":"Derek Duchesne", "name2":"DD"}' http://localhost:6969/make_friends;