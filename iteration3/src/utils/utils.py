from typing import List

from idna import core
from model.config import Config
from model.course import Course
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

def convert_dict_to_course(cours: dict) -> Course:
    course_code: str
    course_name: str
    credit: int
    semester: int
    course_type: str
    prerequisites: List[Course] = []
    
    if "courseCode" in cours:
        course_code = cours["courseCode"]

    if "courseName" in cours:
        course_name = cours["courseName"]
    
    if "credit" in cours:
        credit = cours["credit"]

    if "semester" in cours:
        semester = cours["semester"]

    if "type" in cours:
        course_type = cours["type"]

    if "prerequisites" in cours:
        prerequisites = [course for course in cours["prerequisites"]]

    return Course(course_code, course_name, credit, prerequisites, semester, course_type)

def convert_dict_to_config(conf: dict) -> Config:
    student_generation: bool
    registration_term: str
    curriculum: List[Course]
    electives: List[Elective]

    if "studentGeneration" in conf:
        student_generation = conf["studentGeneration"]

    if "registrationTerm" in conf:
        registration_term = conf["registrationTerm"]

    if "curriculum" in conf:
        curriculum = [convert_dict_to_course(course) for course in conf["curriculum"]]

    if "electives" in conf:
        electives = [convert_dict_to_elective(elect) for elect in conf["electives"]]
    
    return Config(student_generation, registration_term, curriculum, electives)