package id.dhanarjkusuma.app.loket.helper;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static java.text.Normalizer.Form.NFD;
import static java.util.Locale.ENGLISH;

public class Utils {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final SimpleDateFormat generalDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat  generalDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(ENGLISH);
    }

    public static String formatGeneralDate(Date date){
        return generalDateFormatter.format(date);
    }

    public static String formatGeneralDateTime(Date date){
        return generalDateTimeFormatter.format(date);
    }
}
