package org.example.report;

import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import org.example.model.Holiday;
import org.example.service.Parser;
import org.example.service.ParserImpl;

import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class Crosstab {
    private Parser parser;
    public Crosstab() {
        build();
    }
    StyleBuilder crosstabStyle = stl.style()
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
            .setBorder(stl.pen1Point())
            .setItalic(true);

    private void build() {
        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("country", String.class);
        CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("month", String.class);
        CrosstabBuilder crosstab = ctab.crosstab()
                .rowGroups(rowGroup)
                .columnGroups(columnGroup.setHeaderStyle(crosstabStyle))
                .measures(ctab.measure( "name", String.class, Calculation.COUNT).setStyle(crosstabStyle))
                .setCellWidth(29);
        try{
            report()
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                    .title(cmp.text("All the holidays"))
                    .summary(crosstab)
                    .setDataSource(createDataSource())
                    .show();
        }catch(Exception e)    {
            e.printStackTrace();
        }
    }
    private DRDataSource createDataSource() throws Exception {
        DRDataSource dataSource = new DRDataSource("country","month","name");
        parser = new ParserImpl();
        List<Holiday> holidays = parser.parseFromXML();
        for(Holiday holiday : holidays){
            String country = holiday.getCountry();
            String[] temp = holiday.getDate().split("/");
            String month = temp[1];
            String name = holiday.getName();
            dataSource.add(country,month,name);
        }
        return dataSource;
    }

    public static void main(String[] args){
        new Crosstab();
    }

}
