from typing import List

from model.course import Course

class Elective(Course):
    def __init__(self):
        pass

    def __init__(self, course_type: str, credit_requirement: int, quota: int, courses: List[Course]):
        self.__course_type = course_type
        self.__credit_requirement = credit_requirement
        self.__quota = quota
        self.__courses = courses

    def get_elective_quantity(self) -> int:
        return len(self.__courses)

    def is_quota_full(self, cs: Course) -> bool:
        for course in self.__courses:
            if course.__courseCode == cs.__courseCode:
                if self.__quota <= self.getEnrolledSize():
                    return True
        return False

    def set_new_term(self):
        for course in self.__courses:
            course.__stdQuotaProblem = []
            course.__enrolledList = []

    def getQuota(self):
        return self.__quota

    def setSemester(self, course: Course, semester: int):
        course.__semester = semester

    def set_semester(self, course: Course, semester: int):
        course.__semester = semester

    def __str__(self):
        return f"Elective(type={self.__course_type}, credit_requirement={self.__credit_requirement}, quota={self.__quota}, courses={self.__courses})"

    def __repr__(self):
        return f"Elective(type={self.__course_type}, credit_requirement={self.__credit_requirement}, quota={self.__quota}, courses={self.__courses})"