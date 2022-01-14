from model.student import Student

def convert_file_to_student(std: dict) -> Student:
    stdid: int
    name: str
    surname: str
    semester: int
    
    if "id" in std:
        stdid = std["id"]
    
    if "name" in std:
        name = std["name"]

    if "surname" in std:
        surname = std["surname"]

    if "semester" in std:
        semester = std["semester"]

    return Student(stdid, name, surname, semester)
