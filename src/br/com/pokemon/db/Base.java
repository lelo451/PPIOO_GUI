package br.com.pokemon.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe responsável pela comunicação com o banco de dados
 */
public class Base {

    /**
     * Metódo responsável pela conexão com o banco
     * @return a conexão com o banco de dados
     * @throws Exception caso a conexão não seja bem sucedida ou o banco não seja encontrado
     */
    private static Connection getConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/pokemon?useSSL=false&createDatabaseIfNotExist=true";
        String username = "root";
        String password = "";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Metódo responsável por criar as tabelas no banco de dados
     * @throws FileNotFoundException para o caso do arquivo contendo os dados não ser achado
     */
    public void criarBase() throws FileNotFoundException {
        System.out.println("Inicializando a Criação da Base de Dados");
        File atk = new File("ataques.csv");
        File pokes = new File("Pokemons.csv");
        Scanner sc = new Scanner(atk);
        Scanner sc2 = new Scanner(pokes);
        List<String> ataque = Stream.of(sc.nextLine().split(",")).collect(Collectors.toList());
        List<String> pokemon = Stream.of(sc2.nextLine().split(",")).collect(Collectors.toList());
        final String ATAK_TABLE = "create table if not exists ataque (" +
                ataque.get(0) + " INT PRIMARY KEY, " +
                ataque.get(1) + " VARCHAR(30), " +
                ataque.get(2) + " VARCHAR(30), " +
                ataque.get(3) + " VARCHAR(30), " +
                ataque.get(4) + " VARCHAR(30), " +
                ataque.get(5) + " VARCHAR(30), " +
                ataque.get(6) + " VARCHAR(30), " +
                ataque.get(7) + " VARCHAR(30) ) DEFAULT CHARSET=UTF8 ENGINE=InnoDB";
        final String POKEMON_TABLE = "create table if not exists pokemon (" +
                pokemon.get(0) + " INT PRIMARY KEY, " +
                pokemon.get(1) + " VARCHAR(30), " +
                pokemon.get(2) + " VARCHAR(30), " +
                pokemon.get(3) + " VARCHAR(30), " +
                pokemon.get(4) + " VARCHAR(30), " +
                pokemon.get(5) + " VARCHAR(30), " +
                pokemon.get(6) + " VARCHAR(30), " +
                pokemon.get(7) + " VARCHAR(30), " +
                pokemon.get(8) + " VARCHAR(30) ) DEFAULT CHARSET=UTF8 ENGINE=InnoDB";
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(ATAK_TABLE);
            stmt.executeUpdate(POKEMON_TABLE);
            while (true) {
                try {
                    ataque = Stream.of(sc.nextLine().split(",")).collect(Collectors.toList());
                } catch (Exception e) {
                    break;
                }
                if(ataque.size() == 7) {
                    ataque.add("");
                }
                final String insert = "insert into ataque (ID,Name,Type,PP,Power,Accuracy,Classe,Parâmetros) values (" +
                        ataque.get(0) + ",'" + ataque.get(1) + "','" + ataque.get(2) + "'," + ataque.get(3) + "," + ataque.get(4) + "," +
                        ataque.get(5) + ",'" + ataque.get(6) + "','" + ataque.get(7) + "');";
                try {
                    stmt.executeUpdate(insert);
                } catch (Exception e) {
                    break;
                }
            }
            while (true) {
                try {
                    pokemon = Stream.of(sc2.nextLine().split(",")).collect(Collectors.toList());
                } catch (Exception e) {
                    break;
                }
                if(pokemon.get(0).equals("83")) {
                    pokemon.set(1, "Farfetch''d");
                }
                final String insert = "insert into pokemon (ID,Especie,Tipo_1,Tipo_2,Base_HP,Base_ATK,Base_DEF,Base_SPE,Base_SPD) values (" +
                        pokemon.get(0) + ",'" + pokemon.get(1) + "','" + pokemon.get(2) + "','" + pokemon.get(3) + "'," + pokemon.get(4) + "," +
                        pokemon.get(5) + "," + pokemon.get(6) + "," + pokemon.get(7) + "," + pokemon.get(8) + ");";
                try {
                    stmt.executeUpdate(insert);
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                connection.close();
                System.out.println("Base de Dados Criada com Sucesso!");
            } catch (SQLException e) {
                System.out.println("Por Favor Informe Quando for Executar o Programa o Driver de Conexão com o Banco de Dados!");
                System.exit(0);
            }
        }
    }

    /**
     * Metódo responsável por retornar todos os ataques/pokemons dependendo do @param pokemon passado
     * @param pokemon booleano para saber se os dados a serem retornados são do pkemons ou do ataque
     * @return retorna um ArrayList de vetor de String contendo todos os dados dos ataques/pokemons
     */
    public ArrayList<String[]> getAllData(boolean pokemon) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<String[]> result = new ArrayList<>();
        try {
            conn= getConnection();
            stmt = conn.createStatement();
            String query = null;
            if(pokemon) {
                query = "select * from pokemon";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String[] data = new String[2];
                    data[0] = rs.getString("ID");
                    data[1] = rs.getString("Especie");
                    result.add(data);
                }
            } else {
                query = "select * from ataque";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String[] data = new String[2];
                    data[0] = rs.getString("ID");
                    data[1] = rs.getString("Name");
                    result.add(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Metódo responsável por retornar todos os dados sobre um pokemons/ataque
     * @param id id do pokemon/ataque na base de dados
     * @param pokemon booleano para saber se os dados a serem retornados são do pkemons ou do ataque
     * @return retorna um ArrayList de String contendo os dados do ataque/pokemon
     */
    public ArrayList<String> getData(int id, boolean pokemon) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<String> resultado = new ArrayList<>();
        try {
            conn= getConnection();
            stmt = conn.createStatement();
            String query = null;
            if(pokemon) {
                query = "select * from pokemon where ID = " + id;
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()) {
                    resultado.add(rs.getString("ID"));
                    resultado.add(rs.getString("Especie"));
                    resultado.add(rs.getString("Tipo_1"));
                    resultado.add(rs.getString("Tipo_2"));
                    resultado.add(rs.getString("Base_HP"));
                    resultado.add(rs.getString("Base_ATK"));
                    resultado.add(rs.getString("Base_DEF"));
                    resultado.add(rs.getString("Base_SPE"));
                    resultado.add(rs.getString("Base_SPD"));
                }
            } else {
                query = "select * from ataque where ID = " + id;
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()) {
                    resultado.add(rs.getString("ID"));
                    resultado.add(rs.getString("Name"));
                    resultado.add(rs.getString("Type"));
                    resultado.add(rs.getString("PP"));
                    resultado.add(rs.getString("Power"));
                    resultado.add(rs.getString("Accuracy"));
                    resultado.add(rs.getString("Classe"));
                    resultado.add(rs.getString("Parâmetros"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

}