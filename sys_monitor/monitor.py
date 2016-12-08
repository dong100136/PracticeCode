import time
import datetime
import re
import os
import pymysql.cursors


def get_cpu_usage():
    command = "top|head -n 10|grep 'CPU usage'"
    msg = os.popen(command).read()
    ptn = re.compile("\d+\.\d+")
    return ptn.findall(msg)


def get_and_save_cpu_statis():
    connection = pymysql.connect(
        host="localhost", port=3306, user='bupt', password='bupt', db='stone')
    connection.autocommit(True)
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


if __name__ == "__main__":
    get_and_save_cpu_static()
