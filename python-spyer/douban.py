import requests,json,re
from bs4 import BeautifulSoup as BS

item_num = 300

def get_info(session,tv_url):
    content = session.get(tv_url).content.decode('utf-8')
    item = parse_html(content)
    save(item)
    return item


def parse_html(html):
    soup =  BS(html,'html.parser')
    info = soup.find_all(id = "info")[0]
    _,*info_list,_ = info.text.split('\n')

    shoubo_pattern = re.compile('\d{4}-\d{2}-\d{2}')
    item = {'daoyan':'','bianju':'','zhuyan':'','leixing':'','diqu':'','shoubo':''}
    for x in info_list:
        k,v,*_ = x.split(":")
        if k=='导演':
            item['daoyan'] = v
        elif k=='编剧':
            item['bianju'] = v
        elif k=='主演':
            item['zhuyan'] = v
        elif k=='类型':
            item['leixing'] = v
        elif k=='制片国家/地区':
            item['diqu'] = v
        elif k=='首播':
            c = re.findall(shoubo_pattern,v)
            item['shoubo'] = c[0]
    item['name'] = soup.find_all('h1')[0].span.text 
    item['pingfen'] = soup.find_all('strong',class_='ll rating_num')[0].text
    item['renshu'] =soup.find_all('a',class_='rating_people')[0].span.text
    print(item)
    return item

def save(filename,item):
    with open(filename,'w+') as f:
        f.write('%s#%s#%s#%s#%s#%s#%s#%s#%s\n' % \
        (item['name'],item['daoyan'],item['bianju'],item['zhuyan'],item['leixing'],item['diqu'],item['shoubo'],item['pingfen'],item['renshu']))

if __name__ == '__main__':
    session = requests.Session()
    url  = "https://movie.douban.com/j/search_subjects?type=tv&tag=%E7%83%AD%E9%97%A8&sort=recommend&page_limit="+str(item_num)+"&page_start=0"
    content = session.get(url).content.decode('utf-8')
    content = json.loads(content)
    tv_list = [x['url'] for x in content['subjects']]

    # result = []
    # for tv in tv_list:
    #     print('正在处理',tv)
    #     result.append(get_info(session,tv))