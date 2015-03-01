from flask import Flask, g, request
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
	db.execute("INSERT INTO USER (NAME,PASSWORD) VALUES (?,?);", (user, password))
	conn.commit()
	conn.close()
	return "0"

@app.route("/make_friends", methods = ["POST", "GET"])
def make_friends():
	incoming = request.json
	user1 = incoming['name1']
	user2 = incoming['name2']
	conn = connect_db()
	db = conn.cursor()
	db.execute("PRAGMA foreign_keys = ON;")
	db.execute("SELECT * FROM FRIENDS WHERE NAME1 = ? AND NAME2 = ?", (user1, user2))
	first_query = db.fetchone()
	db.execute("SELECT * FROM FRIENDS WHERE NAME1 = ? AND NAME2 = ?", (user2, user1))
	second_query = db.fetchone()
	print first_query,second_query
	if first_query is not None or second_query is not None:
		return "Frienship is already made."
	db.execute("INSERT INTO FRIENDS (NAME1,NAME2) VALUES (?,?);", (user1, user2))
	conn.commit()
	conn.close()
	return "0"

@app.route("/return_games", methods = ["POST", "GET"])
def return_games():
	incoming = request.json
	user = incoming['name']
	conn = connect_db()
	db = conn.cursor()
	db.execute("SELECT * FROM GAME WHERE NAME1 = ? OR NAME2 = ?", (user, user))
	conn.commit()
	conn.close()
	return "Worked"

@app.route("/return_friends", methods = ["POST", "GET"])
def return_friends():
	incoming = request.json
	user = incoming['name']
	conn = connect_db()
	db = conn.cursor()
	db.execute("SELECT * FROM FRIENDS WHERE NAME1 = ? OR NAME2 = ?", (user, user))
	conn.commit()
	conn.close()
	return "Worked"


if __name__ == '__main__':
	debug = True
	port = 6969
	host = "0.0.0.0"
	app.run(host,port,debug)