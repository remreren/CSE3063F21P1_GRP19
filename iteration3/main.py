import json
from app import App
from model.elective import Elective
from model.student import Student
from utils.utils import convert_file_to_student

if __name__ == '__main__':
    app: App = App()
    app.start()
    pass

with open('./test.json') as f:
    std: Student = json.load(f, object_hook=convert_file_to_student)
    elect: Elective = Elective("TE", 96, 100, ["hello"])
    print(std)
    print(elect)
