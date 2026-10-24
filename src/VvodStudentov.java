import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/** Пункт меню 3: ввод данных о всех студентах, сохранение списка в БД и табличный вывод. */
public class VvodStudentov extends PunktMenu {

    public VvodStudentov(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Ввести данные о всех студентах и сохранить список в PostgreSQL с последующим табличным (форматированным) выводом в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;

        // Переменная супер-класса, объект наследника: вызывается переопределённый vvod() с сортировкой
        Students vvodchik = new SortirovannyeStudents();
        List<Students> spisok = vvodchik.vvod(b.scan, StudentyBD.vseId(b));

        System.out.println("\nВведённый список, отсортированный по фамилии:");
        Students.pechat(spisok);

        PreparedStatement ps = b.conn.prepareStatement("INSERT INTO " + b.tablename
                + " (student_id, fio, napravlenie, gruppa) VALUES (?,?,?,?)");
        for (Students s : spisok) {
            ps.setInt(1, s.getId());
            ps.setString(2, s.getFio());
            ps.setString(3, s.getNapravlenie());
            ps.setString(4, s.getGruppa());
            ps.executeUpdate();
        }
        ps.close();
        System.out.println("Сохранено студентов: " + spisok.size() + " (таблица '" + b.tablename + "')");

        System.out.println("\nВсе студенты в таблице, по алфавиту (данные из БД):");
        Students.pechat(StudentyBD.zagruzit(b, null));
    }
}
