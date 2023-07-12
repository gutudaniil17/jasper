package org.example.report;

import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalImageAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import org.example.constants.FilePaths;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class Report {
    private ReportJRXML reportJRXML;

    StyleBuilder columnTitleStyle = stl.style()
            .setBorder(stl.pen1Point())
            .setItalic(true)
            .setFontSize(13);
    StyleBuilder headerStyle = stl.style()
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
            .setFontSize(13);
    StyleBuilder titleStyle = stl.style()
            .setHorizontalImageAlignment(HorizontalImageAlignment.RIGHT)
            .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
            .setFontSize(15)
            .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
            .setVerticalImageAlignment(VerticalImageAlignment.MIDDLE);

    private void build() {
        try {
            report()
                    .title(cmp.text("Holidays").setStyle(titleStyle),
                            cmp.image(FilePaths.CEADCRI_PNG)
                                    .setStyle(titleStyle)
                                    .setHeight(60)
                                    .setWidth(80))
                    .pageHeader(cmp.text("All the holidays of 2021").setStyle(headerStyle))
                    .columns(
                            col.column("country", "country", type.stringType()),
                            col.column("date", "date", type.stringType()),
                            col.column("name", "name", type.stringType()))
                    .setColumnStyle(columnTitleStyle)
                    .pageFooter((cmp.pageXofY()))
                    .setDataSource(createDataSourceFromJRBean())
                    .show();
        } catch (DRException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Report() {
        build();
    }
    private JRDataSource createDataSourceFromJRBean() throws Exception {
        reportJRXML = new ReportJRXMLImpl();
        return reportJRXML.createDataSourceFromJRResultSet();
    }

    public static void main(String[] args) {
        new Report();
    }
}
