package pdf;

import java.io.File;

public class DocPrint {
	public static void main(String[] args) throws Exception {

		File file = new File("C:\\Users\\Admin\\Desktop\\R");
		for (File file1 : file.listFiles()) {
			for (File file2 : file1.listFiles()) {
				//for (File file_ : file2.listFiles()) {
					try {
						PrintPDF pdf = new PrintPDF();
						pdf.print(new String[] { "-printerName \"HP LaserJet P4515 PCL6 Class Driver\" "  , file2.getPath() } );
					} catch (Exception e) {
						e.printStackTrace();
					}
				//}
			}
		}
	}
}
// SHARP MX-2610N - WSD