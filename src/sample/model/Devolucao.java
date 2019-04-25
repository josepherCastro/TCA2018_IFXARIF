package sample.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Devolucao {
    private  Retirada fk_Retirada;
    private double quantidade;
    private LocalDateTime dataHora;

    public Devolucao(Retirada fk_Retirada, double quantidade, LocalDateTime dataHora) {
        this.fk_Retirada = fk_Retirada;
        this.quantidade = quantidade;
        this.dataHora = dataHora;
    }

    public Retirada getFk_Retirada() {
        return fk_Retirada;
    }

    public void setFk_Retirada(Retirada fk_Retirada) {
        this.fk_Retirada = fk_Retirada;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
