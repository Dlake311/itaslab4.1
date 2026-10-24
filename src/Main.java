import java.sql.SQLException;

/**
 * Лабораторная работа 4, часть 1 (сложный вариант).
 * ООП и коллекции: класс Students и наследник SortirovannyeStudents (список по алфавиту, по фамилии).
 * Каждый пункт меню - отдельный класс-наследник PunktMenu.
 */
public class Main {
    public static void main(String[] args) {
        Baza b = new Baza("student_id INT PRIMARY KEY, fio VARCHAR(150), napravlenie VARCHAR(150), gruppa VARCHAR(30)");
        if (!b.podkl()) return;

        PunktMenu[] menu = {
                new VyvodTablic(b),
                new SozdanieTablicy(b),
                new VvodStudentov(b),
                new PoiskPoId(b),
                new UdaleniePoId(b),
                new EksportExcel(b)
        };

        while (true) {
            System.out.println();
            System.out.println("========== МЕНЮ ==========");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ". " + menu[i].nazvanie());
            }
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int vib = b.chitatVybor();
            if (vib == 0) {
                b.zakr();
                System.out.println("Работа завершена");
                return;
            }
            if (vib < 1 || vib > menu.length) {
                System.out.println("Нет такого пункта меню, введите число от 0 до " + menu.length);
                continue;
            }
            try {
                menu[vib - 1].vypolnit();
            } catch (SQLException e) {
                System.out.println("Ошибка работы с базой данных: " + e.getMessage());
            }
        }
    }
}
