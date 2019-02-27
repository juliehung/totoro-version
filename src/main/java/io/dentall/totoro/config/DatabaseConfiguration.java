package io.dentall.totoro.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.yandex.qatools.embed.postgresql.*;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableJpaRepositories("io.dentall.totoro.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Bean(destroyMethod = "stop")
    @Profile("embedded-postgres")
    public PostgresProcess postgresProcess() throws IOException {
        log.info("Starting embedded Postgres");

        PostgresConfig postgresConfig = new PostgresConfig(
            Version.V10_6,
            new AbstractPostgresConfig.Net("localhost", 5432),
            new AbstractPostgresConfig.Storage("totoro"),
            new AbstractPostgresConfig.Timeout(),
            new AbstractPostgresConfig.Credentials("totoro", "totoro")
        );

        if (SystemUtils.IS_OS_WINDOWS) {
            postgresConfig.getAdditionalInitDbParams().addAll(Arrays.asList(
                "--encoding=UTF-8",
                "--locale=en-US"
            ));
        } else if (SystemUtils.IS_OS_MAC) {
            postgresConfig.getAdditionalInitDbParams().add("--locale=en_US.UTF-8");
        } else if (SystemUtils.IS_OS_LINUX) {
            postgresConfig.getAdditionalInitDbParams().add("--locale=en_US.utf8");
        }

        PostgresStarter<PostgresExecutable, PostgresProcess> runtime =
            PostgresStarter.getInstance(EmbeddedPostgres.defaultRuntimeConfig());

        return runtime.prepare(postgresConfig).start();
    }

    @Bean(destroyMethod = "close")
    @DependsOn("postgresProcess")
    @Profile("embedded-postgres")
    public DataSource dataSource(PostgresProcess postgresProcess) {
        PostgresConfig postgresConfig = postgresProcess.getConfig();

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:" + postgresConfig.net().port() + "/" + postgresConfig.storage().dbName());
        ds.setUsername(postgresConfig.credentials().username());
        ds.setPassword(postgresConfig.credentials().password());
        ds.setAutoCommit(false);
        ds.setConnectionTimeout(postgresConfig.timeout().startupTimeout());

        return ds;
    }
}
