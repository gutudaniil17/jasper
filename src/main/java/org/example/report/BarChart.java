package org.example.report;

import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import org.example.model.Holiday;
import org.example.service.Parser;
import org.example.service.ParserImpl;

import java.util.List;

public class BarChart {
    private Parser parser;

    public BarChart() {
        build();
    }

    private void build() {
        BarChartBuilder barChart = DynamicReports.cht.barChart();

        // Define the data source
        DRDataSource dataSource = null;
        try {
            dataSource = createDataSource();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Define the columns
        TextColumnBuilder<String> monthColumn = DynamicReports.col.column("Month", "month", String.class);
        TextColumnBuilder<Integer> nameColumn = DynamicReports.col.column("Name", "name", Integer.class);

        // Add the columns to the bar chart
        barChart.setCategory(monthColumn);
        barChart.addSerie(DynamicReports.cht.serie(nameColumn));

        try {
            DynamicReports.report()
                    .summary(barChart)
                    .sortBy(monthColumn)
                    .setDataSource(dataSource)
                    .show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private DRDataSource createDataSource() throws Exception {
        DRDataSource dataSource = new DRDataSource("month", "name");
        parser = new ParserImpl();
        List<Holiday> holidays = parser.parseFromXML();
        for (Holiday holiday : holidays) {
            String[] temp = holiday.getDate().split("/");
            String month = temp[1];
            int name = 1;
            dataSource.add(month, name);
        }
        return dataSource;
    }

    public static void main(String[] args) {
        new BarChart();
    }
}

