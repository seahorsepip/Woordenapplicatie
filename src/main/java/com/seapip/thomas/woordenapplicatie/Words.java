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

    public static int count(String text) {
        return stringToWords(new ArrayList<>(), text).size();
    }

    public static int countUnique(String text) {
        return stringToWords(new HashSet<>(), text).size();
    }

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
        Collection<String> words = stringToWords(new ArrayList<>(), text);
        Map<String, Integer> frequency = new HashMap<>();

        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        return frequency;
    }

    public static Map<String, Integer> frequency(String text) {
        List<Map.Entry> frequencyList = new ArrayList<>(unsortedFrequency(text).entrySet());
        frequencyList.sort((o1, o2) -> ((Integer) o2.getValue()).compareTo((Integer) o1.getValue()));

        Map<String, Integer> frequencySorted = new LinkedHashMap<>(frequencyList.size());
        for (Map.Entry entry : frequencyList) {
            frequencySorted.put((String) entry.getKey(), (Integer) entry.getValue());
        }

        return frequencySorted;
    }

    public static Map<String, Integer> frequencyAlternative(String text) {
        Map<String, Integer> frequency = unsortedFrequency(text);

        Map<Integer, List<String>> frequencyReverseKeyValueReverseSorted = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry entry : frequency.entrySet()) {
            if (frequencyReverseKeyValueReverseSorted.containsKey(entry.getValue())) {
                frequencyReverseKeyValueReverseSorted.get(entry.getValue()).add((String) entry.getKey());
            } else {
                List<String> values = new ArrayList<>();
                values.add((String) entry.getKey());
                frequencyReverseKeyValueReverseSorted.put((int) entry.getValue(), values);
            }
        }

        Map<String, Integer> frequencySorted = new LinkedHashMap<>(frequency.size());
        for (Map.Entry entry : frequencyReverseKeyValueReverseSorted.entrySet()) {
            int value = (int) entry.getKey();
            for (String word : (List<String>) entry.getValue()) {
                frequencySorted.put(word, value);
            }
        }

        return frequencySorted;
    }

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
                    if (character == '\n' || character == '\r') {
                        row++;
                    }
                    if (word.length() > 0) {
                        String string = word.toString();
                        if (!concordance.containsKey(string)) {
                            concordance.put(string, new ArrayList<>());
                        }
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
