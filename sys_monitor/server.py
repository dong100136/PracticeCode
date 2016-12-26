from flask import Flask, request, render_template

import pymysql.cursors
import json
import monitor
import threading

app = Flask(__name__)

app.secret_key = "stone"
socketio = SocketIO(app)

connection = pymysql.connect(
    host='localhost', user='root', password='stone', db='test')
connection.autocommit(True)

@app.route("/index")
def index():
    return render_template("index.html")


@app.route("/cpu", methods=['GET','POST'])
def cpu_of_time():
    start = request.args['start']
    if start==None:
        start=''
    return monitor.get_cpu_statis_from_db(start)

if __name__ == "__main__":
    app.debug = True
    t = threading.Thread(target=monitor.get_and_save_cpu_statis)
    t.start()
    socketio.run(app)
