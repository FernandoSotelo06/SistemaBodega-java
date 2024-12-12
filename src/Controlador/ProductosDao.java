/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando
 */
public class ProductosDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    public boolean RegistrarProductos(Productos pro) {
        String sql = "INSERT INTO producto (descripcion, cantidad, precio, categoria, vencimiento, proveedor_ruc) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1,pro.getDescripcion());
            ps.setInt(2,pro.getCantidad());
            ps.setDouble(3,pro.getPrecio());
            ps.setString(4,pro.getCategoria());
            ps.setString(5, pro.getVencimiento());
            ps.setString(6, pro.getProveedor());
            ps.execute();
            return true;
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try{
                con.close();
            } catch(SQLException e){
                System.out.println(e.toString());
            }
        }
    }
    public void ConsularProveedor(JComboBox proveedor){
        String sql= "SELECT nombre FROM proveedor";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {                
                proveedor.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public List ListarProductos(){
        List<Productos>Listapro=new ArrayList();
        String sql = "SELECT * FROM producto";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()) {                
                Productos pro = new Productos(); 
                pro.setCodigo(rs.getInt("codigo"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setCantidad(rs.getInt("cantidad"));
                pro.setPrecio(rs.getDouble("precio"));
                pro.setCategoria(rs.getString("categoria"));
                pro.setVencimiento(rs.getString("vencimiento"));
                pro.setProveedor(rs.getString("proveedor_ruc"));
                Listapro.add(pro);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return Listapro;
    }
        public boolean EliminarProducto(int id){
        String sql="DELETE FROM producto WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }   
            public boolean EditarProductos(Productos pro){
       String sql = "UPDATE producto SET descripcion=?, cantidad=?, precio=?, categoria=?, vencimiento=?, proveedor_ruc=? WHERE codigo=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, pro.getDescripcion());
           ps.setInt(2, pro.getCantidad());
           ps.setDouble(3, pro.getPrecio());
           ps.setString(4, pro.getCategoria());
           ps.setString(5, pro.getVencimiento());
           ps.setString(6, pro.getProveedor());
           ps.setInt(7, pro.getCodigo());
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
   }
        public Productos BuscarPro(String cod){
        Productos producto = new Productos();
        String sql = "SELECT * FROM producto WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setCodigo(rs.getInt("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
}

