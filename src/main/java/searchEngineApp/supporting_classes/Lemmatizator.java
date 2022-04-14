package searchEngineApp.supporting_classes;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import searchEngineApp.ENUMS.LemmaLanguage;


import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Разбивает полученный текст на леммы как русские, так и английские. При этом не записывает союзы, частицы и предлоги.
 */

public class Lemmatizator {
    private final String text;

    public Lemmatizator(String text) {
        this.text = text;
    }

    public HashMap<String, Integer> getLemmas () throws IOException {
        HashSet<String> engWords = new HashSet<>();
        HashSet<String> rusWords = new HashSet<>();
        Pattern pEng = Pattern.compile("[A-Za-z]{2,}");
        Pattern pRus = Pattern.compile("[А-яёЁ]{2,}");
        Matcher engMatcher = pEng.matcher(text);
        Matcher rusMatcher = pRus.matcher(text);
        while (engMatcher.find()) {
            engWords.add(engMatcher.group());
        }
        while (rusMatcher.find()) {
            rusWords.add(rusMatcher.group());
        }
        HashMap<String, Integer> lemmWords = new HashMap<>();
        addEngOrRusLemmas(rusWords, LemmaLanguage.RUS, lemmWords);
        addEngOrRusLemmas(engWords, LemmaLanguage.ENG, lemmWords);
        return lemmWords;
    }

    private HashMap<String, Integer> getTagMap(String text) throws IOException {
        Lemmatizator textLemma = new Lemmatizator(text);
        return textLemma.getLemmas();

    }

    private void addEngOrRusLemmas(HashSet<String> words, LemmaLanguage language, HashMap<String, Integer> lemmWords) throws IOException {
        LuceneMorphology russianLuceneMorphology = new RussianLuceneMorphology();
        LuceneMorphology englishLuceneMorphology = new EnglishLuceneMorphology();
        for (String s : words) {
            String morph = (language == LemmaLanguage.RUS)
                    ? russianLuceneMorphology.getMorphInfo(s.toLowerCase(Locale.ROOT)).get(0)
                    : englishLuceneMorphology.getMorphInfo(s.toLowerCase(Locale.ROOT)).get(0);
            String word = (language == LemmaLanguage.RUS)
                    ? russianLuceneMorphology.getNormalForms(s.toLowerCase(Locale.ROOT)).get(0).replace('ё','е')
                    : englishLuceneMorphology.getNormalForms(s.toLowerCase(Locale.ROOT)).get(0);
            if (!morph.contains("СОЮЗ") && !morph.contains("ПРЕДЛ")) {
                if (lemmWords.containsKey(word)) {
                    lemmWords.put(word, lemmWords.get(word) + 1);
                } else {
                    lemmWords.put(word, 1);
                }
            }
        }
    }
}
