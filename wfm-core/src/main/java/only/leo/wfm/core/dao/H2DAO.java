package only.leo.wfm.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class H2DAO {
    @Autowired
    private DataSource connectionPool;
    /**
     * 建表方法
     * @return
     * @throws SQLException
     */
    public Boolean executeSQL(String ddl) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connectionPool.getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            stmt = conn.createStatement();
            stmt.execute(ddl);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            releaseConnection(conn, stmt, null);
        }
        return false;
    }

 
    private void releaseConnection(Connection conn, Statement stmt,
                                          ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
