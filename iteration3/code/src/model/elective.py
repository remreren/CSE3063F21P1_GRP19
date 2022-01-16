from typing import List

from model.course import Course

class Elective(Course):
    def __init__(self):
        pass

    def __init__(self, course_type: str, credit_requirement: int, quota: int, courses: List[Course]):
        self.__courseType: str = course_type
        self.__creditRequirement: int = credit_requirement
        self.__quota: int = quota
        self.__courses: List[Course] = courses

    def isQuotaFull(self, cs: Course) -> bool:
        for course in self.__courses:
            if course.getCourseCode() == cs.getCourseCode:
                if self.__quota <= self.getEnrolledSize():
                    return True
        return False

    def setNewTerm(self):
        for course in self.__courses:
            course.__stdQuotaProblem = []
            course.__enrolledList = []

    def setSemester(self, course: Course, semester: int):
        course.setSemester(semester)

    def getCourseType(self) -> str:
        return self.__courseType

    def getCreditRequirement(self) -> int:
        return self.__creditRequirement

    def getQuota(self) -> int:
        return self.__quota

    def getCourses(self) -> List[Course]:
        return self.__courses

    def getElectiveQuantity(self) -> int:
        return len(self.__courses)

    def __str__(self):
        return f"Elective(type={self.__courseType}, credit_requirement={self.__creditRequirement}, quota={self.__quota}, courses={self.__courses})"

    def __repr__(self):
        return f"Elective(type={self.__courseType}, credit_requirement={self.__creditRequirement}, quota={self.__quota}, courses={self.__courses})"