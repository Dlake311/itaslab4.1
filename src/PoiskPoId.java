import java.sql.SQLException;
import java.util.List;

/** Пункт меню 4: вывод данных о студенте по ID. */
public class PoiskPoId extends PunktMenu {

    public PoiskPoId(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Вывести данные о студенте по ID из PostgreSQL";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        int id = b.chitatCeloe("Введите ID студента: ");
        List<Students> list = StudentyBD.zagruzit(b, id);
        if (list.isEmpty()) System.out.println("Студент с ID " + id + " не найден");
        else Students.pechat(list);
    }
}
