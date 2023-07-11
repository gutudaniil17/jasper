package org.example.report;

import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import org.example.model.Holiday;
import org.example.service.Parser;

import java.util.List;

public class BarChart {
    public static void main(String[] args) {
        // Create a bar chart builder
        BarChartBuilder barChart = DynamicReports.cht.barChart();

        // Define the data source
        DRDataSource dataSource = null;
        try {
            dataSource = createDataSource();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Define the columns
        /*TextColumnBuilder<String> countryColumn = DynamicReports.col.column("Country", "country", String.class);*/
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

    private static DRDataSource createDataSource() throws Exception {
        DRDataSource dataSource = new DRDataSource("month", "name");
        List<Holiday> holidays = Parser.parse();
        for(Holiday holiday : holidays){
            String country = holiday.getCountry();
            String[] temp = holiday.getDate().split("/");
            String  month = temp[1];
            int name = 1;
            dataSource.add(month,name);
        }
        return dataSource;
    }
}

