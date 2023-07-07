package org.example.report;

import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.charts.JRValueAxisFormat;
import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.engine.JRDataSource;
import org.example.model.Holiday;
import org.example.service.Parser;

import java.awt.*;
import java.util.ArrayList;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class Report {
    public Report() {
        build();
    }
    StyleBuilder columnTitleStyle  = stl.style()
            .setBorder(stl.pen1Point())
            .setItalic(true)
            /*.setBackgroundColor(Color.LIGHT_GRAY)*/;

    private void build() {
        try {
            report()
                    .setColumnStyle(columnTitleStyle)
                    .columns(
                            col.column("country", "country", type.stringType()),
                            col.column("date", "date", type.stringType()),
                            col.column("name", "name", type.stringType()))
                    .title(cmp.text("Holydays"))
                    .pageFooter((cmp.pageXofY()))
                    .setDataSource(createDataSource())
                    .show();
        } catch (DRException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JRDataSource createDataSource() throws Exception {
        DRDataSource dataSource = new DRDataSource("country", "date", "name");
        ArrayList<Holiday> list = (ArrayList<Holiday>) Parser.parse();
        for (Holiday holiday : list) {
            String country = holiday.getCountry().equals("Italia") ? "IT"
                    : holiday.getCountry().equals("Moldavia") ? "MD" : "";
            String date = holiday.getDate();
            String name = holiday.getName();
            dataSource.add(country, date, name);
        }
        return dataSource;
    }

    public static void main(String[] args) {
        new Report();
    }
}
