public class Cota {

    protected int id;
    protected int id_titular;
    protected String codigo;
    protected String nome;
    protected Double valor;
    protected boolean vendendo;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIdTitular(int id_titular) {
        this.id_titular = id_titular;
    }

    public int getIdTitular() {
        return id_titular;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public void setVendendo(boolean vendendo) {
        this.vendendo = vendendo;
    }

    public boolean getVendendo() {
        return this.vendendo;
    }

}