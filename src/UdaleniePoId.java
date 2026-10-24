import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/** Пункт меню 5: удаление данных о студенте по ID. */
public class UdaleniePoId extends PunktMenu {

    public UdaleniePoId(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Удалить данные о студенте из PostgreSQL по ID";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        int id = b.chitatCeloe("Введите ID студента для удаления: ");
        List<Students> list = StudentyBD.zagruzit(b, id);
        if (list.isEmpty()) {
            System.out.println("Студент с ID " + id + " не найден");
            return;
        }
        System.out.println("Будет удалён:");
        Students.pechat(list);
        System.out.print("Удалить? (д/н): ");
        String otv = b.scan.nextLine().trim().toLowerCase();
        if (!(otv.startsWith("д") || otv.startsWith("y"))) {
            System.out.println("Удаление отменено");
            return;
        }

        PreparedStatement ps = b.conn.prepareStatement("DELETE FROM " + b.tablename + " WHERE student_id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        System.out.println("Студент с ID " + id + " удалён");

        System.out.println("\nОставшиеся студенты (данные из БД):");
        Students.pechat(StudentyBD.zagruzit(b, null));
    }
}
