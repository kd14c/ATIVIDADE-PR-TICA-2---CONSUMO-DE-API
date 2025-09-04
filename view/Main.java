package br.edu.fatecpg.consomeapi.view;

import br.edu.fatecpg.consomeapi.dao.EnderecoDAO;
import br.edu.fatecpg.consomeapi.model.Endereco;
import br.edu.fatecpg.consomeapi.service.BuscaEndereco;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        Scanner sc = new Scanner(System.in);
        BuscaEndereco be = new BuscaEndereco();
        EnderecoDAO dao = new EnderecoDAO();
        Gson gson = new Gson();

        int opcao;
        do {
            System.out.println("1 - Buscar Endereço");
            System.out.println("2 - Mostrar todos endereços salvos no banco");
            System.out.println("3 - Sair");
            System.out.print("Digite a opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Digite o CEP: ");
                    String cep = sc.nextLine();
                    String resposta = be.obterEndereco(cep);
                    Endereco endereco = gson.fromJson(resposta, Endereco.class);
                    System.out.println("Endereço encontrado:");
                    System.out.println(endereco);

                    System.out.print("Deseja salvar no banco de dados? (s/n): ");
                    String salvar = sc.nextLine();
                    if (salvar.equalsIgnoreCase("s")) {
                        dao.salvarEndereco(endereco);
                        System.out.println("Endereço salvo com sucesso!");
                    } else {
                        System.out.println("Endereço não salvo.");
                    }
                    break;

                case 2:
                    List<Endereco> enderecos = dao.listarEnderecos();
                    if (enderecos.isEmpty()) {
                        System.out.println("Nenhum endereço salvo.");
                    } else {
                        System.out.println("\nEndereços Salvos");
                        for (Endereco e : enderecos) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 3);

        sc.close();
    }
}