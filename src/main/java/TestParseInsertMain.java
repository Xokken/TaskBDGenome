import java.util.List;

public class TestParseInsertMain {
    public static void main(String[] args) throws Exception {
        GenomeRepository repository = new GenomeRepository();
        parseInsert(repository);
    }

    private static void parseInsert(GenomeRepository repository) throws Exception {
        MyReaderGenome reader_1 = new MyReaderGenome("Genome_1-1.txt");
        MyReaderGenome reader_2 = new MyReaderGenome("Genome_2-1.txt");

        List<String> g2_1 = reader_1.take(3);
        List<String> g5_1 = reader_1.take(5);
        List<String> g9_1 = reader_1.take(9);
        Genome genome_1 = new Genome(1, g2_1, g5_1, g9_1);

        List<String> g2_2 = reader_2.take(3);
        List<String> g5_2 = reader_2.take(5);
        List<String> g9_2 = reader_2.take(9);
        Genome genome_2 = new Genome(2, g2_2, g5_2, g9_2);

        repository.insert_genome(genome_1);
        repository.insert_genome(genome_2);
    }
}
