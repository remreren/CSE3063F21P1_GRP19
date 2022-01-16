from model.course import Course


class TakenCourse(Course):
    def __init__(self):
        self.__letterGrade: str = None

    def setLetterGrade(self, letterGrade: str):
        self.__letterGrade = letterGrade

    def getLetterGrade(self) -> str:
        return self.__letterGrade