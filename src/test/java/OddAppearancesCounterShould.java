import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class OddAppearancesCounterShould {
    public Map<String, Integer> countOddAppearancesOfVowels(String sentence){
        if (sentence.isBlank()){
            return new HashMap<>();
        }
        return countVowelsIn(sentence);
    }

    private Map<String, Integer> countVowelsIn(String sentence) {
        Map<String, Integer> counts = new HashMap<>();
        Map<String, Integer> oddAppearances = new HashMap<>();
        for (String letter: removeDiacritics(sentence.toLowerCase()).split("")) {
            if (counts.containsKey(letter)){

                int update = counts.get(letter) + 1;
                counts.put(letter, update);

                if (update%2 == 0){
                    oddAppearances.remove(letter);
                }else{
                    oddAppearances.put(letter, update);
                }
            }
            else {
                boolean isVowel = letter.matches("[aeiou]");
                if (isVowel) {
                    counts.put(letter, 1);
                    oddAppearances.put(letter, 1);
                }
            }
        }
        return oddAppearances;
    }

    private Map<String, Integer> removeEvenAppareancesIn(Map<String, Integer> counts) {
        return counts.entrySet()
                .stream().filter(entry -> entry.getValue() % 2 != 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String removeDiacritics(String sentence) {
        sentence = sentence.replace('á', 'a');
        sentence = sentence.replace('é', 'e');
        sentence = sentence.replace('í', 'i');
        sentence = sentence.replace('ó', 'o');
        sentence = sentence.replace('ú', 'u');
        return sentence;
    }

    @Test
    public void counts_vowels_appearing_an_odd_number_of_times(){
        assertThat(countOddAppearancesOfVowels("")).isEqualTo(new HashMap<String, Integer>());
        assertThat(countOddAppearancesOfVowels("z")).isEqualTo(new HashMap<String, Integer>());
        assertThat(countOddAppearancesOfVowels("a"))
                .isEqualTo(new HashMap<String, Integer>(){{ put("a", 1); }});
        assertThat(countOddAppearancesOfVowels("aa"))
                .isEqualTo(new HashMap<String, Integer>());
        assertThat(countOddAppearancesOfVowels("asdjfwkleee"))
                .isEqualTo(new HashMap<String, Integer>(){{ put("a", 1); put("e", 3); }});
        assertThat(countOddAppearancesOfVowels("azAár"))
                .isEqualTo(new HashMap<String, Integer>(){{ put("a", 3); }});
    }

    @Test
    public void is_not_case_sensitive(){
        assertThat(countOddAppearancesOfVowels("aA"))
                .isEqualTo(new HashMap<String, Integer>());
    }

    @Test
    public void considers_diacritics_are_regular_vowels(){
        assertThat(countOddAppearancesOfVowels("áaÁa"))
                .isEqualTo(new HashMap<String, Integer>());
    }
}