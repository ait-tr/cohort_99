"""
Это пример Python-файла, который демонстрирует разные элементы языка:
- Докстринги
- Классы
- Функции с аннотацией типов
- Простые операции и вывод в консоль
"""

class Calculator:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def add(self):
        return self.a + self.b

    def multiply(self):
        return self.a * self.b


def greet(name: str) -> str:
    """
    Возвращает приветственное сообщение для заданного имени.
    :param name: Имя, которому адресуется приветствие
    :return: Строка приветствия
    """
    return f"Привет, {name}!"


if __name__ == "__main__":
    # Создаём экземпляр класса Calculator и выполняем вычисления
    calc = Calculator(3, 4)
    sum_result = calc.add()
    product_result = calc.multiply()

    # Печатаем результаты
    print(greet("Python"))
    print(f"Сумма: {sum_result}")
    print(f"Произведение: {product_result}")
