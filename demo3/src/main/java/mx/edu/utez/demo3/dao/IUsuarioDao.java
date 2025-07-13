package mx.edu.utez.demo3.dao;

import java.sql.SQLException;

public interface IUsuarioDao {

    public boolean login(String correo, String pass) throws SQLException;
}
