package Services;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.*;

public class WriterToPdf {

    /**
     * @param srcFile исходный файл
     * @param image   QR код в формате Image
     * @throws IOException
     * @throws DocumentException
     */
    public void writeToPdf(String srcFile, Image image) throws IOException, DocumentException {
        File destFile = new File("src/main/resources", "ResultPDF.pdf");//результирующий
        OutputStream fos = new FileOutputStream(destFile);

        PdfReader pdfReader = new PdfReader(srcFile);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
        // Получить размеры первой страницы
        Rectangle pageSize = pdfReader.getPageSizeWithRotation(1);
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();

        float absoluteX = image.getWidth();//width - image.getWidth() - 38;
        float absoluteY = height - image.getHeight() - 38;
        //Задать позицию картинки на странице
        image.setAbsolutePosition(absoluteX, absoluteY);
        // цикл для всех страниц в файле
        for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
            PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
            if (i == 1) // добавление картинки только на 1ю страницу
                pdfContentByte.addImage(image);
        }
        pdfStamper.close();
        fos.close();
        System.out.println("Результат: " + destFile.getAbsolutePath());
    }
}
