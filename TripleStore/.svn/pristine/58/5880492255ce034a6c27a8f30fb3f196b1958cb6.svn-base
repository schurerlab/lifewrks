package edu.miami.ccs.life.gui;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.derby.drda.NetworkServerControl;

import java.io.File;
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class DerbyIndexManager {

    private PoolingDataSource dataSource;

    private static final String jdbcDerbyDbName = "indexSparqlDb";

    private static final String indexSparqlTable = "indexSparqlTable";

    private static final String createTableQuery =
            "CREATE TABLE " + indexSparqlTable + "( \n" +
                    "indexId \t INTEGER NOT NULL \n" +
                    "        \t PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
                    "        \t (START WITH 1, INCREMENT BY 1), " +
                    "sparqlQuery \t VARCHAR(4000), \n" +
                    "sparqlLable \t VARCHAR(4000), \n" +
                    "difficulty  \t INT \n" +
                    ")";

    private static final String insertIntoTableQuery =
            "INSERT INTO " + indexSparqlTable + "\n" +
                    "   (sparqlQuery, sparqlLable, difficulty) \n" +
                    "VALUES (?, ?, ?)";

    private static final String selectAllQuery =
            "SELECT * FROM " + indexSparqlTable;

    private static final String mainTableExistTest =
            "SELECT * FROM " + indexSparqlTable + " WHERE indexId=1";

    private static final String deleteARow_ =
            "DELETE FROM " + indexSparqlTable + " \n" +
                    "WHERE indexId=";

    private SparqlExampleIndex indices;

    private final String dbHost;

    private final int dbPort;

    public DerbyIndexManager(SparqlExampleIndex indices, String dbHost, int dbPort) {
        this.indices = indices;
        this.dbHost = dbHost;
        this.dbPort = dbPort;
    }

    public void init() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        // start the server
        final Thread serverThread = new Thread(new Runnable() {
            public void run() {
                try {
                    NetworkServerControl server =
                            new NetworkServerControl(InetAddress.getByName(dbHost), dbPort);
                    server.start(null);
                } catch (Exception e) {
                    System.err.println("Problem starting the network server");
                    System.exit(-89);
                }

            }
        });
        serverThread.start();
        // create the database only if it does not exists 
        // derby with embedded driver
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

        String url;
        if (!(new File(dbHost).exists())) {
            System.err.println("db does not exists");
            url = "jdbc:derby:" + dbHost + "/" + jdbcDerbyDbName + ";create=true";
        } else {
            System.out.println("db exits");
            url = "jdbc:derby:" + dbHost + "/" + jdbcDerbyDbName;
        }

        ObjectPool connectionPool = new GenericObjectPool(null);
        ConnectionFactory connectionFactory =
                new DriverManagerConnectionFactory(url, null, null);
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false,
                        true);
        dataSource = new PoolingDataSource(connectionPool);
        // create the tables
        createTable();
    }

    public boolean isMainTableExists() {
        Connection conn = null;
        Statement stmt = null;
        boolean ret = false;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            stmt.execute(mainTableExistTest);
            ret = true;
        } catch (SQLException e) {
            System.err.println("db error main table exist " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return ret;
    }

    public void createTable() {
        if (!isMainTableExists()) {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                stmt.execute(createTableQuery);
            } catch (SQLException e) {
                System.err.println("db error 1 " + e.getMessage());
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (Exception e) {
                    // do nothing
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                    // do nothing
                }
            }
            indices.populate(this);
        }
    }

    public void insert(Index index) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(insertIntoTableQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, index.getQuery());
            stmt.setString(2, index.toString());
            stmt.setInt(3, index.getLevel());
            int rowCnt = stmt.executeUpdate();
            System.out.println("rowCnt=" + rowCnt);
            rset = stmt.getGeneratedKeys();
            while (rset.next()) {
                int anId = rset.getInt(1);
                System.out.println("ids=" + anId);
                index.setDbId(anId);
            }

        } catch (SQLException e) {
            System.err.println("db error 2" + e.getMessage());
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public void delete(Index index) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            String del = deleteARow_ + index.getDbId();
            stmt.execute(del);
        } catch (SQLException e) {
            System.err.println("db error 3" + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public List<Index> selectAll() {
        List<Index> indexList = new ArrayList<Index>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery(selectAllQuery);
            Index index;
            while (rset.next()) {
                String q = rset.getString("sparqlQuery");
                String l = rset.getString("sparqlLable");
                int d = rset.getInt("difficulty");
                index = new Index(l, q, d);
                indexList.add(index);
            }
        } catch (SQLException e) {
            System.err.println("db error " + e.getMessage());
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return indexList;
    }
}
