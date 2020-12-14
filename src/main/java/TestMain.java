public class TestMain {
    public static void main(String[] args) {
        System.out.println("System.out.println(Its working)");
        GenomeRepository repository = new GenomeRepository();
        System.out.println("GENOME 3: " + repository.getFor_3(1, 2));
        System.out.println("GENOME 5: " + repository.getFor_5(1, 2));
        System.out.println("GENOME 9: " + repository.getFor_9(1, 2));
    }

}
