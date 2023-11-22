package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.stream.Collectors;

public class V2__LoadQuotes extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        InputStream inputStream = ResourceUtils.getURL("classpath:fortunes.txt").openStream();
        String contents = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        String[] quotes = contents.split("\n~~~~~\n");
        String sql = "INSERT INTO quote (quote) VALUES (?)";
        try (PreparedStatement stmt = context.getConnection().prepareStatement(sql)) {
            for (String quote : quotes) {
                stmt.setString(1, quote);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

}
