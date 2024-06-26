package classes_base;

public class Disciplina {
    private int id;
    private String nome;
    private float av1;
    private float av2;
    private int idAluno;

    public Disciplina(int id, String nome, float av1, float av2, int idAluno) {
        this.id = id;
        this.nome = nome;
        this.av1 = av1;
        this.av2 = av2;
        this.idAluno = idAluno;
    }

    public Disciplina() {}

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getAv1() {
        return av1;
    }

    public void setAv1(float av1) {
        this.av1 = av1;
    }

    public float getAv2() {
        return av2;
    }

    public void setAv2(float av2) {
        this.av2 = av2;
    }

    public float calcularMedia() {
        return (getAv1() + getAv2()) / 2;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }
}
