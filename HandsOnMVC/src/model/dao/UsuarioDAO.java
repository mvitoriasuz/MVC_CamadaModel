package model.dao;

import java.util.*;

import java.sql.*;
import model.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

    // Incluir dependencia de conexão
    private final Connection conexao;

    // Fazer inversão/injeção de dependência
    public UsuarioDAO(Connection connection) {
        this.conexao = connection;
        init();
    }

    private void init() {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS fatec;\n";
        String createTable = "CREATE TABLE IF NOT EXISTS fatec.usuarios("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "nome VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) NOT NULL UNIQUE, "
                + "senha VARCHAR(100) NOT NULL);";

        try (Statement stm = conexao.createStatement()) {
            stm.execute(createDatabase);
            stm.execute(createTable);
        } catch (Exception e) {
            System.out.println("Erro ao criar a entidade usuarios. Erro: "
                    + e.getLocalizedMessage());
        }
    }

    @Override
    public void salvar(Usuario usuario) throws Exception {
        String sql = "INSERT INTO fatec.usuarios (nome, email, senha) "
                + "values ('%s', '%s', '%s')";

        System.out.println("PASSEI POR AQUI ");

        try (Statement stm = conexao.createStatement()) {
            stm.execute(String.format(sql,
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha()));
        } catch (Exception e) {
            System.out.println("Erro ao criar usuario. Erro: "
                    + e.getLocalizedMessage());
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM fatec.usuarios WHERE email = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuario por email. Erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void atualizar(Usuario usuario) {
        // Atualizar usuário existente em base de dados
        String sql = "UPDATE fatec.usuarios SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getEmail());
            stm.setString(3, usuario.getSenha());
            stm.setInt(4, usuario.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuario. Erro: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void exluir(Integer id) {
        // Atualizar usuário existente em base de dados
        String sql = "DELETE FROM fatec.usuarios WHERE id = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuario. Erro: " + e.getLocalizedMessage());
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        String sql = "SELECT * FROM fatec.usuarios where email = ";
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stm = conexao.createStatement();
                ResultSet rst = stm.executeQuery(sql)) {
            while (rst.next()) {
                Usuario usuario = new Usuario();
                usuario.setNome(rst.getString("nome"));
                usuario.setEmail(rst.getString("email"));
                usuario.setSenha(rst.getString("senha"));

                usuarios.add(usuario);
            }

            return usuarios;
        } catch (Exception e) {
            System.out.println("Falha ao recuperar usuarios.");
        }

        return usuarios;
    }

}
