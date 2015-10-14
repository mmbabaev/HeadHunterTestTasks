import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Mihail on 01.10.15.
 */

public class Task1 {
    public static void main(String[] args) {
        try {
            List<Point> points = Point.getPointsFromFile("test.txt");
            for (int i = 0; i < points.size(); i++) {
                Point p = points.get(i);
                System.out.println(p.x + " " + p.y + ": " +
                        "neighbours count = " + p.getNeighboursCount(i) +", radius = " + p.radius);
            }
        }
        catch (FileNotFoundException ex) {
            System.out.print("File test.txt not found!");
        }
        catch (Exception ex) {
            System.out.print("Error! Check contents of test.txt file!");
        }
    }
}

class Point {
    int x, y;
    double radius = Double.MAX_VALUE;

    // Представление хэш таблицы для расстояний между точками
    static HashMap<Integer, HashMap<Integer, Double>> distances = new HashMap<>();

    protected Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Читаем координаты точек из файла и сразу
     *
     * заполняем хэш таблицу расстояний и ищем радиусы точек
     * @param fileName Название файла с точками
     * @return Список точек из файла
     */
    static public List<Point> getPointsFromFile(String fileName) throws Exception {
        List<Point> points = new ArrayList<>();
        Scanner in = new Scanner(new File(fileName));
        int row = 0;

        while(in.hasNext()) {
            String[] pointString = in.next().split(",");
            int x = new Integer(pointString[0]);
            int y = new Integer(pointString[1]);

            int col = 0;
            Point point = new Point(x, y);
            for (Point p : points) {

                double distance = point.getDistance(p);

                if (p.radius > distance) p.radius = distance;
                if (point.radius > distance) point.radius = distance;

                putDistance(distance, row, col);
                col++;
            }
            row++;
            points.add(point);
        }

        return points;
    }

    /**
     * Записываем дистанцию между точками в хэш таблицу
     *
     * @param distance расстояние между точками
     * @param row      номер ряда таблицы
     * @param col      номер столбика таблицы
     */
    static private void putDistance(double distance, int row, int col) {
        HashMap<Integer, Double> hashRow = distances.get(col);

        if (hashRow == null) {
            hashRow = new HashMap<>();
            distances.put(col, hashRow);
        }

        hashRow.put(row, distance);
    }

    private double getDistance(Point p) {
        return (Math.sqrt(Math.pow(p.x - this.x, 2) + Math.pow(p.y - this.y, 2)));
    }

    /**
     * При подсчете соседей для точки пробегаем те элементы хэш таблицы,
     * которые стоят в строке или столбике pointNumber
     *
     * @param pointNumber номер точки
     * @return количество точек, которые находятся на расстоянии двойного радиуса
     */
    public int getNeighboursCount(int pointNumber) {
        int result = 0;
        double doubleRadius = radius * 2;

        for (int i = 0; i < pointNumber; i++) {
            if (distances.get(i).get(pointNumber) <= doubleRadius)
                result++;
        }

        for (int i = pointNumber + 1; i < distances.get(0).size() + 1; i++) {
            if (distances.get(pointNumber).get(i) <= doubleRadius)
                result++;
        }

        return result;
    }
}
