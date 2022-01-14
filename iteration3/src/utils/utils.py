from typing import List
from model.elective import Elective
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

def convert_dict_to_elective(elect: dict) -> Elective:
    course_type: str
    credit_requirement: int
    quota: int
    courses: List[str]

    if "type" in elect:
        course_type = elect["type"]

    if "creditRequirement" in elect:
        credit_requirement = elect["creditRequirement"]

    if "quota" in elect:
        quota = elect["quota"]

    if "courses" in elect:
        courses = elect["courses"]

    return Elective(course_type, credit_requirement, quota, courses)