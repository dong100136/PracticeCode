import time
import datetime
import re
import os
import pymysql.cursors


global connection
connection = pymysql.connect(
    host="localhost", port=3306, user='bupt', password='bupt', db='stone')
connection.autocommit(True)

def get_cpu_usage():
    command = "top|head -n 10|grep 'CPU usage'"
    msg = os.popen(command).read()
    ptn = re.compile("\d+\.\d+")
    return ptn.findall(msg)


def get_and_save_cpu_statis():
    global connection
    # delete all data
    with connection.cursor() as cursor:
        cursor.execute("delete from cpu")

    while True:
        sql = "insert into cpu(time,user_cpu,sys_cpu,idle_cpu) values(%s,%s,%s,%s)"
        with connection.cursor() as cursor:
            rs = get_cpu_usage()
            t = datetime.datetime.now().strftime("%y-%m-%d %H:%M:%S")
            cursor.execute(sql, (t, rs[0], rs[1], rs[2]))
        time.sleep(1)

def get_cpu_statis_from_db(start_time):
    with connection.cursor() as cursor:
        sql = "select * from cpu where time >%s"
        print(sql)
        cursor.execute(sql, (start_time))
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


if __name__ == "__main__":
    get_and_save_cpu_static()
