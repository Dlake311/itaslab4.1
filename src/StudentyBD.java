import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Работа с таблицей студентов. */
public class StudentyBD {

    static Set<Integer> vseId(Baza b) throws SQLException {
        Set<Integer> ids = new HashSet<>();
        ResultSet rs = b.stat.executeQuery("SELECT student_id FROM " + b.tablename);
        while (rs.next()) ids.add(rs.getInt(1));
        rs.close();
        return ids;
    }

    // Читает студентов (всех или с указанным ID) и сортирует по фамилии
    static List<Students> zagruzit(Baza b, Integer id) throws SQLException {
        PreparedStatement ps = b.conn.prepareStatement("SELECT * FROM " + b.tablename
                + (id == null ? "" : " WHERE student_id = ?"));
        if (id != null) ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        List<Students> list = new ArrayList<>();
        while (rs.next()) {
            list.add(Students.izBD(rs.getInt("student_id"), rs.getString("fio"),
                    rs.getString("napravlenie"), rs.getString("gruppa")));
        }
        rs.close();
        ps.close();
        SortirovannyeStudents.sortirovat(list);
        return list;
    }
}
