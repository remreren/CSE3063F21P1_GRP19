from model.course import Course


class TakenCourse(Course):
    def __init__(self):
        self.__letterGrade: str = None

    def getLetterGrade(self) -> str:
        return self.__letterGrade