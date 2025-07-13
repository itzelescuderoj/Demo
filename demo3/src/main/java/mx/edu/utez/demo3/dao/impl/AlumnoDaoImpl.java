package mx.edu.utez.demo3.dao.impl;

import mx.edu.utez.demo3.config.DBConnection;
import mx.edu.utez.demo3.dao.IAlumnoDao;
import mx.edu.utez.demo3.model.Alumno;
import mx.edu.utez.demo3.model.Asignatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDaoImpl implements IAlumnoDao {

    @Override
    public List<Alumno> findAll() throws SQLException {
        String sql="SELECT * FROM ALUMNO";//WHERE
        List<Alumno> alumnos= new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Alumno alumno= new Alumno();
                alumno.setNombre(rs.getString("NOMBRES"));
                alumno.setApellidos(rs.getString("APELLIDOS"));
                alumno.setCorreo(rs.getString("CORREO"));
                alumno.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO").toLocalDate());
                alumno.setIdCarrera(rs.getInt("ID_CARRERA"));
                alumno.setId(rs.getInt("ID"));
                alumno.setAsignaturas(getAsignaturaByAlumno(alumno.getId()));
                alumnos.add(alumno);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return alumnos;
    }

    @Override
    public Alumno findAlumnoById(int id) throws SQLException {
      String sql="SELECT * FROM ALUMNO WHERE ID=?";
      Alumno alumno=new Alumno();
      try {
          Connection con = DBConnection.getConnection();
          PreparedStatement ps= con.prepareStatement(sql);
          ps.setInt(1,id);
          ResultSet rs = ps.executeQuery();
          while (rs.next()){
              alumno.setNombre(rs.getString("NOMBRES"));
              alumno.setApellidos(rs.getString("APELLIDOS"));
              alumno.setCorreo(rs.getString("CORREO"));
              alumno.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO").toLocalDate());
              alumno.setIdCarrera(rs.getInt("ID_CARRERA"));
              alumno.setId(rs.getInt("ID"));

          }
          return alumno;
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
    }

    @Override
    public void create(Alumno alumno) throws SQLException {

    }

    @Override
    public void update(Alumno alumno) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    private List<Asignatura> getAsignaturaByAlumno(int id) throws SQLException{
        String sql = "SELECT\n" +
                "    aa.id_alumno,\n" +
                "    aa.id_asignatura,\n" +
                "    asig.*\n" +
                "FROM\n" +
                "         alumno_asignatura aa\n" +
                "    JOIN asignatura asig ON aa.id_asignatura = asig.id\n" +
                "    where aa.id_alumno=?";
        List<Asignatura> asignaturas=new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Asignatura asignatura=new Asignatura();
                asignatura.setNombre(rs.getString("NOMBRE"));
                asignatura.setDescripcion(rs.getString("DESCRIPCION"));
                asignaturas.add(asignatura);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::   ");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return asignaturas;
    }

    public static void main(String[] args) {
        AlumnoDaoImpl dao = new AlumnoDaoImpl();

        try{
           List<Alumno> alumnos= dao.findAll();
           for(Alumno a : alumnos){
               System.out.println(a.getNombre());
               for(Asignatura asig : a.getAsignaturas()){
                   System.out.println("--------");
                   System.out.print(asig.getNombre()+",");
               }
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
