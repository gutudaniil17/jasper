package org.example.report;

import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalImageAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.example.dao.HolidayDAO;
import org.example.model.Holiday;
import org.example.service.Parser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class Report {
    public Report() {
        build();
    }

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
                            cmp.image("src/main/resources/cedacri.png")
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
                    .setDataSource(createDataSourceFromJRResultSet())
                    .show();
        } catch (DRException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JRDataSource createDataSourceFromJRBean() throws Exception {
        List<Holiday> list = Parser.parse();
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(list);
        return collectionDataSource;
    }
    private JRDataSource createDataSourceFromJRResultSet() throws Exception{
        ResultSet resultSet = HolidayDAO.getHolidaysFromTable();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
        return resultSetDataSource;
    }

    private JRDataSource createDataSourceFromJRMap() throws Exception {
        List<Holiday> list = Parser.parse();
        List<Map<String,?>> maps = new ArrayList<>();
        for(Holiday holiday : list){
            Map<String,String> map = new HashMap<>();
            map.put("country",holiday.getCountry());
            map.put("date",holiday.getDate());
            map.put("name",holiday.getName());
            maps.add(map);
        }
        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
        return dataSource;
    }
}
