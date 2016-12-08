from flask import Flask, request,render_template
import pymysql.cursors
import json
import monitor
import threading

app = Flask(__name__)

app.secret_key="stone"
app.register_blueprint(book_bp)

connection = pymysql.connect(host='localhost',user='root',password='stone',db='test')
connection.autocommit(True)

@app.route("/index")
def index():
    return render_template("index.html")

@app.route("/cpu",methods=['POST'])
def cpu_of_time():
    start = request.values.get('start','')
    with connection.cursor() as cursor:
        sql = "select * from cpu where time >%s"
        print(sql)
        cursor.execute(sql,(start))
        rs= cursor.fetchall()
        j = []
        for item in rs:
            temp={}
            temp['time']=str(item[0])
            temp['sys_cpu'] = item[1]
            temp['user_cpu'] = item[2]
            temp['idle_cpu'] = item[3]
            j.append(temp)
    return json.dumps(j)


if __name__ == "__main__":
    app.debug = True
    t = threading.Thread(target=monitor.get_and_save_cpu_statis)
    t.start()
    app.run()
