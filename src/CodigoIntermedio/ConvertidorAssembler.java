package CodigoIntermedio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import org.omg.CORBA.portable.InputStream;

import AnalizadorLexico.TablaSimbolos;

public class ConvertidorAssembler {

    public static final String labelDivCero = "DividirCero";
    public static final String labelOverflowSuma = "OverflowSuma";
    public static final String labelOverflowMult = "OverflowMultiplicacion";

    static ControladorTercetos controladorTercetos;
    static TablaSimbolos tablaSimb;
    static File arch;
    private java.io.InputStream is;
    private java.io.InputStream is2;

    public ConvertidorAssembler( ControladorTercetos controladorTercetos ) throws IOException {
        this.controladorTercetos = controladorTercetos;
        tablaSimb = null;
    }

    public void setTablaSimb(TablaSimbolos tablaSimb) {
        this.tablaSimb = tablaSimb;
        controladorTercetos.setTablaSimbolos(tablaSimb);
    }

    public void generarAssembler () throws IOException{
        //controladorTercetos.generarAssembler();
        arch = new File("salida.asm");
        writeFile1();

//		PrintWriter p = new PrintWriter(new FileWriter(arch));
        //Imprimir codigo assembler

        String comc = "cmd /c .\\masm32\\bin\\ml /c /Zd /coff salida.asm";
        Process ptasm32 = Runtime.getRuntime().exec(comc);
        is = ptasm32.getInputStream();

        String coml = "cmd /c \\masm32\\bin\\Link /SUBSYSTEM:CONSOLE salida.obj ";
        Process ptlink32 = Runtime.getRuntime().exec(coml);
        is2 = ptlink32.getInputStream();
    }

    public String generarArchivo(){
        return controladorTercetos.generarAssembler();
    }

    public static void writeFile1() throws IOException {
        FileOutputStream fos = new FileOutputStream(arch);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


        bw.write(".386" + '\n'
                + ".model flat, stdcall" + '\n'
                + "option casemap :none" + '\n'
                + "include \\masm32\\include\\windows.inc" + '\n'
                + "include \\masm32\\include\\kernel32.inc" + '\n'
                + "include \\masm32\\include\\user32.inc" + '\n'
                + "includelib \\masm32\\lib\\kernel32.lib" + '\n'
                + "includelib \\masm32\\lib\\user32.lib" + '\n'
                + '\n' +".data" + '\n');
        String data = tablaSimb.getAssembler() ;
        data = data + controladorTercetos.getPrintsAssembler();
        data = data + labelDivCero + " db \"Error al dividir por cero!\", 0" + '\n';
        data = data + labelOverflowSuma + " db \"La suma ha generado un Overflow!\", 0" + '\n';
        data = data + labelOverflowMult + " db \"La multiplicacion ha generado un Overflow!\", 0" + '\n';
//		data = data + controladorTercetos.getVarAux();
        data = data + '\n' + ".code"+ "\n";

        bw.write( data );

        //Inicia el codigo
        String code = "start:" + '\n' + (String) controladorTercetos.generarAssembler();

        code = code + "invoke ExitProcess, 0" + '\n';

        bw.write( code );
        String errores = getErroresRunTime();
        bw.write(errores);
        bw.write( "end start" );

        bw.close();
    }

    private static String getErroresRunTime() {
        String errores = "Label"+labelDivCero + ":" + '\n';
        errores = errores + "invoke MessageBox, NULL, addr "+labelDivCero+", addr "+labelDivCero+", MB_OK" + '\n';
        errores = errores + "invoke ExitProcess, 0" + '\n';
        errores = errores + "Label"+labelOverflowSuma + ":" + '\n';
        errores = errores + "invoke MessageBox, NULL, addr "+labelOverflowSuma+", addr "+labelOverflowSuma+", MB_OK" + '\n';
        errores = errores + "invoke ExitProcess, 0" + '\n';
        errores = errores + "Label"+labelOverflowMult + ":" + '\n';
        errores = errores + "invoke MessageBox, NULL, addr "+labelOverflowMult+", addr "+labelOverflowMult+", MB_OK" + '\n';
        errores = errores + "invoke ExitProcess, 0" + '\n';
        return errores;
    }

}