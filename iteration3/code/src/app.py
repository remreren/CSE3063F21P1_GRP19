from src.model.control_center import ControlCenter


class App(object):
    def __init__(self):
        pass

    def start(self):
        controlCenter: ControlCenter = ControlCenter()
        controlCenter.start()