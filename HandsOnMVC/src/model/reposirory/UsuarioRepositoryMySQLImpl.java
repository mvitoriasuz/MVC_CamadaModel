package model.reposirory;

import java.util.List;

import model.Usuario;
import model.dao.IUsuarioDAO;

public class UsuarioRepositoryMySQLImpl implements UsuarioRepository {

    private final IUsuarioDAO dao;

    public UsuarioRepositoryMySQLImpl(IUsuarioDAO dao) {
        this.dao = dao;
    }

    @Override
    public void criar(Usuario usuario) throws Exception {
        dao.salvar(usuario);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        // Retornar um usuário existente com base no e-mail recebido como parametro.
        return dao.buscarPorEmail(email);
    }

    @Override
    public List<Usuario> obterTodos() {
        return dao.buscarTodos();
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        // Buscar usuario existente e atualizar os dados, persistindo a alteração.
        Usuario UsuarioLocalizado = dao.buscarPorEmail(usuario.getEmail());
        // Atualizar o usuario
        if (UsuarioLocalizado != null) {
            UsuarioLocalizado.setNome(usuario.getNome());
            UsuarioLocalizado.setSenha(usuario.getSenha());
            dao.atualizar(UsuarioLocalizado);
            return UsuarioLocalizado;
        }

        return null;
    }

    @Override
    public void excluir(Integer id) {
        dao.exluir(id);
    }
}
