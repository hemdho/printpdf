package pdf;

import java.awt.print.PrinterJob;

import javax.print.PrintService;

public class PrinterLookup {
    public static void main(String[] args){
        PrintService[] printService = PrinterJob.lookupPrintServices();
        boolean printerFound = false;
        for (int i = 0;   i < printService.length; i++) {
           System.out.println(printService[i].getName());
        }
    }
}
