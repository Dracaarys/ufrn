package ufrn.br.ufrn;

public class produtos {
    public produtos(Integer id, String nome, String descricao, float preco,int estoque){
        super();
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }
    int id;
    String nome;
    String descricao;
    float preco;
    int estoque;

    // get
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getPreco() {
        return preco;
    }

    public Integer getEstoque(){return estoque;}


    //set
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void setEstoque(int estoque) {this.estoque = estoque;}
    public void incrementaEstoque() {
        this.estoque++;
    }
    public void diminuiEstoque() {
        this.estoque--;
    }

}
