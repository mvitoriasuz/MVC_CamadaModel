import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;

import model.Usuario;
import model.dao.ConexaoFactory;
import model.dao.IUsuarioDAO;
import model.dao.UsuarioDAO;
import model.reposirory.*;
import services.UsuarioService;

public class MvcApp {
    public static void main(String[] args) throws Exception {

        /*
         * Este conjunto de instruções inicializaram as dependencias
         * para o uso do serviço de contatos utilizando o repositório
         * com o SGBD MySQL.
         */
        Connection conexao = ConexaoFactory.getConexao();
        IUsuarioDAO dao = new UsuarioDAO(conexao);
        UsuarioRepository reposirory = new UsuarioRepositoryMySQLImpl(dao);

        UsuarioMemoriaRepositoryImpl repo = new UsuarioMemoriaRepositoryImpl();
        UsuarioService service = new UsuarioService(reposirory); // estava repo

        Scanner in = new Scanner(System.in);
        System.out.println("Digite o nome: ");
        String nome = in.nextLine();
        System.out.println("Digite o e-mail: ");
        String email = in.nextLine();
        System.out.println("Digite o senha: ");
        String senha = in.nextLine();

        // TODO: Criar mais 2 usuarios
        Usuario u1 = new Usuario(null, "Usuario1", "Usuario1@email.com", "123def");
        Usuario u2 = new Usuario(null, "Usuario2", "Usuario2@email.com", "123abc");
        Usuario u3 = new Usuario(null, "Usuario3", "Usuario3@email.com", "123ghj");

        service.salvar(u1);
        service.salvar(u2);
        service.salvar(u3);

        // TODO: Exibir os usuarios cadastrados
        List<Usuario> usuarios = service.obterTodos();
        for (Usuario usuario : usuarios) {
            System.out.println(usuarios);
        }

        // TODO: Remover o primeiro usuario criado.
        if (u1.getId() != null) {
            service.excluir(u1.getId());
        }

        // TODO: Buscar e exibir o segundo usuario criado com base no e-mail
        Usuario usuarioLocalizado = service.buscarPorEmail(u2.getEmail());
        System.out.println(usuarioLocalizado);

        // TODO: Reinserir os usuários no repositório em memória

        try {
            service.salvar(u2);
            service.salvar(u3);
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados no banco de dados: " + e.getMessage());
        }

        UsuarioRepository memUsuarioRepository = new UsuarioMemoriaRepositoryImpl();
        UsuarioService mUsuarioService = new UsuarioService(memUsuarioRepository);
    }
}
