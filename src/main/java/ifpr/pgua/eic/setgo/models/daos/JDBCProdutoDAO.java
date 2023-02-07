package ifpr.pgua.eic.setgo.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.setgo.models.FabricaConexoes;
import ifpr.pgua.eic.setgo.models.entities.Produto;
import ifpr.pgua.eic.setgo.models.results.Result;

public class JDBCProdutoDAO implements ProdutoDAO {
    private FabricaConexoes fabricaConexoes;
    
    public JDBCProdutoDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }
    
    @Override
    public List<Produto> buscarTodos(){
        try {
           Connection con = fabricaConexoes.getConnection();
           
           PreparedStatement prep = con.prepareStatement("SELECT * FROM produtos");
           
           ResultSet result = prep.executeQuery();
           
           ArrayList<Produto> produtos = new ArrayList<>();
           
           while(result.next()){
               Integer id = result.getInt("id");
               String nome = result.getString("nome");
               String descricao = result.getString("descricao");
               float preco = result.getFloat("preco");
               
               Produto produto = new Produto(id, nome, descricao, preco);
               
               produtos.add(produto);
           }
           
           result.close();
           prep.close();
           con.close();
           
           return produtos;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
        
    }

    @Override
    public Result inserir(Produto produto){
        try {
            Connection con = fabricaConexoes.getConnection();
            
            PreparedStatement prep = con
                    .prepareStatement("INSERT INTO produtos (nome,descricao,preco) VALUES (?,?,?)");
            
            prep.setString(1, produto.getNome());
            prep.setString(2, produto.getDescricao());
            prep.setFloat(3, produto.getPreco());
            
            prep.executeUpdate();
            
            prep.close();
            con.close();
            
            return Result.success("Tudo certo!!!! TOme!!");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
        
}
    
