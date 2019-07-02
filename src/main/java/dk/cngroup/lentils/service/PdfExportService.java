package dk.cngroup.lentils.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dk.cngroup.lentils.entity.view.TeamScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfExportService {
    private static final int NUMBER_OF_TABLE_COLUMNS = 3;
    private static final int PARAGRAPH_FONT_SIZE = 35;
    private static final int SPACING_AFTER_PARAGRAPH = 20;

    private final ScoreService scoreService;

    @Autowired
    public PdfExportService(final ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    public ByteArrayInputStream exportScoresToPdf() throws DocumentException{
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            addHeaderParagraphToDocument(document);
            addTableToDocument(document);
            document.close();
        } catch (DocumentException e) {
            throw e;
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeaderParagraphToDocument(final Document document) throws DocumentException{
            document.add(getHeaderParagraph());
    }

    private Paragraph getHeaderParagraph() {
        Font font = FontFactory.getFont(FontFactory.COURIER, PARAGRAPH_FONT_SIZE, BaseColor.BLACK);
        Paragraph p = new Paragraph("Výsledné skóre", font);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(SPACING_AFTER_PARAGRAPH);
        return p;
    }

    private void addTableToDocument(final Document document) throws DocumentException{
        PdfPTable table = new PdfPTable(NUMBER_OF_TABLE_COLUMNS);
        addTableHeader(table);
        addRows(table);
        document.add(table);
    }

    private void addTableHeader(final PdfPTable table) {
        Stream.of("Poradí", "Název týmu", "Body")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void addRows(final PdfPTable table) {
        List<TeamScore> teamsWithScores = scoreService.getAllTeamsWithScores();
        teamsWithScores.stream()
                .forEach(teamScore -> {
                    table.addCell(teamScore.getRank().toString());
                    table.addCell(teamScore.getTeam().getName());
                    table.addCell(String.valueOf(teamScore.getScore()));
                });
    }
}
