package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {

    boolean checkGender(final String gender) {
        return gender.equals("M") || gender.equals("F");
    }

    boolean checkBirthDate(final String birthDate) {

        Pattern pat = Pattern.compile("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))");
        Matcher mat = pat.matcher(birthDate);

        return mat.matches();
    }

    boolean checkNumber(final String number) {

        /*
         * The phone number should be split into groups using a space or dash. One group is also possible.
         * Before the first group, there may or may not be a plus symbol.
         * The first group or the second group can be wrapped in parentheses,
         * but there should be no more than one group which is wrapped in parentheses.
         * There may be no groups wrapped in parentheses.
         * A group can contain numbers, uppercase, and lowercase English letters.
         * A group should be at least 2 symbols in length. But the first group may be only one symbol in length.
         * */
        Pattern pat = Pattern.compile(
                "^\\+?([\\da-zA-Z]+[\\s-]?)?(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$"
        );
        Matcher mat = pat.matcher(number);

        return mat.matches();
    }
}
