from flask import Flask
import sqlite

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'yolo!'



if __name__ == '__main__':
	debug = False
	port = 6969
	host = "0.0.0.0"
	app.run(host,port,debug)