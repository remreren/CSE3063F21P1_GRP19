from typing import List

from model.course import Course

class Elective(Course):
    def __init__(self):
        pass

    def __init__(self, course_type: str, credit_requirement: int, quota: int, courses: List[Course]):
        self.course_type = course_type
        self.credit_requirement = credit_requirement
        self.quota = quota
        self.courses = courses

    def get_elective_quantity(self) -> int:
        return len(self.courses)

    def is_quota_full(self, cs: Course) -> bool:
        for course in self.courses:
            if course.course_code == cs.course_code:
                if self.quota <= self.get_enrolled_size():
                    return True
        return False

    def set_new_term(self):
        for course in self.courses:
            course.std_quota_problem = []
            course.enrolled_list = []

    def set_semester(self, course: Course, semester: int):
        course.semester = semester

    def __str__(self):
        return f"Elective(type={self.course_type}, credit_requirement={self.credit_requirement}, quota={self.quota}, courses={self.courses})"