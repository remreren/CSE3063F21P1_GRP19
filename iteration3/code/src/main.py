import json
from app import App
from model.config import Config
from model.elective import Elective
from model.student import Student
from utils.utils import convert_dict_to_config, convert_file_to_student

if __name__ == '__main__':
    app: App = App()
    app.start()
    pass

def test(inp):
    print(inp)

with open("./input.json") as inp:
    test = json.load(inp)
    print(convert_dict_to_config(test))
    # print("studentGeneration" in test)s
    # conf: Config = json.load(inp, object_hook=convert_dict_to_config)
    # print(conf)

with open('./test.json') as f:
    std: Student = json.load(f, object_hook=convert_file_to_student)
    elect: Elective = Elective("TE", 96, 100, ["hello"])
    print(std)
    print(elect)