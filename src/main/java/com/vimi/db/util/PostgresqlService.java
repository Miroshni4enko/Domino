package com.vimi.db.util;

import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

import java.sql.Connection;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.PRODUCTION;
import static ru.yandex.qatools.embed.postgresql.util.SocketUtil.findFreePort;

public class PostgresqlService {

    private PostgresProcess process;
    private Connection conn;

    public void start() throws Exception {
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();
        final PostgresConfig config = new PostgresConfig(PRODUCTION, new AbstractPostgresConfig.Net("localhost", findFreePort()),
                new AbstractPostgresConfig.Storage("DominoDB"), new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("DominoAdmin", "Mir()sh1997"));
        PostgresExecutable exec = runtime.prepare(config);
        
        process = exec.start();
        /*String url = format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
                config.net().host(),
                config.net().port(),
                config.storage().dbName(),
                config.credentials().username(),
                config.credentials().password()
        );
        conn = DriverManager.getConnection(url);*/
    }

    public PostgresProcess getProcess() {
        return process;
    }

    public Connection getConn() {
        return conn;
    }

    public void stop() throws Exception {
        conn.close();
        process.stop();
    }
}
