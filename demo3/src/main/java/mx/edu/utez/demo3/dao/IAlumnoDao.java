package mx.edu.utez.demo3.dao;

import mx.edu.utez.demo3.model.Alumno;

import java.sql.SQLException;
import java.util.List;

public interface IAlumnoDao {

    List<Alumno> findAll() throws SQLException;
    Alumno findAlumnoById(int id) throws SQLException;
    void create(Alumno alumno) throws SQLException;
    void update(Alumno alumno) throws SQLException;
    void delete(int id) throws SQLException;
}
