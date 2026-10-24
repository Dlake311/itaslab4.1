import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

/** Пункт меню 6: выгрузка итогового списка (по алфавиту) из БД в Excel (CSV) и вывод в консоль. */
public class EksportExcel extends PunktMenu {

    public EksportExcel(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Сохранить итоговые результаты из PostgreSQL в Excel и вывести их в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        File file = new File(b.papkaDlyaFaila(), b.tablename + ".csv");
        List<Students> list = StudentyBD.zagruzit(b, null);

        // CSV с BOM и разделителем ';' - Excel открывает его с правильной кодировкой
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            w.write('﻿');
            w.write("№;ID;ФИО;Направление подготовки;Группа\n");
            int n = 1;
            for (Students s : list) {
                w.write(n++ + ";" + s.getId() + ";" + b.csv(s.getFio()) + ";"
                        + b.csv(s.getNapravlenie()) + ";" + b.csv(s.getGruppa()) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
            return;
        }

        System.out.println("\n=== Содержимое таблицы '" + b.tablename + "' (по алфавиту) ===");
        if (list.isEmpty()) System.out.println("Таблица пуста");
        else Students.pechat(list);
        System.out.println("Данные сохранены в Excel-файл: " + file.getAbsolutePath() + " (студентов: " + list.size() + ")");
        b.otkrytFail(file);
    }
}
