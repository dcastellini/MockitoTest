package org.dcastellini.junit.models;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Banco {

  private List<Cuenta> cuentaList;
  private String nombre;

  public Banco(){
    this.cuentaList = new ArrayList<Cuenta>();
  }


  public void transferir(Cuenta cuentaOrigen, Cuenta cuentaDestino, BigDecimal monto) {
    cuentaOrigen.debito(monto);
    cuentaDestino.credito(monto);
  }

  public void addCuenta(Cuenta cuenta){
    cuentaList.add(cuenta);
    cuenta.setBanco(this);
  }


}
