package com.olc1.golite.visitor.interpreter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.olc1.golite.ast.exp.Expresion;
import com.olc1.golite.ast.exp.FuncionEmbebida;
import com.olc1.golite.ast.exp.Identificador;
import com.olc1.golite.ast.exp.Literal;
import com.olc1.golite.ast.exp.LlamadaFuncion;
import com.olc1.golite.ast.exp.OperacionBinaria;
import com.olc1.golite.ast.exp.OperacionUnaria;
import com.olc1.golite.ast.stm.Asignacion;
import com.olc1.golite.ast.stm.Bloque;
import com.olc1.golite.ast.stm.Break;
import com.olc1.golite.ast.stm.Continue;
import com.olc1.golite.ast.stm.Declaracion;
import com.olc1.golite.ast.stm.Decremento;
import com.olc1.golite.ast.stm.For;
import com.olc1.golite.ast.stm.Funcion;
import com.olc1.golite.ast.stm.If;
import com.olc1.golite.ast.stm.Incremento;
import com.olc1.golite.ast.stm.Instruccion;
import com.olc1.golite.ast.stm.LlamadaFuncionStmt;
import com.olc1.golite.ast.stm.Print;
import com.olc1.golite.ast.stm.Return;
import com.olc1.golite.ui.ConsolePanel;
import com.olc1.golite.visitor.interpreter.environment.Entorno;
import com.olc1.golite.visitor.interpreter.value.ValueWrapper;

public class InterpreterVisitor {

    private Entorno entorno;
    private ConsolePanel consola;
    private Map<String, Funcion> funciones;

    public InterpreterVisitor(ConsolePanel consola) {
        this.consola = consola;
        this.entorno = new Entorno();
        this.funciones = new HashMap<>();
    }

    public void ejecutar(List<Instruccion> instrucciones) {
        for (Instruccion ins : instrucciones) {
            if (ins instanceof Funcion f) {
                if (funciones.containsKey(
                        f.getNombre())) {
    
                    throw new RuntimeException(
                        "La funcion ya existe: "+ f.getNombre());
                }
                funciones.put(f.getNombre(), f);
            }
        }
    
        for (Instruccion ins : instrucciones) {
            if (!(ins instanceof Funcion)) {
                ejecutar(ins);
            }
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
            return;
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

        if (ins instanceof Funcion f) {
            return;
        }

        if (ins instanceof Return r) {
            ejecutarReturn(r);
            return;
        }

        if (ins instanceof LlamadaFuncionStmt l) {
            ejecutarFuncion( l.getLlamada());
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
        
        System.out.println(
                "ASIGNANDO -> " +
                a.getIdentificador() +
                " = " +
                a.getValor().getClass().getSimpleName()
            );

        Object valor = evaluar(a.getValor());
        System.out.println(
            "VALOR CALCULADO -> " + valor
        );

        entorno.asignar(a.getIdentificador(),
            new ValueWrapper(valor));
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
        System.out.println("BREAK EJECUTADO");
        throw new BreakException();
    }
    
    private void ejecutarContinue(Continue c) {
        System.out.println("CONTINUE EJECUTADO");
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

    private Object ejecutarFuncion( LlamadaFuncion llamada) {

        Funcion funcion = funciones.get(llamada.getNombre());

        if (funcion == null) {

            throw new RuntimeException(
                "Funcion no definida: "
                + llamada.getNombre());
        }

        Entorno anterior = entorno;
        entorno = new Entorno(anterior);

        try {
            try {
                ejecutar(
                    funcion.getBloque());
            } catch (ReturnException ex) {
                return ex.getValor();
            }
            return null;
        } finally {
            entorno = anterior;
        }
    }

    private void ejecutarReturn(Return r) {
        Object valor = null;

        if (r.getValor() != null) {
            valor = evaluar(r.getValor());
        }
        throw new ReturnException(valor);
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

        if (exp instanceof LlamadaFuncion lf) {
            return ejecutarFuncion(lf);
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

                if (izq instanceof Number && der instanceof Number) {
                    return ((Number) izq).doubleValue()
                            == ((Number) der).doubleValue();
                }

                return java.util.Objects.equals(izq, der);

            case "!=":

                if (izq instanceof Number && der instanceof Number) {
                    return ((Number) izq).doubleValue()
                            != ((Number) der).doubleValue();
                }

                return !java.util.Objects.equals(izq, der);

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
            System.out.println(
                "FUNCION -> " + f.getNombre()
            );    
        Object valor =
                evaluar(
                    f.getArgumento()
                );

        switch (f.getNombre().toLowerCase()) {

            case "atoi":
               return Integer.parseInt(
                        valor.toString());
                
            case "parsefloat":
                return Double.parseDouble(
                        valor.toString());
                
            case "typeof":
                if (valor == null) {
                    return "nil";
                }
                
                return valor.getClass().getSimpleName();
        }

        throw new RuntimeException(
            "Funcion embebida no soportada"
        );
    }
}