package com.olc1.golite.visitor.interpreter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.olc1.golite.ast.exp.AccesoSlice;
import com.olc1.golite.ast.exp.Append;
import com.olc1.golite.ast.exp.Expresion;
import com.olc1.golite.ast.exp.FuncionEmbebida;
import com.olc1.golite.ast.exp.Identificador;
import com.olc1.golite.ast.exp.Len;
import com.olc1.golite.ast.exp.Literal;
import com.olc1.golite.ast.exp.LlamadaFuncion;
import com.olc1.golite.ast.exp.OperacionBinaria;
import com.olc1.golite.ast.exp.OperacionUnaria;
import com.olc1.golite.ast.exp.SliceLiteral;
import com.olc1.golite.ast.stm.Asignacion;
import com.olc1.golite.ast.stm.Bloque;
import com.olc1.golite.ast.stm.Break;
import com.olc1.golite.ast.stm.CaseSwitch;
import com.olc1.golite.ast.stm.Continue;
import com.olc1.golite.ast.stm.Declaracion;
import com.olc1.golite.ast.stm.Decremento;
import com.olc1.golite.ast.stm.For;
import com.olc1.golite.ast.stm.Funcion;
import com.olc1.golite.ast.stm.If;
import com.olc1.golite.ast.stm.Incremento;
import com.olc1.golite.ast.stm.Instruccion;
import com.olc1.golite.ast.stm.LlamadaFuncionStmt;
import com.olc1.golite.ast.stm.Parametro;
import com.olc1.golite.ast.stm.Print;
import com.olc1.golite.ast.stm.Return;
import com.olc1.golite.ast.stm.Switch;
import com.olc1.golite.ui.ConsolePanel;
import com.olc1.golite.visitor.interpreter.environment.Entorno;
import com.olc1.golite.visitor.interpreter.value.ValueWrapper;

public class InterpreterVisitor {

    private Entorno entorno;
    private ConsolePanel consola;
    private Map<String, Funcion> funciones;
    private Set<String> variablesGlobales = new HashSet<>();

    public InterpreterVisitor(ConsolePanel consola) {
        this.consola = consola;
        this.entorno = new Entorno();
        this.funciones = new HashMap<>();
    }

    public void ejecutar(List<Instruccion> instrucciones) {
        variablesGlobales.clear();

        for (Instruccion ins : instrucciones) {
            if (ins instanceof Declaracion d) {
                variablesGlobales.add(d.getIdentificador());
            }
        }

        for (Instruccion ins : instrucciones) {
            if (!(ins instanceof Funcion)) {
                validarFuncionesGlobales(ins);
            }
        }

        for (Instruccion ins : instrucciones) {
            if (ins instanceof Funcion f) {
                validarFuncionesDentroDeFuncion(f.getBloque());

                if (funciones.containsKey( f.getNombre())) {
                    throw new RuntimeException("La funcion ya existe: "+ f.getNombre());
                }

                if (variablesGlobales.contains(f.getNombre())) {
                    throw new RuntimeException( "Ya existe una variable llamada "
                        + f.getNombre());
                }

                HashSet<String> nombres = new HashSet<>();

                for (Parametro p : f.getParametros()) {
                    if (!nombres.add( p.getNombre())) {
                        throw new RuntimeException("Parametro repetido: "
                            + p.getNombre() + " en funcion " + f.getNombre());
                    }
                }
                funciones.put(f.getNombre(), f);
            }
        }

        Funcion main = funciones.get("main");

        if (main == null) {
            throw new RuntimeException("No existe la funcion main");
        }

        if (!main.getParametros().isEmpty()) {
            throw new RuntimeException( "La funcion main no puede recibir parametros");
        }

        if (main.getTipoRetorno() != null) {
            throw new RuntimeException(
                "La funcion main no puede retornar valores");
        }

        ejecutarFuncion( new LlamadaFuncion("main", new ArrayList<>()));
    }

    private void validarFuncionesGlobales(Instruccion ins) {
        if (ins instanceof Funcion) {
            throw new RuntimeException("Las funciones solo pueden declararse en el ambito global");
        }

        if (ins instanceof Bloque b) {
            for (Instruccion i : b.getInstrucciones()) {
                validarFuncionesGlobales(i);
            }
        }

        if (ins instanceof If i) {
            validarFuncionesGlobales(i.getBloqueThen());

            if (i.getBloqueElse() != null) {
                validarFuncionesGlobales(i.getBloqueElse());
            }
        }

        if (ins instanceof For f) {
            validarFuncionesGlobales(f.getBloque());
        }
    }

    private void validarFuncionesDentroDeFuncion( Bloque bloque) {
        for (Instruccion ins : bloque.getInstrucciones()) {
            if (ins instanceof Funcion) {
                throw new RuntimeException("Las funciones solo pueden declararse en el ambito global");
            }

            if (ins instanceof Bloque b) {
                validarFuncionesDentroDeFuncion(b);
            }

            if (ins instanceof If i) {
                if (i.getBloqueThen() instanceof Bloque bt) {
                    validarFuncionesDentroDeFuncion(bt);
                }

                if (i.getBloqueElse() instanceof Bloque be) {
                    validarFuncionesDentroDeFuncion(be);
                }
            }

            if (ins instanceof For f) {
                validarFuncionesDentroDeFuncion(f.getBloque());
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

        if (ins instanceof Switch s) {
            ejecutarSwitch(s);
            return;
        }
    }

    private void ejecutarDeclaracion(Declaracion d) {
        Object valor = null;

        if (d.getValor() != null) {
            valor = evaluar(d.getValor());
        }

        if (funciones.containsKey(d.getIdentificador())) {
            throw new RuntimeException("Ya existe una funcion llamada "
                + d.getIdentificador());
        }

        entorno.declarar(d.getIdentificador(), new ValueWrapper(valor));
    }

    private void ejecutarAsignacion( Asignacion a) {
        System.out.println( "ASIGNANDO -> " + a.getIdentificador() +
                " = " + a.getValor().getClass().getSimpleName());

        Object valor = evaluar(a.getValor());
        System.out.println( "VALOR CALCULADO -> " + valor);

        entorno.asignar(a.getIdentificador(),new ValueWrapper(valor));
    }

    private void ejecutarPrint( Print p) {
        StringBuilder salida = new StringBuilder();

        for (Expresion e : p.getExpresiones()) {
            Object valor = evaluar(e);

            salida.append(valor);
            salida.append(" ");
        }

        consola.append(salida.toString());
    }

    private void ejecutarIf(If i) {
        Object condicion = evaluar(i.getCondicion());
    
        if (!(condicion instanceof Boolean)) {
            throw new RuntimeException("La condicion del if debe ser booleana");
        }
    
        if ((Boolean) condicion) {
            ejecutar(i.getBloqueThen());
    
        } else {
            if (i.getBloqueElse() != null) {
                ejecutar(i.getBloqueElse());
            }
        }
    }

    private void ejecutarBloque(Bloque b) {
        Entorno anterior = entorno;
        entorno = new Entorno(anterior);
    
        try {
            for (Instruccion ins : b.getInstrucciones()) {
                ejecutar(ins);
            }
    
        } finally {
            entorno = anterior;
        }
    }

    private void ejecutarIncremento( Incremento i) {

        ValueWrapper valor = entorno.obtener(i.getIdentificador());

        if (valor == null) {
            throw new RuntimeException("Variable no definida: " + i.getIdentificador());
        }

        Number numero = (Number) valor.getValor();

        entorno.asignar(i.getIdentificador(), new ValueWrapper(numero.doubleValue() + 1));
    }

    private void ejecutarDecremento(Decremento d) {

        ValueWrapper valor = entorno.obtener( d.getIdentificador());

        if (valor == null) {
            throw new RuntimeException("Variable no definida: " + d.getIdentificador());
        }

        Number numero = (Number) valor.getValor();

        entorno.asignar(d.getIdentificador(), new ValueWrapper(numero.doubleValue() - 1 ));
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
                    Object condicion =  evaluar(f.getCondicion());
    
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

    private Object ejecutarFuncion(LlamadaFuncion llamada) {
        Funcion funcion = funciones.get(llamada.getNombre());

        if (funcion == null) {
            throw new RuntimeException("Funcion no definida: "
                + llamada.getNombre());
        }

        if (llamada.getArgumentos().size() != funcion.getParametros().size()) {
            throw new RuntimeException("Cantidad incorrecta de parametros en "
                + llamada.getNombre());
        }

        Entorno anterior = entorno;
        entorno = new Entorno(anterior);

        try {
            for (int i = 0;  i < funcion.getParametros().size(); i++) {
                Parametro parametro = funcion.getParametros().get(i);
            
                Object valor = evaluar(llamada.getArgumentos().get(i));
            
                if (!tipoCompatible(parametro.getTipo(), valor)) {
                    throw new RuntimeException( "Tipo incorrecto para parametro "
                        + parametro.getNombre());
                }
            
                entorno.declarar(parametro.getNombre(), new ValueWrapper(valor));
            }

            try {
                ejecutar(funcion.getBloque());
            } catch (ReturnException ex) {
                if (!tipoCompatible( funcion.getTipoRetorno(), ex.getValor())) {
                        throw new RuntimeException( "Tipo de retorno incorrecto en "
                            + funcion.getNombre());
                    }
                    return ex.getValor();
               }
               if (funcion.getTipoRetorno() != null) {
                throw new RuntimeException( "La funcion "
                    + funcion.getNombre() + " debe retornar un valor");
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

            ValueWrapper valor = entorno.obtener(id.getNombre());

            if (valor == null) {
                throw new RuntimeException("Variable no definida: " + id.getNombre());
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

        if (exp instanceof SliceLiteral s) {
            return evaluarSliceLiteral(s);
        }

        if (exp instanceof AccesoSlice a) {
            return evaluarAccesoSlice(a);
        }

        if (exp instanceof Len l) {
            return evaluarLen(l);
        }

        if (exp instanceof Append a) {
            return evaluarAppend(a);
        }

        return null;
    }

    private Object evaluarOperacionBinaria(
        OperacionBinaria op) {

        Object izq = evaluar(op.getIzquierda());

        Object der = evaluar(op.getDerecha());

        String operador = op.getOperador();

        switch (operador) {
            case "+":
                if (izq instanceof String || der instanceof String) {
                    return String.valueOf(izq) + String.valueOf(der);
                }

                if (izq instanceof Double || der instanceof Double) {
                    return ((Number) izq).doubleValue() + ((Number) der).doubleValue();
                }

                return ((Number) izq).intValue() + ((Number) der).intValue();

            case "-":
                if (izq instanceof Double || der instanceof Double) {
                    return ((Number) izq).doubleValue() - ((Number) der).doubleValue();
                }
                return ((Number) izq).intValue() - ((Number) der).intValue();

            case "*":
                if (izq instanceof Double || der instanceof Double) {
                    return ((Number) izq).doubleValue() * ((Number) der).doubleValue();
                }
                return ((Number) izq).intValue() * ((Number) der).intValue();

            case "/":
                return ((Number) izq).doubleValue()
                        / ((Number) der).doubleValue();

            case "%":
                return ((Number) izq).intValue()%((Number) der).intValue();

            case ">":
                return ((Number) izq).doubleValue()>((Number) der).doubleValue();

            case "<":
                return ((Number) izq).doubleValue()<((Number) der).doubleValue();

            case ">=":

                return ((Number) izq).doubleValue()>=((Number) der).doubleValue();

            case "<=":

                return ((Number) izq).doubleValue()<= ((Number) der).doubleValue();

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
                return ((Boolean) izq) && ((Boolean) der);

            case "||":
                return ((Boolean) izq) || ((Boolean) der);
        }

        throw new RuntimeException("Operador no soportado: " + operador );
    }

    private Object evaluarOperacionUnaria( OperacionUnaria op) {
        Object valor = evaluar(op.getExpresion());

        switch (op.getOperador()) {
            case "!":
                return !((Boolean) valor);

            case "-":
                return -((Number) valor)
                        .doubleValue();
        }

        throw new RuntimeException( "Operador unario no soportado" );
    }

    private Object evaluarFuncionEmbebida(
        FuncionEmbebida f) {
            System.out.println( "FUNCION -> " + f.getNombre());    
        Object valor = evaluar(f.getArgumento());

        switch (f.getNombre().toLowerCase()) {
            case "atoi":
               return Integer.parseInt(valor.toString());
                
            case "parsefloat":
                return Double.parseDouble( valor.toString());
                
            case "typeof":
                if (valor == null) {
                    return "nil";
                }
                return valor.getClass().getSimpleName();
        }
        throw new RuntimeException("Funcion embebida no soportada");
    }

    private boolean tipoCompatible(String tipo, Object valor) {
        if (tipo == null) {
            return valor == null;
        }
        
        switch (tipo) {
            case "int":
                return valor instanceof Integer;

            case "float64":
                return valor instanceof Double;

            case "string":
                return valor instanceof String;

            case "bool":
                return valor instanceof Boolean;

            case "rune":
                return valor instanceof Character;
        }
        return true;
    }

    private void ejecutarSwitch(Switch s) {
        Object valorSwitch = evaluar(s.getExpresion());

        for (CaseSwitch caso : s.getCasos()) {
            Object valorCase = evaluar(caso.getValor());

            if (java.util.Objects.equals(valorSwitch, valorCase)) {
                ejecutar(caso.getBloque());
                return;
            }
        }

        if (s.getBloqueDefault() != null) {
            ejecutar(s.getBloqueDefault());
        }
    }

    private Object evaluarSliceLiteral(SliceLiteral slice) {
        ArrayList<Object> valores = new ArrayList<>();

        for (Expresion e : slice.getElementos()) {
            valores.add( evaluar(e));
        }
        return valores;
    }

    private Object evaluarAccesoSlice(AccesoSlice acceso) {
        ValueWrapper wrapper = entorno.obtener(acceso.getIdentificador());

        if (wrapper == null) {
            throw new RuntimeException("Variable no definida: "  + acceso.getIdentificador());
        }

        Object valor = wrapper.getValor();

        if (!(valor instanceof List<?> lista)) {
            throw new RuntimeException( acceso.getIdentificador()  + " no es un slice");
        }

        Object indiceObj = evaluar(acceso.getIndice());

        int indice = ((Number) indiceObj).intValue();

        if (indice < 0 || indice >= lista.size()) {
            throw new RuntimeException("Indice fuera de rango");
        }
        return lista.get(indice);
    }

    private Object evaluarLen( Len len) {
        Object valor = evaluar(len.getExpresion());

        if (valor instanceof List<?> lista) {
            return lista.size();
        }

        if (valor instanceof String s) {
            return s.length();
        }
        throw new RuntimeException("len solo puede aplicarse a slices o strings");
    }

    private Object evaluarAppend( Append append) {
        Object sliceObj = evaluar( append.getSlice());

        if (!(sliceObj instanceof List<?>)) {
            throw new RuntimeException(
                "append requiere un slice");
        }

        @SuppressWarnings("unchecked")
        List<Object> lista = (List<Object>) sliceObj;

        lista.add( evaluar(append.getValor()));
        return lista;
    }
}
