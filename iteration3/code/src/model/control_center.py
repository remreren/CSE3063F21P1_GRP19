
import fnmatch
import json
from os import listdir
import random
from typing import List

from model.config import Config
from model.student import Student
from model.takencourse import TakenCourse
from model.transcript import Transcript
from utils.utils import convert_dict_to_config, convert_dict_to_student


class ControlCenter:

    def __init__(self):
        self.__config: Config = None
        self.__data: Data = None
        self.__letterNotes: List[str]= ["AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD", "FF"]


    def start(self):
        self.readInput()
        self.readNames()
        if len(fnmatch.filter(listdir("./output"), "150*.json")) == 0 or self.__config.studentGeneration:
            self.createStudents()
        else:
            self.readStudents()
            self.gradesOfLastSemester()
            self.newTermRegistration()
        self.writeOutputJson()


    # assigns grades to the last registered (non-graded yet) courses before new
    # registration
    def gradesOfLastSemester(self):
        random.seed(123155)
        for st in self.__data.getStudents():
            numberOfTrc = len(st.getTranscripts())
            # if the term parameter isn't changed, no grades will be assigned
            if (self.__config.getRegistrationTerm().casefold() == "fall" and numberOfTrc % 2 != 0 and numberOfTrc != 8) or (self.__config.getRegistrationTerm().casefold() == "spring" and numberOfTrc % 2 == 0 and numberOfTrc!=8):
                return False

            trscript = st.getTranscripts()[numberOfTrc - 1] # last semesterCourses in transcript
            for tkc in trscript.getSemesterCourses():
                rnd = random.randint(0, 8)
                tkc.setLetterGrade(self.__letterNotes[rnd])
            trscript.calculate()
            st.calculate()
            st.save()
        return True

    # students register new term (term parameter changed in input.json)
    def newTermRegistration(self):
        for student in self.__data.getStudents():
            lastRegisteredSem = len(student.getTranscripts())
            newSem = lastRegisteredSem + 1

            # if the term parameter isn't changed, no new registration will be done
            if (self.__config.getRegistrationTerm().casefold() == "fall" and lastRegisteredSem % 2 != 0 and lastRegisteredSem!=8) or (self.__config.getRegistrationTerm().casefold() == "spring" and lastRegisteredSem % 2 == 0 and lastRegisteredSem!=8):
                return False

            if lastRegisteredSem == 8:
                continue # (for now) there will be no new registration for students who took all courses

            trscript = Transcript()
            for c in self.__config.getCurriculum():
                quota = True
                random.seed(342342)
                if newSem == c.getSemester() and student.canTakeCourse(c):
                    quota = True
                    #Elective Part Starts
                    if c.getType() is not None:
                        for e in self.__config.getElectives():
                            if e.type == c.getType():
                                electiveRandom = random.randint(0, self.getElectiveQuota(e.type) - 1)
                                while student.isStudentEnrolled(e.courses[electiveRandom]):
                                    electiveRandom = random.randint(0, self.getElectiveQuota(e.type) - 1) #creates for random course inside electives
                                if e.isQuotaFull(e.courses[electiveRandom]):
                                    e.setSemester(e.courses[electiveRandom], c.getSemester())
                                    e.courses[electiveRandom].addQuotaProblem(student)
                                    student.addFeedbackQuota(e.courses[electiveRandom])
                                    quota = False
                                else:
                                    c = e.getCourses()[electiveRandom]
                                    c.setType(e.type)
                    #Elective Part Ends
                    tc = None
                    if quota:
                        tc = TakenCourse(c) # newly registered courses has no grade attribute
                        trscript.addCourse(tc)
                        c.enrollStudent(student)
            trscript.setSemester(newSem)
            trscript.calculate()
            student.addTranscript(trscript)
            student.calculate()
            student.save()

        return True
    def setElectivesNull(self):
        for e in self.__config.getElectives():
            e.setNewTerm()

    def readStudents(self):
        self.__data.setStudents([])
        for fname in fnmatch.filter(listdir("./output"), "150*.json"):
            with open('./output/' + fname) as f:
                st = convert_dict_to_student(json.load(f))
                st.calculate()
                self.__data.students.append(st)

    def readNames(self):
        with open("./input/allStudentNames.json") as f:
            self.__data.names = json.load(f)

    def readInput(self):
        with open("./input/input.json") as f:
            self.__config = convert_dict_to_config(json.load(f))

    def createStudents(self):
        num = 150121001
        index = 0
        sem = 2 if (self.__config.getRegistrationTerm().toLowerCase() == "spring") else 1
        while sem <= 8:
            _id = num
            while id < num + 70:
                std = Student(self.__data.getStudent(index))
                std.setId(id)
                self.getCoursesBySemester(sem, std)
                self.__data.addStudent(std)
                std.save()
                index += 1
                _id += 1
            num = num - 1000
            sem += 2

    def getCoursesBySemester(self, semester, st):
        tc = None
        random.seed(3245435)
        for i in range(1, semester + 1):
            trscript = Transcript()
            for c in self.__config.getCurriculum():
                # Checks for semester
                if i == c.getSemester():
                    rnd = random.randint(0, 8)
                    newEnrollment = False
                    quota = True
                    # Elective Part Starts
                    if c.getType() is not None:
                        for e in self.__config.getElectives():
                            if e.type is c.getType():
                                electiveRandom = random.randint(0, self.getElectiveQuota(c.getType()) - 1)  # creates for random course inside electives
                                while st.isStudentEnrolled(e.getCourses()[electiveRandom]):
                                    electiveRandom = random.randint(0, self.getElectiveQuota(c.getType()) - 1)  # creates for random course inside electives
                                e.setSemester(e.getCourses()[electiveRandom], c.getSemester())
                                if e.isQuotaFull(e.getCourses()[electiveRandom]):
                                    e.getCourses()[electiveRandom].addQuotaProblem(st)
                                    st.addFeedbackQuota(e.getCourses()[electiveRandom])
                                    quota = False
                                else:
                                    c = e.getCourses()[electiveRandom]
                    # Elective Part Ends
                    if i == semester:
                        tc = TakenCourse(c)  # newly registered courses has no grade attribute
                        newEnrollment = True
                    else:
                        tc = TakenCourse(c, self.__letterNotes[rnd])

                    if st.canTakeCourse(c):
                        if quota:
                            trscript.addCourse(tc)
                        if newEnrollment and quota:
                            c.enrollStudent(st)
            trscript.setSemester(i)
            trscript.calculate()
            st.addTranscript(trscript)
        st.calculate()

    def getElectiveQuota(self, type):
        ret = 0
        for e in self.__config.getElectives():
            if type == e.type:
                ret = e.getElectiveQuantity()
        return ret

    # writes a general feedback for all students on the screen and into OUTPUT.json
    # file under output folder
    def writeOutputJson(self):
        outputObj = Output()
        for c in self.__config.getCurriculum():
            for s in c.getFeedback():
                print(s)
                outputObj.addFeedback(s)
        for e in self.__config.getElectives():
            for c in e.courses:
                for s in c.getFeedback():
                    print(s)
                    outputObj.addFeedback(s)
        with open("./output/output.json", "w") as f:
            output = json.dumps(outputObj)
            f.write(output)



class Output:
    def __init__(self):
        self.__feedbackList = None

    def addFeedback(self, feedback):
        if self.__feedbackList is None:
            self.__feedbackList = []
        self.__feedbackList.append(feedback)

class Data:
    def __init__(self):
        self.__students: List[Student] = []

    def getStudents(self) -> List[Student]:
        return self.__students

    def getStudent(self, index: int) -> Student:
        return self.__students[index]

    def addStudent(self, student: Student):
        self.__students.append(student)

    def setStudents(self, students: List[Student]):
        self.__students = students
