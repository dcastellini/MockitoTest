package org.dcastellini.junit.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import org.dcastellini.junit.exceptions.DineroInsuficienteException;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cuenta  {

    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto){

        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteException("Dinero insuficiente");
        }
        this.saldo = nuevoSaldo;

    }

    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Cuenta)){
            return false;
        }
        Cuenta cuenta = (Cuenta) obj;
        if(this.persona == null || this.saldo == null){
            return false;
        }
        return this.persona.equals(cuenta.getPersona()) && this.saldo.equals(cuenta.getSaldo());
    }
}
