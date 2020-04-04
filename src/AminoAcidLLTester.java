import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class AminoAcidLLTester {
    /*This test the basic functionality of the createFromRNASequence() to see if it can create a link list of AminoAcids
        with a given RNA string. It passed a created the correct AminoAcid link list.
     */
    @Test
    public void createFromRNASequenceTest() {
        String str = "GCU" + "ACG" +"GAG" + "CUU";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);

        char[] expected = {'A', 'T', 'E', 'L'};

        for(int i = 0; i < expected.length; i++ ){
            assertEquals(expected[i],test.getAminoAcid());
            test = test.getNext();
        }
    }

    /*This test makes sure that addCodon does not create duplicates. Q and A both would have shown twice but they only
        show once. Therefore, this test passed.
     */
    @Test
    public void addCodonTest1() {
        String str = "AUG" + "CAA" + "CAA" + "GCU" + "GUC" + "GCG";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);

        char[] expected = {'M', 'Q', 'A', 'V'};

        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i],test.getAminoAcid());
            test = test.getNext();
        }
    }

    /*This test is similar as the one above, however, here I am testing to see if addCodon incremented the correct codon
        when there are duplicates. I chose to look at Q's codons to see if the CAA codons were marked twice. The test
        passed.
   */
    @Test
    public void addCodonTest2() {
        String str = "AUG" + "CAA" + "CAA" + "GCU" + "GUC" + "GCG";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);
        int[] answer = test.getNext().getCounts();

        int[] expected = {0, 2};

        assertArrayEquals(expected,answer);
    }

    /*Here I tested to see if totalCount() works correctly. I tested it on the AminoAcid A because it shows 4 time
        with three different codon (one having a duplicate). With different codons and duplicates, the test passed.
     */
    @Test
    public void totalCountTest() {
        String str = "AUG" + "CAA" + "CAA" + "GCU" + "GUC" + "GCG" + "GCA" + "GCU";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);
        while(test.getAminoAcid() != 'A'){
            test = test.getNext();
        }
        int answer = test.getTotalCounts();

        int expected = 4;

        assertEquals(expected,answer);
    }

    /*This test sees if AminoAcidList can create a character array in the order the link list is in. This test the basic
        functionality of the method and it passed.
     */
    @Test
    public void AminoAcidListTest() {
        String str = "AUG" + "CAA" + "CAA" + "GCU" + "GUC" + "GCG" + "GCA" + "GCU";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);
        char[] answer = test.getAminoAcidList();

        char[] expected = {'M', 'Q', 'A', 'V'};

        assertArrayEquals(expected,answer);
    }

    /*This test sees if AminoAcidCounts can create a integer array of the total counts of each aminoAcid. This test
      had multiple duplicates and still passed.
   */
    @Test
    public void AminoAcidCountsTest() {
        String str = "AUG" + "CAA" + "CAA" + "GCU" + "GUC" + "GCG" + "GCA" + "GCU";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);
        int[] answer = test.getAminoAcidCounts();

        int[] expected = {1, 2, 4, 1};

        assertArrayEquals(expected,answer);
    }

    /*This test to see if the isSorted function works correctly. Given an already sorted function it returns true.
        Therefore, it passed.
*/
    @Test
    public void isSortTest() {
        String str = "GCU" + "AUG" + "CAA" + "GUC";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);
        boolean answer = test.isSorted();

        assertTrue(answer);
    }

    /*Now that isSort() has been tested, we can see whether sort() works. Given a link list that has AminoAcids out of
    order, the sort value was able to return a true isSort(). Meaning the test passed.
 */
    @Test
    public void sortTest() {
        String str = "AUG" + "CAA" + "CAA" + "GCU" + "GUC" + "GCG" + "GCA" + "GCU";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);
        AminoAcidLL answer = test.getSort();

        assertTrue(answer.isSorted());
    }

    /*This test the functionality of AminoAcidCompare(). Given two link lists, list and inlist, AminoAcid compare was
    able to find their total differences. This also test to see what happens when the list are the same size
    but the last node has two different AminoAcid. Even with two different acids the test passed.
     */
    @Test
    public void AminoCompare() {
        String firstRNA = "GCC" + "GCU" + "GGG" + "GGU" + "AUG";
        String secondRNA = "GCG" + "GGA" + "GGC" + "GGU" + "GUC";

        AminoAcidLL list = AminoAcidLL.createFromRNASequence(firstRNA);
        AminoAcidLL inList = AminoAcidLL.createFromRNASequence(secondRNA);

        int answer = list.aminoAcidCompare(inList);
        int expected = 4;

        assertEquals(expected,answer);

    }

    /*This test the functionality of codonCompare(). Given two link lists, list and inlist, codonCompare was
    able to find their total differences between their codons. This test also test to see what happens when
    the list are the not same size. Considering that this code is very similar to aminoAcidComare() it is also testing
    the different sizes parameter for aminoAcidCompare(). Even with two different sizes the test passed.
     */
    @Test
    public void CodonCompare() {
        String firstRNA = "GAG" + "GAG" + "GAG" + "GGC" + "GGC" + "AGG" + "ACA" + "GUG";
        String secondRNA = "GAG" + "GCC" + "GGC" + "GGC" + "AGG" + "AGG";

        AminoAcidLL list = AminoAcidLL.createFromRNASequence(firstRNA);
        AminoAcidLL inList = AminoAcidLL.createFromRNASequence(secondRNA);

        int answer = list.codonCompare(inList);
        int expected = 6;

        assertEquals(expected,answer);
    }


}
