package acesso_banco;

import classes_base.Disciplina;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO extends DAOAbstrato <Disciplina, Integer> {
    private final String scriptTabela = "CREATE TABLE IF NOT EXISTS disciplina (\n" +
            "    iddisciplina INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    av1 REAL,\n" +
            "    av2 REAL,\n" +
            "    media REAL,\n" +
            "    nome TEXT,\n" +
            "    fk_idaluno INTEGER,\n" +
            "    FOREIGN KEY (fk_idaluno) REFERENCES aluno(idaluno)\n" +
            ")";

    public DisciplinaDAO() {
        criarTabela();
    }

    @Override
    protected void criarTabela() {
        try (var stmt = conectar().createStatement()) {
            stmt.execute(scriptTabela);
        } catch (SQLException ex) {
            System.err.println("Erro ao criar tabela disciplina: " + ex.getMessage());
        }
    }

    public void inserir(float av1, float av2, float media, String nome, int idAluno) {
        try (var stmt = conectar().prepareStatement("INSERT INTO disciplina (" +
                "av1, av2, media, nome, fk_idaluno) VALUES (" +
                "?, ?, ?, ?, ?)")) {
            stmt.setFloat(1, av1);
            stmt.setFloat(2, av2);
            stmt.setFloat(3, media);
            stmt.setString(4, nome);
            stmt.setInt(5, idAluno);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir disciplina: " + ex.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        try (var stmt = conectar().prepareStatement("DELETE FROM disciplina WHERE iddisciplina = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao deletar disciplina: " + ex.getMessage());
        }
    }

    @Override
    public void alterar(Disciplina d) {
        try (var stmt = conectar().prepareStatement("UPDATE disciplina SET av1 = ?," +
                "av2 = ?, media = ?, nome = ?, fk_idaluno = ? WHERE iddisciplina = ?)")) {
            stmt.setFloat(1, d.getAv1());
            stmt.setFloat(2, d.getAv2());
            stmt.setFloat(3, d.calcularMedia());
            stmt.setString(4, d.getNome());
            stmt.setInt(5, d.getIdAluno());
            stmt.setInt(6, d.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao alterar disciplina: " + ex.getMessage());
        }
    }

    @Override
    public Disciplina selecionar(Integer id) {
        Disciplina d = null;

        try (var stmt = conectar().prepareStatement("SELECT * FROM disciplina WHERE iddisciplina = ?")) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            while (r.next()) {
                d = new Disciplina(r.getInt("iddisciplina"), r.getString("nome"),
                        r.getFloat("av1"), r.getFloat("av2"),
                        r.getInt("fk_idaluno"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar disciplina: " + ex.getMessage());
        }
        return d;
    }

    @Override
    public List<Disciplina> selecionarTodos() {
        List<Disciplina> lista = new ArrayList<>();

        try (var stmt = conectar().createStatement()) {
            var r = stmt.executeQuery("SELECT * FROM disciplina");
            while (r.next()) {
                lista.add(new Disciplina(r.getInt("iddisciplina"),
                        r.getString("nome"), r.getFloat("av1"),
                        r.getFloat("av2"), r.getInt("fk_idaluno")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar todas as disciplinas: " + ex.getMessage());
        }
        return lista;
    }
}
