package Services;

public class GrammarServices {
    public static boolean isFirstLetterVowel(String text){
        text = text.toLowerCase();
        char first = text.charAt(0);
        if(first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u'){
            return true;
        }else{
            return false;
        }
    }
}
