package ufrn.br.ufrn;

public class produtos {
    
    private Integer id;
    private String nome;
    private String descricao;
    private float preco;

    public produtos(){

    }
    public produtos(Integer id, String nome, String descricao, float preco){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
    
    // get
    public Integer getId() {
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

}
