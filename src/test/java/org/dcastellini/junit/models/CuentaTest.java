package org.dcastellini.junit.models;

import org.dcastellini.junit.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

  @Test
  void test_nombre_cuenta() {
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal(1000.1234));
    String esperado = "Andres";
    String actual = cuenta.getPersona();

    assertNotNull(actual, "La cuenta no puede ser nula.");
    assertEquals(esperado, actual, "El nombre de la cuenta no es el esperado.");
    assertTrue(actual.equals("Andres"), "Nombre de cuenta esperada debe ser igual a la real");
  }

  @Test
  void test_saldo_cuentas() {
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));

    assertNotNull(cuenta.getSaldo());
    assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
    assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
  }

  @Test
  void test_referencia_de_cuenta() {
    Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
    Cuenta cuentaDos = new Cuenta("John Roe", new BigDecimal("8900.9997"));

    assertEquals(cuenta, cuentaDos);
  }

  @Test
  void test_debito_cuenta() {
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
    cuenta.debito(new BigDecimal(100));

    assertNotNull(cuenta.getSaldo());
    assertEquals(900, cuenta.getSaldo().intValue());
    assertEquals("900.12345", cuenta.getSaldo().toPlainString());

  }

  @Test
  void test_credito_cuenta() {
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
    cuenta.credito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1100, cuenta.getSaldo().intValue());
    assertEquals("1100.12345", cuenta.getSaldo().toPlainString());

  }

  @Test
  void test_dinero_insuficiente_exception_cuenta() {
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));

    Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
      cuenta.debito(new BigDecimal(1500));
    });
    String actual = exception.getMessage();
    String esperado = "Dinero insuficiente";

    assertEquals(esperado, actual);

  }

  @Test
  void test_transferir_dinero_cuentas() {
    Cuenta cuentaUno = new Cuenta("John Doe", new BigDecimal("2500"));
    Cuenta cuentaDos = new Cuenta("Andres", new BigDecimal("1500.8989"));

    Banco banco = new Banco();
    banco.setNombre("Santander");
    banco.transferir(cuentaDos, cuentaUno, new BigDecimal("500"));

    assertEquals("1000.8989", cuentaDos.getSaldo().toPlainString());
    assertEquals("3000", cuentaUno.getSaldo().toPlainString());

  }

  @Test
  void test_relacion_banco_cuenta() {

    Cuenta cuentaUno = new Cuenta("John Doe", new BigDecimal("2500"));
    Cuenta cuentaDos = new Cuenta("Andres", new BigDecimal("1500.8989"));

    Banco banco = new Banco();
    banco.setNombre("Santander");

    banco.addCuenta(cuentaUno);
    banco.addCuenta(cuentaDos);

    banco.transferir(cuentaDos, cuentaUno, new BigDecimal("500"));

    assertAll(() -> assertEquals(2, banco.getCuentaList().size()),
        () -> assertEquals(2, banco.getCuentaList().size()),
        () -> assertEquals("Santander", cuentaUno.getBanco().getNombre()),
        () -> assertEquals("Andres", banco.getCuentaList()
            .stream()
            .filter(c -> c.getPersona().equals("Andres"))
            .findFirst()
            .get()
            .getPersona()),
        () -> assertTrue(banco.getCuentaList()
            .stream()
            .filter(c -> c.getPersona().equals("Andres"))
            .findFirst()
            .isPresent()),
        () -> assertTrue(banco.getCuentaList()
            .stream()
            .anyMatch(c -> c.getPersona().equals("Andres"))));
  }

}