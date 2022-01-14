import json

with open("data.json") as file:
    db = json.load(file)
    items = []
    a=[db_item for db_item in db]
    print(len(a))
