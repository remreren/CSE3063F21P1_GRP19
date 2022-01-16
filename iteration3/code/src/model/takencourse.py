from src.model.course import Course


class TakenCourse(Course):
    def __init__(self, course: Course = None):
        if course is not None:
            self.__courseCode = course.getCourseCode()
            self.__courseName = course.getCourseName()
            self.__credit = course.getCredit()
            self.__semester = course.getSemester()
            self.__courseType = course.getCourseType()
            self.__prerequisites = course.getPrerequisites()
        
        self.__letterGrade: str = None
        

    def setLetterGrade(self, letterGrade: str):
        self.__letterGrade = letterGrade

    def getLetterGrade(self) -> str:
        return self.__letterGrade