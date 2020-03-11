import org.junit.jupiter.api.Test;

public class AminoAcidLLTester {
    @Test
    public void Monday() {
        String str = "GCUGCUACGGAGCUUCGGAGCUAGGCCGCUGCUACGGAGCUUCGGAGCUAGGCC";
        AminoAcidLL test = AminoAcidLL.createFromRNASequence(str);
        test.print();

    }
}
