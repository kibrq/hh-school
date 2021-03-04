class Car:
    def __init__(self, model, name):
        self.model = model
        self.name = name

class Garage:
    def __init__(self, cars):
        self.cars = cars

    def __iter__(self):
        return iter(self.cars)

    def add(self, car):
        self.cars.append(car)

    def delete(self, index):
        self.cars.pop(index)

