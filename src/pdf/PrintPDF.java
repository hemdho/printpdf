package pdf;


import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.attribute.PrintRequestAttributeSet;

import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * 26 * This is a command line program that will print a PDF document.
 * 27 *
 * 28 * @author <a href="ben@benlitchfield.com">Ben Litchfield</a>
 * 29 * @version $Revision: 1.4 $
 * 30
 */
public class PrintPDF {

    private static final String PASSWORD = "-password";
    private static final String SILENT = "-silentPrint";
    private static final String PRINTER_NAME = "-printerName";

    /**
     * 39 * private constructor.
     * 40
     */
    public PrintPDF() {
        // static class
    }

    /**
     * 47 * Infamous main method.
     * 48 *
     * 49 * @param args Command line arguments, should be one and a reference to a file.
     * 50 *
     * 51 * @throws Exception If there is an error parsing the document.
     * 52
     */
    public void print(String[] args) throws Exception {
        String password = "";
        String pdfFile = null;
        boolean silentPrint = true;
        String printerName = null;
        for (int i = 0; i < args.length; i++) {
            if /*
                * (args[i].equals(PASSWORD)) {
                * i++;
                * if (i >= args.length) {
                * usage();
                * }
                * password = args[i];
                * } else if
                */(args[i].equals(PRINTER_NAME)) {
                i++;
                if (i >= args.length) {
                    usage();
                }
                printerName = args[i];
            } /*
               * else if (args[i].equals(SILENT)) {
               * silentPrint = true;
               * }
               */else {
                pdfFile = args[i];
            }
        }

        if (pdfFile == null) {
            // usage();
            // pdfFile = args;
        }

        printerName = null;
        PDDocument document = null;
        try {
            document = PDDocument.load(pdfFile);

            if (document.isEncrypted()) {
                document.decrypt(password);
            }
            PrinterJob printJob = PrinterJob.getPrinterJob();
          /*  PageFormat pageFormat = printJob.defaultPage();
            Paper paper = pageFormat.getPaper();

            paper.setImageableArea(25, 25, 750, 570);

            pageFormat.setPaper(paper);
            printJob.defaultPage(pageFormat);
            System.out.println("height " + pageFormat.getHeight());
            System.out.println("width " +  pageFormat.getWidth());
            System.out.println( " imageable h " + pageFormat.getImageableHeight());
            System.out.println( " imageable w " + pageFormat.getImageableWidth());
            System.out.println( " imageable X " + pageFormat.getImageableX() );
            System.out.println( " imageable Y " + pageFormat.getImageableY());*/

            if (printerName != null) {
                PrintService[] printService = PrinterJob.lookupPrintServices();
                boolean printerFound = false;
                for (int i = 0; !printerFound && i < printService.length; i++) {
                    if (printService[i].getName().indexOf(printerName) != -1) {
                        printJob.setPrintService(printService[i]);
                        printerFound = true;
                    }
                }
            }

            if (silentPrint) {

               document.silentPrint(printJob);

            } else {
                document.print(printJob);
            }

        } finally {
            if (document != null) {
                document.close();

            }
        }
    }

    /**
     * This will print the usage requirements and exit.
     */
    private static void usage() {
        System.err.println("Usage: java org.apache.pdfbox.PrintPDF [OPTIONS] <PDF file>\n" + "  -password  <password>        Password to decrypt document\n"
                + "  -silentPrint                 Print without prompting for printer info\n");
        System.exit(1);

    }
}
