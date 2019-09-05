import requests
from multiprocessing import Pool
from multiprocessing import Process
from time import sleep

baseUrl = 'http://localhost:8080'
header = {'Content-Type': 'application/json'}
categories = ['news', 'art', 'blog', 'music', 'sport']

res = requests.get(baseUrl + '/login', headers=header).json()
print(res)
header['token'] = res['token']

def doThing(category):
    page = 1

    while True:
        postFix = '/' + category + '/feature/' + str(page)
        url = baseUrl + postFix
        pres = requests.get(url, headers=header).json()
        # print(category, pres)

        if 'score' in pres:
            print(pres['score'])
            break

        if pres['status'] == 402:
            print('Something is wrong[%s]' % category)
            continue

        if postFix == pres['next_url']:
            # print('%s %s' % (postFix, pres['next_url']))
            sleep(0.1)
            continue

        save = []
        delete = []
        images = pres['images']

        for i in images:
            name, action = i.split()
            
            if action == 'SAVE':
                save.append(name)
            else:
                delete.append(name)

        if len(save) > 0:
            pres = requests.post(baseUrl + '/' + category + '/save', headers=header, json={'images': save}).json()
        if len(delete) > 0:
            pres = requests.post(baseUrl + '/' + category + '/delete', headers=header, json={'images': delete}).json()
        page += 1

# pool = Pool(len(categories))
# pool.map(doThing, categories)

proc = []
for cate in categories:
    p = Process(target=doThing, args=(cate,))
    proc.append(p)
    p.start()

for p in proc:
    p.join()
