package systems.achilles.tax.cli.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public boolean validRegex(String regex, String text){
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    public boolean checkRecord(String text){
        if(text != null && !text.replaceAll("\\s+","").isEmpty() && text.replaceAll("\\s+", "").split(Constants.SEPERATOR).length > 0) return true;
        return false;
    }

    public String[] getRecord(String text){
        return text.replaceAll("\\s+", "").split(Constants.SEPERATOR);
    }
}
