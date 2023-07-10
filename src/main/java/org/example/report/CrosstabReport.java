package org.example.report;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabMeasureBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.model.Holiday;
import org.example.service.Parser;

import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class CrosstabReport {
    public CrosstabReport() {
        build();
    }

    private void build() {
        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("Holiday", String.class)
                .setTotalHeader("Total for state");

        CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("name", String.class);

        CrosstabBuilder crosstab = ctab.crosstab()
                .headerCell(cmp.text("State / Item"))
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(
                        ctab.measure("Total", "name", String.class, Calculation.SUM));

        try {
            report()
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                    .title(cmp.text("Crosstab"))
                    .summary(crosstab)
                    /*.pageFooter(Templates.footerComponent)*/
                    .setDataSource(createDataSource())
                    .show();
        } catch (DRException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JRDataSource createDataSource() throws Exception {
        List<Holiday> list = Parser.parse();
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(list);
        return collectionDataSource;
    }

    public static void main(String[] args) {
        JasperReportBuilder report = DynamicReports.report();

        // Define the data source
        JRDataSource dataSource = null;
        try {
            dataSource = createDataSource();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Define the columns for the crosstab
        TextColumnBuilder<String> countryColumn = DynamicReports.col.column("Country", "country", DynamicReports.type.stringType());
        TextColumnBuilder<String> monthColumn = DynamicReports.col.column("Date", "date", DynamicReports.type.stringType());

        // Define the crosstab elements
        CrosstabRowGroupBuilder<String> rowGroup = DynamicReports.ctab.rowGroup(countryColumn);
        CrosstabColumnGroupBuilder<String> columnGroup = DynamicReports.ctab.columnGroup(monthColumn);
        CrosstabMeasureBuilder measure = DynamicReports.ctab.measure("Count", "name", DynamicReports.type.stringType().getClass(), Calculation.COUNT);

        // Build the crosstab
        CrosstabBuilder crosstab = DynamicReports.ctab.crosstab()
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(measure);

        try {
            report.setDataSource(dataSource)
                    .summary(crosstab)
                    .show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }
}

