package org.kie.workbench.backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;

public class DataLoader {

    @Resource(lookup = "java:jboss/datasources/ExampleDS")
    private DataSource dataSource;

    public DataLoader() {

    }

    public void load() {

        try ( final Connection conn = dataSource.getConnection() ) {
            final ResultSet rs = conn.createStatement().executeQuery( "SELECT 'aaa' FROM 1 as one" );
            System.err.println( rs.getString( "one" ) );
            rs.next();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

    }
}
