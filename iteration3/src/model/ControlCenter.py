
class ControlCenter:

    def __init__(self):
        
        self.__config = None
        self.__data = None
        self.__gson = Utils.getInstance().getGson()
        self.__students = []
        self.__transcriptFilter = None
        self.__letterNotes = ["AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD", "FF"]


    def start(self):
        self.readInput()
        self.readNames()
        if Utils.getInstance().getOutputPath().listFiles(self.__transcriptFilter).length == 0 or self.__config.studentGeneration:
            self.createStudents()
        else:
            self.readStudents()
            self.gradesOfLastSemester()
            self.newTermRegistration()
        self.writeOutputJson()

    class FilenameFilterAnonymousInnerClass():

        def accept(self, dir, name):
            return name.startswith("150") and name.endswith(".json")


    # assigns grades to the last registered (non-graded yet) courses before new
    # registration
    def gradesOfLastSemester(self):
        for st in self.__data.students:
            numberOfTrc = len(st.getTranscripts())
            # if the term parameter isn't changed, no grades will be assigned
            if (self.__config.registrationTerm.casefold() == "fall" and math.fmod(numberOfTrc, 2) != 0 and numberOfTrc!=8) or (self.__config.registrationTerm.casefold() == "spring" and math.fmod(numberOfTrc, 2) == 0 and numberOfTrc!=8):
                return False

            trscript = st.getTranscripts()[numberOfTrc - 1] # last semesterCourses in transcript
            for tkc in trscript.getSemesterCourses():
                rnd = self.__random.nextInt(9)
                tkc.setLetterGrade(self.__letterNotes[rnd])
            trscript.calculate()
            st.calculate()
            st.save()
        return True

    # students register new term (term parameter changed in input.json)
    def newTermRegistration(self):
        #setElectivesNull();//Electives new term
        for student in self.__data.students:
            lastRegisteredSem = len(student.getTranscripts())
            newSem = lastRegisteredSem + 1

            # if the term parameter isn't changed, no new registration will be done
            if (self.__config.registrationTerm.casefold() == "fall" and math.fmod(lastRegisteredSem, 2) != 0 and lastRegisteredSem!=8) or (self.__config.registrationTerm.casefold() == "spring" and math.fmod(lastRegisteredSem, 2) == 0 and lastRegisteredSem!=8):
                return False

            if lastRegisteredSem == 8:
                continue # (for now) there will be no new registration for students who took all courses

            trscript = coursetracking.models.Transcript()
            for c in self.__config.curriculum:
                quota = True
                rand = java.util.Random()
                if newSem == c.getSemester() and student.canTakeCourse(c):
                    quota = True
                    #Elective Part Starts
                    if c.getType() is not None:
                        for e in self.__config.electives:
                            if e.type == c.getType():
                                electiveRandom = rand.nextInt(self.getElectiveQuota(e.type))
                                while student.isStudentEnrolled(e.courses[electiveRandom]):
                                    electiveRandom = rand.nextInt(self.getElectiveQuota(e.type)) #creates for random course inside electives
                                if e.isQuotaFull(e.courses[electiveRandom]):
                                    e.setSemester(e.courses[electiveRandom], c.getSemester())
                                    e.courses[electiveRandom].addQuotaProblem(student)
                                    student.addFeedbackQuota(e.courses[electiveRandom])
                                    quota = False
                                else:
                                    c = e.courses[electiveRandom]
                                    c.setType(e.type)
                    #Elective Part Ends
                    tc = None
                    if quota:
                        tc = coursetracking.models.TakenCourse(c) # newly registered courses has no grade attribute
                        trscript.addCourse(tc)
                        c.enrollStudent(student)
            trscript.setSemester(newSem)
            trscript.calculate()
            student.addTranscript(trscript)
            student.calculate()
            student.save()

        return True
    def setElectivesNull(self):
        for e in self.__config.electives:
            e.setNewTerm()

    def readStudents(self):
        self.__data.students = []
        for f in Utils.getInstance().getOutputPath().listFiles(self.__transcriptFilter):
            if f.getName().endsWith(".json"):
                data = ""
                myReader = java.util.Scanner(f)
                while myReader.hasNextLine():
                    data += myReader.nextLine()
                myReader.close()
                st = self.__gson.fromJson(data, coursetracking.models.Student)
                st.calculate()
                self.__data.students.append(st)

    def readNames(self):
        input = Utils.getInstance().getResource("allStudentNames.json")
        data = ""
        myReader = Scanner(input)
        while myReader.hasNextLine():
            data += myReader.nextLine()
        myReader.close()
        self.data = gson.fromJson(data, Data)



    def readInput(self):
        input = Utils.getInstance().getResource("input.json")
        data = ""
        myReader = Scanner(input)
        while myReader.hasNextLine():
            data += myReader.nextLine()
        myReader.close()
        config = gson.fromJson(data, Config)

    def createStudents(self):
        num = 150121001
        index = 0
        sem = 2 if (config.registrationTerm.toLowerCase() == "spring") else 1
        while sem <= 8:
            id = num
            while id < num + 70:
                std = Student(data.students.get(index))
                std.setId(id)
                self.getCoursesBySemester(sem, std)
                students.add(std)
                std.save()
                index += 1
                id += 1
            num = num - 1000
            sem += 2

    def getCoursesBySemester(self, semester, st):
        tc = None
        for i in range(1, semester + 1):
            trscript = Transcript()
            for c in config.curriculum:
                # Checks for semester
                if i == c.getSemester():
                    rnd = self.__random.nextInt(9)
                    newEnrollment = False
                    quota = True
                    # Elective Part Starts
                    if c.getType() is not None:
                        rand = Random()
                        for e in config.electives:
                            if e.type is c.getType():
                                electiveRandom = rand.nextInt(
                                    self.getElectiveQuota(c.getType()))  # creates for random course inside electives
                                while st.isStudentEnrolled(e.courses.get(electiveRandom)):
                                    electiveRandom = rand.nextInt(
                                        self.getElectiveQuota(e.type))  # creates for random course inside electives
                                e.setSemester(e.courses.get(electiveRandom), c.getSemester())
                                if e.isQuotaFull(e.courses.get(electiveRandom)):
                                    e.courses.get(electiveRandom).addQuotaProblem(st)
                                    st.addFeedbackQuota(e.courses.get(electiveRandom))
                                    quota = False
                                else:
                                    c = e.courses.get(electiveRandom)
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
        for e in config.electives:
            if type == e.type:
                ret = e.getElectiveQuantity()
        return ret

    # writes a general feedback for all students on the screen and into OUTPUT.json
    # file under output folder
    def writeOutputJson(self):
        outputObj = Output()
        for c in config.curriculum:
            for s in c.getFeedback():
                print(s)
                outputObj.addFeedback(s)
        for e in config.electives:
            for c in e.courses:
                for s in c.getFeedback():
                    print(s)
                    outputObj.addFeedback(s)
        try:
            output = File(Utils.getInstance().getOutputPath(), "OUTPUT.json")
            if not output.exists():
                output.createNewFile()
            writer = FileWriter(output, False)
            writer.write(gson.toJson(outputObj))
            writer.close()
        except Exception as e:
            print(e)



    class Output:

        def __init__(self):
            self.feedbackList = None

        def addFeedback(self, feedback):
            if self.feedbackList is None:
                self.feedbackList = []
            self.feedbackList.append(feedback)

    class Data:
        def __init__(self):
            self.students = None


