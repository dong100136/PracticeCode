import os
import re
import requests
from PIL import Image
import pytesseract
from bs4 import BeautifulSoup as BS
from prettytable import PrettyTable as PT

username = ''
password = '' 

def getCode(session):
    print("正在获取验证码...")
    imgUrl = "http://jwxt.bupt.edu.cn/validateCodeAction.do?random=1234"
    img = session.get(imgUrl)
    f = open("./img.jpg", "wb")
    f.write(img.content)
    f.close()
    img = Image.open("./img.jpg")
    imgCode = pytesseract.image_to_string(img)
    print("验证码：%s" % imgCode)
    return imgCode


def login(session):
    print("=" * 100)
    print("正在登陆...")
    while True:
        imgCode = getCode(session)

        url = "http://jwxt.bupt.edu.cn/jwLoginAction.do"

        data = {"zjh": username, "mm": password, "v_yzm": imgCode}

        r = session.post(url, data=data)
        if len(r.content) > 4000:
            print("登陆失败...")
        else:
            print("登陆成功...")
            break


def printClassTable(session):
    print('=' * 100)
    print('正在获取课程表...')
    # 本学期的课程表
    url = "http://jwxt.bupt.edu.cn/xkAction.do?actionType=6"
    r = session.get(url)
    soup = BS(r.content, 'html.parser')
    table = soup.find_all("td", class_="pageAlign")[1]
    class_table = [];
    for item in table.find_all('tr'):
        if item.parent.name != 'thead':
            coruse = {}
            node = item.find_all('td')
            coruse['name'] = node[2].string
            coruse['point'] = node[4].string
            coruse['room'] = node[17].string
            coruse['type'] = node[5].string
            coruse['week'] = node[11].string
            coruse['teacher'] = node[7].string
            coruse['weekday'] = node[12].string
            coruse['time'] = node[13].string
            coruse['long'] = node[14].string

            for k in coruse.keys():
                coruse[k] = coruse[k].split('\xa0')[-1]
                coruse[k] = coruse[k].replace(' ','')

            class_table.append(coruse)

    table = PT(['课程名字', '教师', '上课周', '星期', '上课时间', '节数','课室' ,'学分', '类型'])
    for item in class_table:
        table.add_row(
            [item['name'], item['teacher'], item['week'], item['weekday'], item['time'], item['long'], item['room'],item['point'],
             item['type']])

    print(table)

    # 按星期打印课程表
    # 初始化数组
    class_table_of_week = []
    for i in range(7):
        weekday = []
        for j in range(12):
            time = None
            weekday.append(time)
        class_table_of_week.append(weekday)

    for item in class_table:
        for t in range(int(item['long'])):
            class_table_of_week[int(item['weekday'])-1][int(item['time'])+t-1]=item;

    table = PT(['节数','星期一','星期二','星期三','星期四','星期五','星期六','星期日'])
    for i in range(12):
        content = [i+1]
        for item in class_table_of_week:
            if item[i]==None:
                content.append('')
            else:
                content.append(item[i]['name']+'\n('+item[i]['room']+')')
        table.add_row(content)
    print(table)

def print_fenshu(fenshu):
    t = PT(['序号','名称','类型','学分','成绩'])
    i=1
    for item in fenshu:
        t.add_row([i,item['name'],item['type'],item['point'],item['grade']])
        i=i+1
    print(t)

def getGrade(session):
    url = "http://jwxt.bupt.edu.cn/gradeLnAllAction.do?type=ln&oper=sxinfo&lnsxdm=001"
    webPage = session.get(url)
    soup = BS(webPage.content.decode('gbk'),'html.parser')
    soup = soup.find_all('td',class_='pageAlign')

    # fenshu中储存必修，选修，任选三种类型的成绩
    fenshu = []
    for item in soup:
        temp = []
        for item2 in item.find_all('tr'):
            if item2.parent.name!='thead':
                td = item2.find_all('td')
                if len(td)>7:
                    c={'name':td[2].string,'type':td[5].string,'point':td[4].string,'grade':td[6].p.string}
                    for k in c.keys():
                        c[k]=c[k].replace(' ','')
                        c[k]=c[k].replace('\n','')
                        c[k]=c[k].replace('\r','')
                        c[k]=c[k].replace('\xa0','')
                    temp.append(c)
        fenshu.append(temp)

    # 计算平均分
    s=0.0
    p=0
    for i in fenshu[0:1]:
        for item in i:
            try:
                s = s + float(item['point'])*float(item['grade'])
                p = p + float(item['point'])
            except Exception:
                print('数据格式出错：',item)
                continue
    mean=s/p
    print('必修课：%d 门  选修课: %d 门 任选课: %d 门 加权平均分（不包括任选课）：%.2f' % (len(fenshu[0]),len(fenshu[1]),len(fenshu[2]),mean))
    show_menu_fenshu(fenshu)

def search_fenshu(fenshu):
    while True:
        result = []
        name = input('输入需要搜索的课程名称(输入q返回)：')
        if name=='q':
            break
        
        for i in fenshu:
            for item in i:
                if name in item['name']:
                    result.append(item)
        print('共找到%d门课程成绩' % len(result))
        print_fenshu(result)

def show_menu_fenshu(fenshu):
    while True:
        print('=' * 100)
        print('选择功能')
        print('[0]打印必修成绩')
        print('[1]打印选修成绩')
        print('[2]打印任选课成绩')
        print('[3]搜索课程')
        print('[q]返回')

        key = input('输入需要功能的序号: ');
        if key == '0':
            print_fenshu(fenshu[0])
        elif key == '1':
            print_fenshu(fenshu[1]) 
        elif key == '2':
            print_fenshu(fenshu[2])
        elif key == '3':
            search_fenshu(fenshu)
        elif key == 'q':
            break

def show_menu(session):
    while True:
        print('=' * 100)
        print('选择功能')
        print('[0]打印本学期的课程表')
        print('[1]查看成绩表')
        print('[q]退出')

        key = input('输入需要功能的序号: ');
        if key == '0':
            printClassTable(session)
        elif key == '1':
            getGrade(session)    
        elif key == 'q':
            print('退出程序')
            break


if __name__ == '__main__':
    if username=='':
        username = input("输入学号：")
        password = input("输入密码：")
        
    session = requests.Session()
    login(session)
    show_menu(session)