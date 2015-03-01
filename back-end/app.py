from flask import Flask, g, request, jsonify
import sqlite3
import os

app = Flask(__name__)

DATABASE = "%s/database.db" % os.getcwd()

def connect_db():
	return sqlite3.connect(DATABASE)

@app.before_request
def before_request():
	g.db = connect_db()

@app.teardown_request
def teardown_request(exception):
	db = getattr(g, 'db', None)
	if db is not None:
		db.close()


@app.route('/')
def hello_world():
	return "hello worls."


@app.route("/create_account", methods = ["POST", "GET"])
def create_account():
	incoming = request.json
	user = incoming['name']
	password = incoming['password']
	conn = connect_db()
	db = conn.cursor()
	db.execute("PRAGMA foreign_keys = ON;")
	db.execute("SELECT * FROM USER WHERE NAME = ?", (user,))
	result = db.fetchall()
	return "0"
	if result is not []:
		result = "Error"
		return jsonify(**{'result': result})
	db.execute("INSERT INTO USER (NAME,PASSWORD) VALUES (?,?);", (user, password))
	conn.commit()
	conn.close()
	result = "Success"
	return jsonify(**{'result': result})

@app.route("/login", methods = ["POST", "GET"])
def login():
	incoming = request.json
	user = incoming['name']
	password = incoming['password']
	conn = connect_db()
	db = conn.xcursor()
	db.execute("PRAGMA foreign_keys = ON;")
	db.execute("SELECT * FROM USER WHERE NAME = ? AND PASSWORD = ?", (user, password))
	result = db.fetchone()
	conn.commit()
	conn.close()jsonify(**{'result': result})
	if result is None:
		result = "Error"
		return jsonify(**{'result': result})
	else:
		result = "Success"
		return jsonify(**{'result': result})

@app.route("/make_friends", methods = ["POST", "GET"])
def make_friends():
	incoming = request.json
	user1 = incoming['name1']
	user2 = incoming['name2']
	both_users = [user1, user2]
	both_users.sort()
	user1,user2 = both_users[0],both_users[1]
	conn = connect_db()
	db = conn.cursor()
	db.execute("PRAGMA foreign_keys = ON;")
	db.execute("INSERT INTO FRIENDS (NAME1,NAME2) VALUES (?,?);", (user1, user2))
	conn.commit()
	conn.close()
	return jsonify(**{'result': result})

@app.route("/return_friends", methods = ["POST", "GET"])
def return_friends():
	incoming = request.json
	user = incoming['name']
	conn = connect_db()
	db = conn.cursor()
	db.execute("SELECT * FROM FRIENDS WHERE NAME1 = ? OR NAME2 = ?", (user, user))
	result = db.fetchall()
	if result is []:
		return jsonify(**{'result': result})
	result = [i for sub in result for i in sub if i != user]
	conn.commit()
	conn.close()
	return jsonify(**{'result': result})


@app.route("/make_game", methods = ["POST", "GET"])
def make_game():
	incoming = request.json
	user1 = incoming['name1']
	user2 = incoming['name2']
	both_users = [user1, user2]
	both_users.sort()
	user1,user2 = both_users[0],both_users[1]
	conn = connect_db()
	db = conn.cursor()
	db.execute("INSERT INTO GAME (NAME1,NAME2,TURN_COUNTER,SCORE1,SCORE2,VIDEO) VALUES (?,?,0,0,0,'Video Link');", (user1, user2))
	conn.commit()
	conn.close()
	result = "Success"
	return jsonify(**{'result': result})


@app.route("/return_games/<user>", methods = ["POST", "GET"])
def return_games(user):
	conn = connect_db()
	db = conn.cursor()
	db.execute("SELECT * FROM GAME WHERE NAME1 = ? OR NAME2 = ?", (user, user))
	result = db.fetchall()
	if result is []:
		return jsonify(**{'result': result})
	conn.commit()
	conn.close()
	return jsonify(**{'result': result})


if __name__ == '__main__':
	debug = True
	port = 6969
	host = "0.0.0.0"
	app.run(host,port,debug)