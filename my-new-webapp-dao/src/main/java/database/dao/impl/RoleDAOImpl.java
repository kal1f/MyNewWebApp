package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.dao.RoleDAO;
import database.entity.Role;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private final ConnectionProvider connectionProvider;

    static final Logger LOGGER = Logger.getLogger(ProductDAOImpl.class);

    public RoleDAOImpl() {
        connectionProvider = new ConnectionProviderImpl();
    }

    public RoleDAOImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Role getRoleById(Integer id) {
        Role role = null;
        Connection con = null;
        CallableStatement ps = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareCall("{call getRoleById (?, ?)}");
            ps.setInt(1, id);
            ps.registerOutParameter(1, Types.INTEGER);
            ps.registerOutParameter(2, Types.VARCHAR);

            ps.execute();


            try {
                role = new Role();
                role.setName((ps.getString(2)));
                role.setId(ps.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        catch (Exception e){
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);

        }

        return role;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        Connection con = null;
        CallableStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareCall("{ call getRoles (?)}");
            ps.registerOutParameter(1, OracleTypes.CURSOR);
            ps.execute();

            rs = (ResultSet) ps.getObject(1);
            while(rs.next()){
                Role role = new Role(rs.getInt("id"), rs.getString("name"));
                roles.add(role);
            }
        }
        catch (Exception e){
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return roles;
    }

    @Override
    public int updateRoleById(Role role){
        int rows = 0;
        Connection con = null;
        CallableStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareCall("{? = call updateRoleById (?,?)}");
            ps.registerOutParameter(1, Types.INTEGER);
            ps.setString(2, role.getName());
            ps.setInt(3, role.getId());

            ps.execute();

            rows = ps.getInt(1);

        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return rows;
    }

    @Override
    public int insertRole(String role) {
        int id = 0;
        Connection con = null;
        CallableStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareCall("{? = call insertRole(?)}");
            ps.registerOutParameter(1, Types.INTEGER);
            ps.setString(2, role);

            ps.execute();

            try {
                id = ps.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return id;
    }


}
