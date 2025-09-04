package br.edu.fatecpg.consomeapi.dao;

import br.edu.fatecpg.consomeapi.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_endereco";
    private static final String USER = "fatec";
    private static final String PASSWORD = "fatec777";

    public void salvarEndereco(Endereco endereco) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        String sql = "INSERT INTO enderecos (cep, rua, bairro, localidade, estado) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, endereco.getCep());
        stmt.setString(2, endereco.getRua());
        stmt.setString(3, endereco.getBairro());
        stmt.setString(4, endereco.getLocalidade());
        stmt.setString(5, endereco.getEstado());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Endereco> listarEnderecos() throws SQLException {
        List<Endereco> lista = new ArrayList<>();
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM enderecos");

        while (rs.next()) {
            Endereco e = new Endereco(
                    rs.getString("cep"),
                    rs.getString("rua"),
                    rs.getString("bairro"),
                    rs.getString("localidade"),
                    rs.getString("estado")
            );
            lista.add(e);
        }

        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }
}