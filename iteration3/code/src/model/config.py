from typing import List
from src.model.course import Course
from src.model.elective import Elective
from src.model.student import Student


class Config(object):
    def __init__(self, student_generation: bool, registration_term: str, curriculum: List[Course], electives: List[Elective]):
        self.__studentGeneration = student_generation
        self.__registrationTerm = registration_term
        self.__curriculum = curriculum
        self.__electives = electives

    def setStudentGeneration(self, studentGeneration: bool):
        self.__studentGeneration = studentGeneration

    def setRegistrationTerm(self, registrationTerm: str):
        self.__registrationTerm = registrationTerm

    def setCurriculum(self, curriculum: List[Course]):
        self.__curriculum = curriculum

    def setElectives(self, electives: List[Elective]):
        self.__electives = electives
    
    def getStudentGeneration(self):
        return self.__studentGeneration

    def getRegistrationTerm(self):
        return self.__registrationTerm

    def getCurriculum(self):
        return self.__curriculum

    def getElectives(self):
        return self.__electives

    def __str__(self):
        return f"Config(student_generation={self.__studentGeneration}, registration_term={self.__registrationTerm}, curriculum={self.__curriculum}, electives={self.__electives})"