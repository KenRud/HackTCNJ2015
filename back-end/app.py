from flask import Flask, g, request, jsonify,send_from_directory
import sqlite3
from random import randrange
import os
from subprocess import call

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
	return "hello world."



@app.route("/create_account", methods = ["POST", "GET"])
def create_account():
	incoming = request.form
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
	incoming = request.form
	user = incoming['name']
	password = incoming['password']
	conn = connect_db()
	db = conn.xcursor()
	db.execute("PRAGMA foreign_keys = ON;")
	db.execute("SELECT * FROM USER WHERE NAME = ? AND PASSWORD = ?", (user, password))
	result = db.fetchone()
	conn.commit()
	conn.close()
	if result is None:
		result = "Error"
		return jsonify(**{'result': result})
	else:
		result = "Success"
		return jsonify(**{'result': result})

@app.route("/make_friends", methods = ["POST", "GET"])
def make_friends():
	incoming = request.form
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
	incoming = request.form
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
	incoming = request.form
	user1 = incoming['name1']
	user2 = incoming['name2']
	both_users = [user1, user2]
	both_users.sort()
	user1,user2 = both_users[0],both_users[1]
	conn = connect_db()
	db = conn.cursor()
	db.execute("INSERT INTO GAME (NAME1,NAME2,TURN_COUNTER,SCORE1,SCORE2,VIDEO) VALUES (?,?,0,0,0,'Video Link');", (user1, user2))
	db.execute("SELECT * FROM GAME WHERE NAME1 = ? and NAME2 = ?", (user1, user2))
	result = db.fetchone()
	conn.commit()
	conn.close()
	return jsonify(**{'result': result})


@app.route("/return_games", methods = ["POST", "GET"])
def return_games():
	incoming = request.form
	user = incoming['name']
	conn = connect_db()
	db = conn.cursor()
	db.execute("SELECT * FROM GAME WHERE NAME1 = ? OR NAME2 = ?", (user, user))
	result = db.fetchall()
	if result is []:
		return jsonify(**{'result': result})
	conn.commit()
	conn.close()
	return jsonify(**{'result': result})

@app.route("/update_game", methods = ["POST", "GET"])
def update_game():
	incoming = request.form
	user1 = incoming['name1']
	user2 = incoming['name2']
	turn = incoming['turn']
	score1 = incoming['score1']
	score2 = incoming['score2']
	video = incoming['video']
	both_users = [user1, user2]
	both_users.sort()
	user1,user2 = both_users[0],both_users[1]
	conn = connect_db()
	db = conn.cursor()
	db.execute("UPDATE GAME SET TURN_COUNTER=?, VIDEO=? WHERE NAME1=? AND NAME2=?", (turn, video, user1, user2))
	db.execute("SELECT * FROM GAME WHERE NAME1 = ? and NAME2 = ?", (user1, user2))
	result = db.fetchone()
	conn.commit()
	conn.close()
	return jsonify(**{'result': result})


@app.route("/get_file", methods = ["POST", "GET"])
def get_file():
	incoming = request.form
	user1 = incoming['name1']
	user2 = incoming['name2']
	turn = incoming['turn']
	score1 = incoming['score1']
	score2 = incoming['score2']
	video = incoming['video']
	both_users = [user1, user2]
	both_users.sort()
	user1,user2 = both_users[0],both_users[1]
	return send_from_directory(".", "out.txt")

@app.route("/get_song", methods = ["POST", "GET"])
def get_song():
	incoming = request.json
	user = incoming['name']
	val = randrange(3)
	url = ""
	if val == 1:
		#believe in life after love
		url = "https://www.youtube.com/watch?v=MB2GQ28sE5s"
	elif val == 0: 
		#avril
		url = "https://www.youtube.com/watch?v=VQXfexNVPSs"
	else:
		#frozen
		url = "https://www.youtube.com/watch?v=L0MK7qz13bU"
	url = "https://www.youtube.com/watch?v=Xm_dS-wEFvs"
	command = "youtube-dl -x --audio-format mp3 %s" % url
	os.chdir("./music/")
	clear = "rm %s" % next(os.walk(os.getcwd()))[2][0]
	call(clear.split())
	call(command.split(), shell=False)
	path = next(os.walk(os.getcwd()))[2][0]
	os.chdir("./../")
	return send_from_directory("./music/", path)


if __name__ == '__main__':
	debug = True
	port = 6969
	host = "0.0.0.0"
	app.run(host,port,debug)