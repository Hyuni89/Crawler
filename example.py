import requests

baseUrl = 'http://localhost:8080'
header = {'Content-Type': 'application/json'}

res = requests.get(baseUrl + '/login', headers=header).json()
print(res)
header['token'] = res['token']

category = 'news'
nurl = '/news/feature/1'

while True:
    res = requests.get(baseUrl + nurl, headers=header).json()
    print(res)

    if 'score' in res:
        break

    purl = res['prev_url']
    nurl = res['next_url']
    images = res['images']

    save = []
    delete = []
    
    for i in images:
        name, action = i.split()
        
        if action == 'SAVE':
            save.append(name)
        else:
            delete.append(name)

    res = requests.post(baseUrl + '/' + category + '/save', headers=header, json={'images': save}).json()
    print(res)
    res = requests.post(baseUrl + '/' + category + '/delete', headers=header, json={'images': delete}).json()
    print(res)
