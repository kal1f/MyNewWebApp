package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.dao.RoleDAO;
import database.entity.Role;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("SELECT * FROM role WHERE id=?");
            ps.setInt(1, id);

            rs=ps.executeQuery();


            while(rs.next()){
                role = new Role();
                role.setName((rs.getString(2)));
                role.setId(rs.getInt(1));
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

        return role;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("SELECT * FROM role");

            rs = ps.executeQuery();

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
    public int insertRole(String role) {
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareStatement("INSERT INTO role (name)" +
                    " VALUES(?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, role);

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            while (rs.next())
            {
                id = rs.getInt(1);
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
