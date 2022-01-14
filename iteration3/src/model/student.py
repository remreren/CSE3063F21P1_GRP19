class Student(object):
    def __init__(self):
        pass

    def __init__(self, stdid, name, surname, semester):
        self.stdid = stdid
        self.name = name
        self.surname = surname
        self.semester = semester
        self.currentCourses = []
        self.transcripts = []

    def __str__(self):
        return f"Student({self.stdid}, {self.name}, {self.surname}, {self.semester})"