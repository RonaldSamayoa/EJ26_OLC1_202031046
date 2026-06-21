package com.olc1.golite.visitor.interpreter;
import java.util.List;

import com.olc1.golite.ast.exp.Expresion;
import com.olc1.golite.ast.exp.FuncionEmbebida;
import com.olc1.golite.ast.exp.Identificador;
import com.olc1.golite.ast.exp.Literal;
import com.olc1.golite.ast.exp.OperacionBinaria;
import com.olc1.golite.ast.exp.OperacionUnaria;
import com.olc1.golite.ast.stm.Asignacion;
import com.olc1.golite.ast.stm.Bloque;
import com.olc1.golite.ast.stm.Break;
import com.olc1.golite.ast.stm.Continue;
import com.olc1.golite.ast.stm.Declaracion;
import com.olc1.golite.ast.stm.Decremento;
import com.olc1.golite.ast.stm.For;
import com.olc1.golite.ast.stm.If;
import com.olc1.golite.ast.stm.Incremento;
import com.olc1.golite.ast.stm.Instruccion;
import com.olc1.golite.ast.stm.Print;
import com.olc1.golite.ui.ConsolePanel;
import com.olc1.golite.visitor.interpreter.environment.Entorno;
import com.olc1.golite.visitor.interpreter.value.ValueWrapper;

public class InterpreterVisitor {

    private Entorno entorno;
    private ConsolePanel consola;

    public InterpreterVisitor(ConsolePanel consola) {
        this.consola = consola;
        this.entorno = new Entorno();
    }

    public void ejecutar(List<Instruccion> instrucciones) {
        for (Instruccion ins : instrucciones) {
            ejecutar(ins);
        }
    }

    private void ejecutar(Instruccion ins) {

        if (ins instanceof Declaracion d) {
            ejecutarDeclaracion(d);
            return;
        }

        if (ins instanceof Asignacion a) {
            ejecutarAsignacion(a);
            return;
        }

        if (ins instanceof Print p) {
            ejecutarPrint(p);
            return;
        }

        if (ins instanceof Bloque b) {
            ejecutarBloque(b);
            return;
        }

        if (ins instanceof If i) {
            ejecutarIf(i);
            return;
        }

        if (ins instanceof Incremento i) {
            ejecutarIncremento(i);
            return;
        }

        if (ins instanceof Decremento d) {
            ejecutarDecremento(d);
        }

        if (ins instanceof For f) {
            ejecutarFor(f);
            return;
        }

        if (ins instanceof Break b) {
            ejecutarBreak(b);
            return;
        }

        if (ins instanceof Continue c) {
            ejecutarContinue(c);
            return;
        }
    }

    private void ejecutarDeclaracion(
        Declaracion d) {

        Object valor = null;

        if (d.getValor() != null) {
            valor = evaluar(d.getValor());
        }

        entorno.declarar(
            d.getIdentificador(),
            new ValueWrapper(valor)
        );
    }

    private void ejecutarAsignacion(
        Asignacion a) {

        Object valor = evaluar(
            a.getValor()
        );

        entorno.asignar(
            a.getIdentificador(),
            new ValueWrapper(valor)
        );
    }

    private void ejecutarPrint(
        Print p) {

        StringBuilder salida =
                new StringBuilder();

        for (Expresion e :
                p.getExpresiones()) {

            Object valor =
                    evaluar(e);

            salida.append(valor);
            salida.append(" ");
        }

        consola.append(
            salida.toString()
        );
    }

    private void ejecutarIf(If i) {

        Object condicion =
                evaluar(i.getCondicion());
    
        if (!(condicion instanceof Boolean)) {
            throw new RuntimeException(
                "La condicion del if debe ser booleana"
            );
        }
    
        if ((Boolean) condicion) {
    
            ejecutar(
                i.getBloqueThen()
            );
    
        } else {
    
            if (i.getBloqueElse() != null) {
                ejecutar(
                    i.getBloqueElse()
                );
            }
        }
    }

    private void ejecutarBloque(Bloque b) {
        Entorno anterior = entorno;
        entorno = new Entorno(anterior);
    
        try {
    
            for (Instruccion ins :
                    b.getInstrucciones()) {
                ejecutar(ins);
            }
    
        } finally {
            entorno = anterior;
        }
    }

    private void ejecutarIncremento(
        Incremento i) {

        ValueWrapper valor =
                entorno.obtener(
                        i.getIdentificador());

        if (valor == null) {

            throw new RuntimeException(
                "Variable no definida: "
                + i.getIdentificador()
            );
        }

        Number numero =
                (Number) valor.getValor();

        entorno.asignar(
            i.getIdentificador(),
            new ValueWrapper(
                numero.doubleValue() + 1
            )
        );
    }

    private void ejecutarDecremento(
        Decremento d) {

        ValueWrapper valor =
                entorno.obtener(
                        d.getIdentificador());

        if (valor == null) {

            throw new RuntimeException(
                "Variable no definida: "
                + d.getIdentificador()
            );
        }

        Number numero =
                (Number) valor.getValor();

        entorno.asignar(
            d.getIdentificador(),
            new ValueWrapper(
                numero.doubleValue() - 1
            )
        );
    }

    private void ejecutarBreak(Break b) {
        throw new BreakException();
    }
    
    private void ejecutarContinue(Continue c) {
        throw new ContinueException();
    }

    private void ejecutarFor(For f) {

        Entorno anterior = entorno;
        entorno = new Entorno(anterior);
    
        try {
    
            if (f.getInicializacion() != null) {
                ejecutar(f.getInicializacion());
            }
    
            while (true) {
    
                if (f.getCondicion() != null) {
    
                    Object condicion =
                            evaluar(f.getCondicion());
    
                    if (!(condicion instanceof Boolean)) {
                        throw new RuntimeException(
                            "La condicion del for debe ser booleana"
                        );
                    }
    
                    if (!((Boolean) condicion)) {
                        break;
                    }
                }
    
                try {
    
                    ejecutar(f.getBloque());
    
                } catch (ContinueException ex) {
    
                    if (f.getActualizacion() != null) {
                        ejecutar(f.getActualizacion());
                    }
    
                    continue;
    
                } catch (BreakException ex) {
    
                    break;
                }
    
                if (f.getActualizacion() != null) {
                    ejecutar(f.getActualizacion());
                }
            }
    
        } finally {
    
            entorno = anterior;
        }
    }

    private Object evaluar(Expresion exp) {
        if (exp instanceof Literal l) {
            return l.getValor();
        }

        if (exp instanceof Identificador id) {

            ValueWrapper valor =
                    entorno.obtener(
                            id.getNombre());

            if (valor == null) {
                throw new RuntimeException(
                    "Variable no definida: "
                    + id.getNombre()
                );
            }

            return valor.getValor();
        }

        if (exp instanceof OperacionBinaria op) {
            return evaluarOperacionBinaria(op);
        }

        if (exp instanceof OperacionUnaria op) {
            return evaluarOperacionUnaria(op);
        }

        if (exp instanceof FuncionEmbebida f) {
            return evaluarFuncionEmbebida(f);
        }

        return null;
    }

    private Object evaluarOperacionBinaria(
        OperacionBinaria op) {

        Object izq =
                evaluar(op.getIzquierda());

        Object der =
                evaluar(op.getDerecha());

        String operador =
                op.getOperador();

        switch (operador) {

            case "+":

                if (izq instanceof String
                        || der instanceof String) {

                    return String.valueOf(izq)
                            + String.valueOf(der);
                }

                return ((Number) izq).doubleValue()
                        + ((Number) der).doubleValue();

            case "-":

                return ((Number) izq).doubleValue()
                        - ((Number) der).doubleValue();

            case "*":

                return ((Number) izq).doubleValue()
                        * ((Number) der).doubleValue();

            case "/":

                return ((Number) izq).doubleValue()
                        / ((Number) der).doubleValue();

            case "%":

                return ((Number) izq).intValue()
                        % ((Number) der).intValue();

            case ">":

                return ((Number) izq).doubleValue()
                        >
                        ((Number) der).doubleValue();

            case "<":

                return ((Number) izq).doubleValue()
                        <
                        ((Number) der).doubleValue();

            case ">=":

                return ((Number) izq).doubleValue()
                        >=
                        ((Number) der).doubleValue();

            case "<=":

                return ((Number) izq).doubleValue()
                        <=
                        ((Number) der).doubleValue();

            case "==":

                return izq.equals(der);

            case "!=":

                return !izq.equals(der);

            case "&&":

                return ((Boolean) izq)
                        &&
                        ((Boolean) der);

            case "||":

                return ((Boolean) izq)
                        ||
                        ((Boolean) der);
        }

        throw new RuntimeException(
            "Operador no soportado: "
            + operador
        );
    }

    private Object evaluarOperacionUnaria(
        OperacionUnaria op) {

        Object valor =
                evaluar(op.getExpresion());

        switch (op.getOperador()) {

            case "!":
                return !((Boolean) valor);

            case "-":
                return -((Number) valor)
                        .doubleValue();
        }

        throw new RuntimeException(
            "Operador unario no soportado"
        );
    }

    private Object evaluarFuncionEmbebida(
        FuncionEmbebida f) {

        Object valor =
                evaluar(
                    f.getArgumento()
                );

        switch (f.getNombre()) {

            case "atoi":
                return Integer.parseInt(
                        valor.toString());

            case "parseFloat":
                return Double.parseDouble(
                        valor.toString());

            case "typeof":

                if (valor == null) {
                    return "nil";
                }

                return valor.getClass()
                        .getSimpleName();
        }

        throw new RuntimeException(
            "Funcion embebida no soportada"
        );
    }
}