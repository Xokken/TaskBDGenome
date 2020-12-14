import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class GenomeRepository {
    private final Sql2o sql2;
    private final String INSERT_INTO_GENOME_3 = "insert into genome_3 (id, string) values(:id,:string)";
    private final String INSERT_INTO_GENOME_5 = "insert into genome_5 (id, string) values (:id,:string)";
    private final String INSERT_INTO_GENOME_9 = "insert into genome_9 (id, string) values (:id,:string)";
    private final String SELECT_GENOME_3 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select string, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, string, count(*) as count from genome_3 where id = :id1 or id = :id2 group by id, string)\n" +
            "                          as i\n" +
            "                group by string)\n" +
            "                 as t where count_id > 1) /\n" +
            "       (select count(*) from genome_3 where id = :id1 or id = :id2)::float as countt;";
    private final String SELECT_GENOME_5 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select string, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, string, count(*) as count from genome_5 where id = :id1 or id = :id2 group by id, string)\n" +
            "                          as i\n" +
            "                group by string)\n" +
            "                 as t where count_id > 1) /\n" +
            "       (select count(*) from genome_5 where id = :id1 or id = :id2)::float as countt;";
    private final String SELECT_GENOME_9 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select string, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, string, count(*) as count from genome_9 where id = :id1 or id = :id2 group by id, string)\n" +
            "                          as i\n" +
            "                group by string)\n" +
            "                 as t where count_id > 1) /\n" +
            "       (select count(*) from genome_9 where id = :id1 or id = :id2)::float as count;";

    public GenomeRepository() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        sql2 = new Sql2o(new HikariDataSource(hikariConfig));
    }

    private void insert_genome_3(Genome genome){
        List<String> genomeList = genome.getTaken3();
        for (String gen: genomeList) {
            try (Connection connection = sql2.open()) {
                connection.createQuery(INSERT_INTO_GENOME_3)
                        .addParameter("id", genome.getId())
                        .addParameter("string", gen)
                        .executeUpdate();
            }
        }
    }

    private void insert_genome_5(Genome genome){
        List<String> genomeList = genome.getTaken5();
        for (String gen: genomeList) {
            try (Connection connection = sql2.open()) {
                connection.createQuery(INSERT_INTO_GENOME_5)
                        .addParameter("id", genome.getId())
                        .addParameter("string", gen)
                        .executeUpdate();
            }
        }
    }

    private void insert_genome_9(Genome genome){
        List<String> genomeList = genome.getTaken9();
        for (String gen: genomeList) {
            try (Connection connection = sql2.open()) {
                connection.createQuery(INSERT_INTO_GENOME_9)
                        .addParameter("id", genome.getId())
                        .addParameter("string", gen)
                        .executeUpdate();
            }
        }
    }

    public Float getFor_3(Integer id1, Integer id2) {
        try (Connection connection = sql2.open()) {
            return connection.createQuery(SELECT_GENOME_3)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2)
                    .executeScalar(Float.class);
        }
    }

    public Float getFor_5(Integer id1, Integer id2) {
        try (Connection connection = sql2.open()) {
            return connection.createQuery(SELECT_GENOME_5)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2)
                    .executeScalar(Float.class);
        }
    }


    public Float getFor_9(Integer id1, Integer id2) {
        try (Connection connection = sql2.open()) {
            return connection.createQuery(SELECT_GENOME_9)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2)
                    .executeScalar(Float.class);
        }
    }

    public void insert_genome(Genome genome) throws Exception {
        insert_genome_3(genome);
        insert_genome_5(genome);
        insert_genome_9(genome);
    }


}
