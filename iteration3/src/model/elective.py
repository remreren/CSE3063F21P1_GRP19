from typing import List


class Elective(object):
    def __init__(self):
        pass

    def __init__(self, course_type: str, credit_requirement: int, quota: int, courses: List[str]):
        self.course_code = "" ##TODO remove this line
        self.course_type = course_type
        self.credit_requirement = credit_requirement
        self.quota = quota
        self.courses = courses

    def get_elective_quantity(self) -> int:
        return len(self.courses)

    def is_quota_full(self) -> bool: ##TODO review function.
        for course in self.courses:
            if course == self.course_code:
                if self.quota <= 5:
                    return True
        return False

    def set_new_term(self):
        for course in self.courses:
            course = "" ##TODO change this

    def __str__(self):
        return f"Elective(type={self.course_type}, credit_requirement={self.credit_requirement}, quota={self.quota}, courses={self.courses})"