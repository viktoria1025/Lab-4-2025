import functions.*;
import functions.basic.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        //Тестирование Sin и Cos
        System.out.println("Sin и Cos:");
        Sin sin = new Sin();
        Cos cos = new Cos();

        for (double x = 0; x <= Math.PI + 1e-10; x += 0.1) {
            System.out.println("x " + x + " sin " + sin.getFunctionValue(x) + " cos " + cos.getFunctionValue(x));
        }
        //Табулированные аналоги
        System.out.println("Табулированные аналоги");
        TabulatedFunction tabSin = TabulatedFunctions.tabulate(sin, 0, Math.PI, 10);
        TabulatedFunction tabCos = TabulatedFunctions.tabulate(cos, 0, Math.PI, 10);

        for (double x = 0; x <= Math.PI + 1e-10; x += 0.1) {
            System.out.println("x " + x + " sin " + sin.getFunctionValue(x) + " tab " + tabSin.getFunctionValue(x) +
                    " cos " + cos.getFunctionValue(x) + " tab " + tabCos.getFunctionValue(x));
        }

        //Сумма квадратов
        System.out.println("Сумма квадратов");

        TabulatedFunction tabSin1 = TabulatedFunctions.tabulate(sin, 0, Math.PI, 10);
        TabulatedFunction tabCos1 = TabulatedFunctions.tabulate(cos, 0, Math.PI, 10);

        Function sinSq = Functions.power(tabSin, 2);
        Function cosSq = Functions.power(tabCos, 2);
        Function sumSq = Functions.sum(sinSq, cosSq);

        for (double x = 0; x <= Math.PI + 1e-10; x += 0.1) {
            double result = sumSq.getFunctionValue(x);
            System.out.println("x " + x + " результат " + result);
        }

        System.out.println("Влияние количества точек на результат");
        int[] counts = {5, 10, 20, 40};

        for (int i = 0; i < 4; i++) {
            int mas = counts[i];
            TabulatedFunction a = TabulatedFunctions.tabulate(sin, 0, Math.PI, mas);
            TabulatedFunction b = TabulatedFunctions.tabulate(cos, 0, Math.PI, mas);

            // Используем классы из meta вместо анонимного вычисления
            Function aSq = Functions.power(a, 2);
            Function bSq = Functions.power(b, 2);
            Function sumf = Functions.sum(aSq, bSq);

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

        Function log1 = new Log(Math.E);
        Function ex = new Exp();

        // Тестирование ArrayTabulatedFunction с Serializable
        System.out.println("ArrayTabulatedFunction (Serializable)");
        TabulatedFunction arrayFunc = TabulatedFunctions.tabulate(Functions.composition(log, ex), 0, 10, 11);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serializable.ser"))) {
            out.writeObject(arrayFunc);
        } catch (IOException e) {
            System.out.println("Ошибка" + e.getMessage());
        }

        // Десериализация Array
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("serializable.ser"))) {
            TabulatedFunction arrayFuncRestored = (TabulatedFunction) in.readObject();

            for (int i = 0; i <= 10; i++) {
                double valueF = arrayFunc.getFunctionValue(i);
                double valueF1 = arrayFuncRestored.getFunctionValue(i);

                if (Math.abs(valueF - valueF1) < 1e-10) {
                    System.out.println("x " + i + ": F = " + valueF + ", F1 = " + valueF1 );
                } else {
                    System.out.println("x " + i + ": F = " + valueF + ", F1 = " + valueF1 );
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка" + e.getMessage());
        }

        // Тестирование LinkedListTabulatedFunction с Externalizable
        System.out.println("LinkedListTabulatedFunction (Externalizable)");
        TabulatedFunction listFunc = TabulatedFunctions.tabulate(Functions.composition(log, ex), 1, 10, 10);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("externalizable.ser"))) {
            out.writeObject(listFunc);
        } catch (IOException e) {
            System.out.println("Ошибка" + e.getMessage());
        }

        // Десериализация LinkedList
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("externalizable.ser"))) {
            TabulatedFunction listFuncRestored = (TabulatedFunction) in.readObject();

            for (int i = 1; i <= 10; i++) {
                double valueF = listFunc.getFunctionValue(i);
                double valueF1 = listFuncRestored.getFunctionValue(i);

                if (Math.abs(valueF - valueF1) < 1e-10) {
                    System.out.println("x = " + i + " F = " + valueF + ", F1 = " + valueF1);
                } else {
                    System.out.println("x = " + i + ": F = " + valueF + ", F1 = " + valueF1);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка" + e.getMessage());
        }
    }
}