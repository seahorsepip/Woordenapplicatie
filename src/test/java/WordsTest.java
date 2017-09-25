import com.seapip.thomas.woordenapplicatie.Words;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class WordsTest {

    private static final String TEST_TEXT = "Een, twee, drie, vier\n" +
            "Hoedje van, hoedje van\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier";

    @org.junit.Test
    public void count() {
        Assert.assertEquals("Word count is 15", 15, Words.count(TEST_TEXT));
    }

    @org.junit.Test
    public void countUnique() {
        assertEquals("Unique word count is 7", 7, Words.countUnique(TEST_TEXT));
    }

    @org.junit.Test
    public void sortReverse() {
        assertEquals("Words are properly reverse sorted",
                "[vier, van, twee, papier, hoedje, een, drie]",
                Words.sortReverse(TEST_TEXT).toString());
    }

    @org.junit.Test
    public void frequency() {
        assertEquals("Correct frequency map is generated",
                "{hoedje=3, van=3, twee=2, drie=2, vier=2, een=2, papier=1}",
                Words.frequency(TEST_TEXT).toString());
    }

    @org.junit.Test
    public void frequencyAlternative() {
        assertEquals("Correct frequency map is generated (alternative)",
                "{hoedje=3, van=3, twee=2, drie=2, vier=2, een=2, papier=1}",
                Words.frequencyAlternative(TEST_TEXT).toString());
    }

    @org.junit.Test
    public void concordance() {
        assertEquals("Correct concordance map is generated",
                "{hoedje=[2, 2, 4], van=[2, 3, 4], twee=[1, 3], drie=[1, 3], vier=[2, 4], papier=[4], een=[1, 3]}",
                Words.concordance(TEST_TEXT).toString());
    }
}