import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Класс-наследник: переопределяет метод vvod() супер-класса -
 * после ввода возвращает список, отсортированный по алфавиту (по фамилии).
 */
public class SortirovannyeStudents extends Students {

    // Русский алфавитный порядок (правильно ставит "Ё" после "Е", не учитывает регистр)
    private static final Collator RU = Collator.getInstance(Locale.forLanguageTag("ru-RU"));

    // Сравнение: сначала по фамилии, при совпадении - по полному ФИО
    public static final Comparator<Students> PO_FAMILII =
            Comparator.comparing(Students::familiya, RU)
                    .thenComparing(Students::getFio, RU);

    @Override
    public List<Students> vvod(Scanner scan, Set<Integer> zanyatyeId) {
        List<Students> spisok = super.vvod(scan, zanyatyeId);
        sortirovat(spisok);
        return spisok;
    }

    public static void sortirovat(List<Students> spisok) {
        spisok.sort(PO_FAMILII);
    }
}
