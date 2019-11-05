
package baserelacionalb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BaseRelacionalB {
Connection conn;

public Connection Conexion() throws SQLException{
    String driver = "jdbc:oracle:thin:";
    String host = "localhost.localdomain";
    String porto = "1521";
    String sid = "orcl";
    String usuario = "hr";
    String password = "hr";
    String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;
    
    Connection conn = DriverManager.getConnection(url);
    
    return conn;
    
}
 public void Cerrar() throws SQLException {

      conn.close();
    
  }
 public void lista() throws SQLException{
     //Esto permite crear un ResulSet de tipo "scrollable" y "updatable"
     String sql ="Select * from produtos";
     Connection conn = Conexion();
     Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
     ResultSet rs = st.executeQuery(sql);
    
     while(rs.next()){
            System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getInt(3));
 }
 rs.close();
 }
 //metodo de actualizacion de produtos mediante ResultSet
 public void actualizarProduto(String codigo, int prezo) throws SQLException{
  Connection conn = Conexion();
  try{
      Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = st.executeQuery("select produtos.* from produtos");
      
      while(rs.next()){
          System.out.println(rs.getString("codigo"));
              if (rs.getString(1).equals(codigo)) {
                    rs.updateInt("precio", prezo);
                      rs.updateRow();
    }

            }
            System.out.println("Prezo do produto actaulizado");
        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro no actualizado do produto");

        }

}
 //Metodo de insercion de produtos mediante Result set
 public void inserirProduto(String codigo, String descricion, int precio) throws SQLException{
     Connection conn = Conexion();
     try{
         Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
         ResultSet rs = st.executeQuery("select produtos.* from produtos");
         rs.moveToInsertRow();
         rs.updateString("codigo", codigo);
         rs.updateString("descricion", descricion);
         rs.updateInt("precio", precio);
         rs.insertRow();
         
         System.out.println("Novo produto inserido");
     }catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro no inserido do produto");
         
     }
 }
 //metodo de borrado mediante ResultSet
  public void borrarFila(String codigo) throws SQLException{
      Connection conn = Conexion();
      try{
          Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
          ResultSet rs = st.executeQuery("select produtos.* from produtos");
          
          while (rs.next()) {

                if (rs.getString(1).equalsIgnoreCase(codigo)) {

                    rs.deleteRow();
                    //break
                    break;

                }

            }
            System.out.println("Produto eliminado");

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }

}
      
          
          
 

    public static void main(String[] args) throws SQLException {
       BaseRelacionalB obj = new BaseRelacionalB();
//       obj.lista();
//       obj.actualizarProduto("p5", 37);
//       obj.inserirProduto("p10", "martelo", 10);
       obj.borrarFila("p10");
       
    }
    
}
