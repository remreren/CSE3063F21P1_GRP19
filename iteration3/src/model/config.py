from typing import List
from model.elective import Elective
from model.student import Student


class Config(object):
    def __init__(self, student_generation: bool, registration_term: str, curriculum: List[Student], electives: List[Elective]):
        self.student_generation = student_generation
        self.registration_term = registration_term
        self.curriculum = curriculum
        self.electives = electives

    def __str__(self):
        return f"Config(student_generation={self.student_generation}, registration_term={self.registration_term}, curriculum={self.curriculum}, electives={self.electives})"