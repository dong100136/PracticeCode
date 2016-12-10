from flask import Flask, request, render_template
from flask_socketio import SocketIO,emit

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


@app.route("/cpu", methods=['POST'])
def cpu_of_time():
    start = request.values.get('start', '')
    with connection.cursor() as cursor:
        sql = "select * from cpu where time >%s"
        print(sql)
        cursor.execute(sql, (start))
        rs = cursor.fetchall()
        j = []
        for item in rs:
            temp = {}
            temp['time'] = str(item[0])
            temp['sys_cpu'] = item[1]
            temp['user_cpu'] = item[2]
            temp['idle_cpu'] = item[3]
            j.append(temp)
    return json.dumps(j)


@app.route("/wspage")
def wspage():
    return render_template('wspage.html')


@socketio.on("message",namespace='/ws')
def echo(message):
    print(message)
    emit("message",message)

@socketio.on("connect",namespace="/ws")
def connect():
    emit("message",{'data':"test"})

if __name__ == "__main__":
    app.debug = True
    # t = threading.Thread(target=monitor.get_and_save_cpu_statis)
    # t.start()
    socketio.run(app)
