from typing import List
from model.student import Student


class Course(object):
    def __init__(self, course_code: str, course_name: str, credit: int, prerequisites: List[object], semester: int, course_type: str):
        self.course_code = course_code
        self.course_name = course_name
        self.credit = credit
        self.semester = semester
        self.course_type = course_type
        self.prerequisites: List[Course] = prerequisites

        self.enrolled_list: List[Student] = []
        self.std_quota_problem: List[Student] = []
        self.std_prereq_problem: List[Student] = []
        self.feedback: List[str] = []

    def enroll_student(self, student: Student):
        self.enrolled_list.append(student)

    def get_enrolled_size(self) -> int:
        return len(self.enrolled_list)

    def get_quota_problem_size(self) -> int:
        return len(self.std_quota_problem)

    def __str__(self):
        return f"Course(course_code={self.course_code}, course_name={self.course_name}, credit={self.credit}, semester={self.semester}, course_type={self.course_type}"
