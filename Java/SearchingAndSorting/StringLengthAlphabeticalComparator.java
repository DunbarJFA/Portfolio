import java.util.Comparator;

public class StringLengthAlphabeticalComparator implements Comparator<String> {
    public int compare(String str1, String str2){
        int lengthDiff = str1.length() - str2.length();
        if(lengthDiff == 0){
            return str1.compareTo(str2);
        }
        return lengthDiff;
    }
}
