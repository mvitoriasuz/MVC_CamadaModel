package model.dao;

import java.util.List;
import model.Usuario;

public interface IUsuarioDAO {

    /**
     * Metodo responsavel por salvar usuario
     * 
     * @param usuario
     *                usuario a ser salvo
     * @throws Exception uma exception qualquer
     */
    void salvar(Usuario usuario) throws Exception;

    /**
     * 
     * @param email
     * @return
     */
    Usuario buscarPorEmail(String email);

    void atualizar(Usuario usuario);

    void exluir(Integer id);

    List<Usuario> buscarTodos();
}
