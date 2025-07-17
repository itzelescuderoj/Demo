package mx.edu.utez.demo3.dao;

import mx.edu.utez.demo3.model.Carrera;

import java.sql.SQLException;
import java.util.List;

public interface ICarreraDao {
    List<Carrera> findAll() throws SQLException;
    Carrera findById(int id) throws SQLException;
    void create(Carrera carrera) throws SQLException;
    void update(Carrera carrera) throws SQLException;
    void delete(int id) throws SQLException;
}
