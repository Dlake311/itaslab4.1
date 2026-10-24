import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Класс Students. Метод vvod() вводит с клавиатуры количество студентов
 * и данные каждого студента в закрытые (private) поля.
 * Класс-наследник SortirovannyeStudents переопределяет vvod() и сортирует список по фамилии.
 */
public class Students {
    public static final int MIN_STUDENTOV = 7;
    public static final String FORMAT = "| %-6s | %-32s | %-30s | %-12s |%n";
    public static final String LINIYA =
            "+--------+----------------------------------+--------------------------------+--------------+";

    private int kolichestvo;   // количество студентов, данные о которых вводим
    private int id;            // ID студента
    private String napravlenie; // направление подготовки
    private String fio;        // ФИО
    private String gruppa;     // группа

    /**
     * Ввод данных о всех студентах. Возвращает список в порядке ввода.
     * zanyatyeId - ID, которые уже есть в базе (повторять их нельзя).
     */
    public List<Students> vvod(Scanner scan, Set<Integer> zanyatyeId) {
        while (true) {
            kolichestvo = chitatChislo(scan, "Количество студентов (не менее " + MIN_STUDENTOV + "): ");
            if (kolichestvo >= MIN_STUDENTOV) break;
            System.out.println("Нужно не менее " + MIN_STUDENTOV + " студентов");
        }

        List<Students> spisok = new ArrayList<>();
        for (int i = 1; i <= kolichestvo; i++) {
            System.out.println("\n--- Студент " + i + " из " + kolichestvo + " ---");
            Students s = new Students();
            s.kolichestvo = kolichestvo;
            while (true) {
                s.id = chitatChislo(scan, "ID студента (целое положительное число): ");
                if (s.id <= 0) System.out.println("ID должен быть больше нуля");
                else if (zanyatyeId.contains(s.id)) System.out.println("Студент с таким ID уже есть, введите другой");
                else break;
            }
            zanyatyeId.add(s.id);
            s.fio = chitatNePustoe(scan, "ФИО (Фамилия Имя Отчество): ");
            s.napravlenie = chitatNePustoe(scan, "Направление подготовки: ");
            s.gruppa = chitatNePustoe(scan, "Группа: ");
            spisok.add(s);
        }
        return spisok;
    }

    // Фамилия - первое слово ФИО
    public String familiya() {
        return fio.trim().split("\\s+")[0];
    }

    public String stroka() {
        return String.format(FORMAT, id, fio, napravlenie, gruppa);
    }

    // Форматированный вывод списка в виде таблицы
    public static void pechat(List<? extends Students> spisok) {
        System.out.println(LINIYA);
        System.out.printf(FORMAT, "ID", "ФИО", "Направление подготовки", "Группа");
        System.out.println(LINIYA);
        for (Students s : spisok) System.out.print(s.stroka());
        System.out.println(LINIYA);
    }

    // ---------- Ввод с проверкой ----------

    protected static String chitatNePustoe(Scanner scan, String text) {
        while (true) {
            System.out.print(text);
            String s = scan.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Поле не может быть пустым");
        }
    }

    protected static int chitatChislo(Scanner scan, String text) {
        while (true) {
            System.out.print(text);
            try {
                return Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число");
            }
        }
    }

    // ---------- Геттеры и заполнение из БД ----------

    public int getKolichestvo() {
        return kolichestvo;
    }

    public int getId() {
        return id;
    }

    public String getNapravlenie() {
        return napravlenie;
    }

    public String getFio() {
        return fio;
    }

    public String getGruppa() {
        return gruppa;
    }

    public static Students izBD(int id, String fio, String napravlenie, String gruppa) {
        Students s = new Students();
        s.id = id;
        s.fio = fio;
        s.napravlenie = napravlenie;
        s.gruppa = gruppa;
        return s;
    }
}
