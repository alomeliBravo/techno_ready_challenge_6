package com.pikolinc.config.database;

import com.pikolinc.config.DatabaseProvider;
import com.pikolinc.config.EnvLoader;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class JdbiProvider implements DatabaseProvider {

    private static Jdbi jdbi;

    @Override
    public void connect(){
        String url = EnvLoader.get("DB_URL","Hola");
        String user = EnvLoader.get("DB_USER","Hola");
        String password = EnvLoader.get("DB_PASS","Hola");

        jdbi = Jdbi.create(url,user,password);
        jdbi.installPlugin(new SqlObjectPlugin());
        System.out.println("Connected to database.");
    }

    @Override
    public void disconnect(){
        jdbi = null;
        System.out.println("Disconnected from database.");
    }

    @Override
    public Jdbi getConnection(){
        return jdbi;
    }
}
