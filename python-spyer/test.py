import json
import requests


with open("./tv.html",'r') as f:
    content = json.loads(f.read())['subjects']
id_list = [x['id'] for x in content]

for id in id_list:
    print("正在处理:",id,"....")
    url = 'http://api.douban.com//v2/movie/subject/'+id
    header = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36','Server':'dae','Content-Type':'text/html;charset=utf-8','Connection':'close',\
        'X-Douban-Mobileapp':'0','Expires':'Sun, 1 Jan 2006 01:00:00 GMT',\
        'X-Douban-Newbid':'qNL7av2zyKI','Pragma':'no-cache','Cache-Control':'t-revalidate, no-cache, private',\
        'P3P' : 'CP="IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT"',\
        'X-Douban-Splittest':'Set-Cookied="qNL7av2zyKI"; path=/; domain=.douban.com; expires=Wed, 23-Nov-2016 01:48:13 GMT',\
        'Set-Cookie':'th=/; domain=.douban.com; expires=Wed, 23-Nov-2016 01:48:13 GMT',\
        'X-DAE-Node':'sindar10b'}
    data = requests.get(url,headers=header).json()
    item = {}
    item['id'] = data['id']
    item['title'] = data['title']
    item['pingfen'] = data['rating']['average']
    item['year'] = data['year']
    item['countries'] ='/'.join(data['countries'])

    with open('./data.txt','a') as f:
        f.write("%s#%s#%s#%s#%s\n" % (item['id'],item['title'],item['year'],item['pingfen'],item['countries']))
