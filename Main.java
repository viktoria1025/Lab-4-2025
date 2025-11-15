import functions.*;
import functions.basic.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем функцию f(x) = 3x + 2 на интервале [1, 5] с 4 точками
        TabulatedFunction f = new ArrayTabulatedFunction(1, 5, 4);
        for (int i = 0; i < f.getPointsCount(); i++) {
            // Для каждого x устанавливаем значение y = 3x + 2
            f.setPointY(i, 3 * f.getPointX(i) + 2);
        }

        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("x,y "+ f.getPointX(i) + " " + f.getPointY(i));
        }

        double[] val = {5, 8, 11, 14}; // Значения y = 3x + 2 для x = 1,2,3,4
        TabulatedFunction fun1 = new LinkedListTabulatedFunction(1, 4, val);
        for (int i = 0; i < fun1.getPointsCount(); i++) {
            System.out.println("x,y "+ fun1.getPointX(i) + " " + fun1.getPointY(i));
        }

        System.out.println("Левая граница " + f.getLeftDomainBorder());
        System.out.println("Правая граница " + f.getRightDomainBorder());

        System.out.println("Значение функции в точке x = 1.5 входящей в интервал: " + f.getFunctionValue(1.5));
        System.out.println("Значение функции в точке x = 3.5 входящей в интервал: " + f.getFunctionValue(3.5));
        System.out.println("Значение функции в точке x = 0 не входящей в интервал: " + f.getFunctionValue(0));
        System.out.println("Значение функции в точке x = 6 не входящей в интервал: " + f.getFunctionValue(6));

        System.out.println("Возвращение копии второй точки " + f.getPoint(1).getX() + " " + f.getPoint(1).getY());

        System.out.println("Замена второй точки в начальной функции (вне интервала)");
        FunctionPoint p1 = new FunctionPoint(0.5, 10); // x=0.5 вне интервала [1,5]
        try {
            f.setPoint(1, p1);
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка при установке точки: " + e.getMessage());
        }
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("x,y "+ f.getPointX(i) + " " + f.getPointY(i));
        }

        System.out.println("Замена 2 точки в начальной функции");
        FunctionPoint p2 = new FunctionPoint(2.5, 12);
        try {
            f.setPoint(1, p2);
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка при установке точки: " + e.getMessage());
        }
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("x,y "+ f.getPointX(i) + " " + f.getPointY(i));
        }

        System.out.println("Замена абсциссы 3 точки");
        System.out.println("Замена абсциссы 3 точки");
        try {
            f.setPointX(2, 3.8);
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка при установке X: " + e.getMessage());
        }
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("x,y "+ f.getPointX(i) + " " + f.getPointY(i));
        }

        System.out.println("Замена ординаты 2 точки");
        f.setPointY(1, 15);
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("x,y "+ f.getPointX(i) + " " + f.getPointY(i));
        }

        System.out.println("Удаление 3 точки");
        f.deletePoint(2);
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("x,y "+ f.getPointX(i) + " " + f.getPointY(i));
        }

        System.out.println("Добавление 2 новых точек");
        FunctionPoint p3 = new FunctionPoint(2.2, 9);
        FunctionPoint p4 = new FunctionPoint(3.5, 13);
        try {
            f.addPoint(p3);
            f.addPoint(p4);
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка при добавлении точки: " + e.getMessage());
        }
        for (int i = 0; i < f.getPointsCount(); i++) {
            System.out.println("x,y "+ f.getPointX(i) + " " + f.getPointY(i));
        }

        System.out.println("тест исключений");

        System.out.println("Исключения конструкторов:");
        System.out.println("Задали границы 8 и 3:");
        try {
            TabulatedFunction f2 = new LinkedListTabulatedFunction(8, 3, 6);
        } catch (IllegalArgumentException e) {
            System.out.println("Левая граница больше правой: 8 > 3");
        }
        System.out.println("Передали только 1 элемент:");
        try {
            double[] val1 = {7};
            TabulatedFunction f3 = new LinkedListTabulatedFunction(2, 9, val1);
        } catch (IllegalArgumentException e) {
            System.out.println("Массив содержит только 1 элемент");
        }
        System.out.println("Остальные исключения:");
        try {
            f.getPoint(8);
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Индекс 8 превышает количество точек");
        }

        try {
            f.addPoint(new FunctionPoint(2.5, 8));
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Точка с X=2.5 уже существует");
        }
        System.out.println("Задали 3 точки:");
        try {
            TabulatedFunction f4 = new LinkedListTabulatedFunction(0, 6, 3);
            f4.deletePoint(1);
            f4.deletePoint(0);
        } catch (IllegalStateException e) {
            System.out.println("Нельзя удалить точку - останется меньше 2 точек");
        }
        System.out.println("Поменяли абсциссу первой точки:");
        try {
            f.setPointX(1, 0.8);
        } catch (InappropriateFunctionPointException e) {
            System.out.println("X=0.8 меньше предыдущей точки");
        }
        System.out.println("Удаление 10 точки:");
        try {
            f.deletePoint(10);
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Индекс 10 для удаления не существует");
        }

        //Тестирование Sin и Cos
        System.out.println("Sin и Cos:");
        Sin sin = new Sin();
        Cos cos = new Cos();

        for (double x = 0; x <= Math.PI; x += 0.1) {
            System.out.println("x " + x + " sin " + sin.getFunctionValue(x) + " cos " + cos.getFunctionValue(x));
        }

        //Табулированные аналоги
        System.out.println("Табулированные аналоги");
        TabulatedFunction tabSin = TabulatedFunctions.tabulate(sin, 0, Math.PI, 10);
        TabulatedFunction tabCos = TabulatedFunctions.tabulate(cos, 0, Math.PI, 10);

        for (double x = 0; x <= Math.PI; x += 0.1) {
            System.out.println("x " + x + " sin " + sin.getFunctionValue(x) + " tab " + tabSin.getFunctionValue(x) +
                    " cos " + cos.getFunctionValue(x) + " tab " + tabCos.getFunctionValue(x));
        }

        //Сумма квадратов
        System.out.println("Сумма квадратов");

        Function sumSquares = new Function() {
            public double getLeftDomainBorder() {
                return 0;
            }
            public double getRightDomainBorder() {
                return Math.PI;
            }
            public double getFunctionValue(double x) {
                Function sin2 = Functions.power(tabSin, 2);
                Function cos2 = Functions.power(tabCos, 2);
                Function sum = Functions.sum(sin2, cos2);

                double result = sum.getFunctionValue(x);
                // Если получили NaN, вычисляем напрямую
                if (Double.isNaN(result)) {
                    double sinVal = tabSin.getFunctionValue(x);
                    double cosVal = tabCos.getFunctionValue(x);
                    result = sinVal * sinVal + cosVal * cosVal;
                }
                return result;
            }
        };

        // Выводим значения этой функции на отрезке от 0 до π
        for (double x = 0; x <= Math.PI; x += 0.1) {
            double result = sumSquares.getFunctionValue(x);
            System.out.println("x " + x + " результат " + result);
        }
        //Влияние количества точек
        System.out.println("Влияние количества точек на результат");
        int[] counts = {5, 10, 20, 40};

        for (int i = 0; i < 4; i++) {
            int mas = counts[i];
            TabulatedFunction a = TabulatedFunctions.tabulate(sin, 0, Math.PI, mas);
            TabulatedFunction b = TabulatedFunctions.tabulate(cos, 0, Math.PI, mas);
            Function sumf = Functions.sum(Functions.power(a, 2), Functions.power(b, 2));
            double res = sumf.getFunctionValue(Math.PI/2);
            System.out.println("Точек: " + mas + ", результат: " + res);
        }

        //Экспонента
        System.out.println("Экспонента");
        Exp exp = new Exp();
        TabulatedFunction tabExp = TabulatedFunctions.tabulate(exp, 0, 10, 11);

        try (FileWriter writer = new FileWriter("exp.txt")) {
            TabulatedFunctions.writeTabulatedFunction(tabExp, writer);
        }
        TabulatedFunction readExp;
        try (FileReader reader = new FileReader("exp.txt")) {
            readExp = TabulatedFunctions.readTabulatedFunction(reader);
        }
        System.out.println("Сравнение экспоненты:");
        for (double x = 0; x <= 10; x += 1) {
            System.out.println("x " + x + " исходная " + tabExp.getFunctionValue(x) + " считанная " + readExp.getFunctionValue(x));
        }

        //Логарифм
        System.out.println("Логарифм");
        Log log = new Log(Math.E);
        TabulatedFunction tabLog = TabulatedFunctions.tabulate(log, 1, 10, 10);
        try (FileOutputStream fos = new FileOutputStream("log.dat")) {
            TabulatedFunctions.outputTabulatedFunction(tabLog, fos);
        }
        TabulatedFunction readLog;
        try (FileInputStream fis = new FileInputStream("log.dat")) {
            readLog = TabulatedFunctions.inputTabulatedFunction(fis);
        }
        System.out.println("Сравнение логарифма");
        for (double x = 1; x <= 10; x += 1) {
            System.out.println("x " + x + " исходная " + tabLog.getFunctionValue(x) + " считанная" + readLog.getFunctionValue(x));
        }
        System.out.println("Тестирование сериализации");
        Function log2 = new Log(Math.E);
        Function ex = new Exp();
        TabulatedFunction F = TabulatedFunctions.tabulate(Functions.composition(log2, ex), 0, 10, 11);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            out.writeObject(F);
        } catch (IOException e) {
            System.out.println("Ошибка");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"))) {
            TabulatedFunction F1 = (TabulatedFunction) in.readObject();
            for (int i = 0; i <= 10; i++) {
                System.out.println(" x " + i + "  " + F.getFunctionValue(i) + "  " + F1.getFunctionValue(i));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка");
        }
    }
}