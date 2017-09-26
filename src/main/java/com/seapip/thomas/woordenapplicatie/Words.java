package com.seapip.thomas.woordenapplicatie;

import java.util.*;

/**
 * The {@code Words} class consists exclusively of static methods to get statistical information from text input.
 *
 * @author Thomas Gladdines
 * @see <a href="https://sonarcloud.io/dashboard?id=com.seapip.thomas.woordenapplicatie%3AWoordenapplicatie">Code analysis</a>
 * @since 1.8
 */
public class Words {
    private Words() {
    }

    private static Collection<String> stringToWords(Collection<String> collection, String text) {
        StringBuilder word = new StringBuilder();

        for (char character : (text + " ").toLowerCase().toCharArray()) {
            switch (character) {
                case ' ':
                case ',':
                case '.':
                case '\n':
                case '\r':
                    if (word.length() > 0) {
                        collection.add(word.toString());
                        word = new StringBuilder();
                    }
                    break;
                default:
                    word.append(character);
                    break;
            }
        }

        return collection;
    }

    /**
     * Get the number of words in a string
     *
     * @param text Text with the words to count
     * @return The number of words counted
     */
    public static int count(String text) {
        return stringToWords(new ArrayList<>(), text).size();
    }

    public static int countUnique(String text) {
        return stringToWords(new HashSet<>(), text).size();
    }


    /**
     * Get the number of unique words in a string
     *
     * @param text Text with the words to count
     * @return The number of unique words counted
     */
    public static Collection<String> sortReverse(String text) {
        Queue<String> queue = (Queue<String>) stringToWords(new PriorityQueue<>(Comparator.reverseOrder()), text);
        List<String> list = new ArrayList<>();

        while (!queue.isEmpty()) {
            String word = queue.poll();
            if (!list.contains(word)) list.add(word);
        }

        return list;
    }

    private static Map<String, Integer> unsortedFrequency(String text) {
        Map<String, Integer> map = new HashMap<>();

        for (String word : stringToWords(new ArrayList<>(), text)) map.put(word, map.getOrDefault(word, 0) + 1);

        return map;
    }

    /**
     * Get the unique words and their frequency in the text sorted in descending order
     *
     * @param text Text with words to analyse
     * @return Map of unique words and their frequency in the text sorted in descending order
     */
    public static Map<String, Integer> frequency(String text) {
        List<Map.Entry> list = new ArrayList<>(unsortedFrequency(text).entrySet());
        list.sort((o1, o2) -> ((Integer) o2.getValue()).compareTo((Integer) o1.getValue()));

        Map<String, Integer> map = new LinkedHashMap<>(list.size());
        for (Map.Entry entry : list) map.put((String) entry.getKey(), (Integer) entry.getValue());

        return map;
    }


    /**
     * Get the unique words and their frequency in the text sorted in descending order, using an alternative method
     *
     * @param text Text with words to analyse
     * @return Map of unique words and their frequency in the text sorted in descending order
     */
    public static Map<String, Integer> frequencyAlternative(String text) {
        Map<Integer, List<String>> valueKeyMap = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry entry : unsortedFrequency(text).entrySet()) {
            int value = (int) entry.getValue();
            if (!valueKeyMap.containsKey(value)) valueKeyMap.put(value, new ArrayList<>());
            valueKeyMap.get(value).add((String) entry.getKey());
        }

        Map<String, Integer> map = new LinkedHashMap<>();
        for (Map.Entry entry : valueKeyMap.entrySet()) {
            int value = (int) entry.getKey();
            for (String word : (List<String>) entry.getValue()) map.put(word, value);
        }

        return map;
    }

    /**
     * Get the words and their line numbers in the text
     *
     * @param text Text with words to analyse
     * @return Map of the words and their line numbers in the text
     */
    public static Map<String, Collection<Integer>> concordance(String text) {
        HashMap<String, Collection<Integer>> concordance = new HashMap<>();
        StringBuilder word = new StringBuilder();
        int row = 1;

        for (char character : (text + " ").toLowerCase().toCharArray()) {
            switch (character) {
                case ' ':
                case ',':
                case '.':
                case '\n':
                case '\r':
                    if (character == '\n' || character == '\r') row++;
                    if (word.length() > 0) {
                        String string = word.toString();
                        if (!concordance.containsKey(string)) concordance.put(string, new ArrayList<>());
                        concordance.get(string).add(row);
                        word = new StringBuilder();
                    }
                    break;
                default:
                    word.append(character);
                    break;
            }
        }

        return concordance;
    }
}
