package ru.ventra.recruitment.ui.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;

public class TopSixTheatersChart extends Chart {
    private static final long serialVersionUID = 1L;

    public TopSixTheatersChart() {
        // TODO this don't actually visualize top six theaters, but just makes a
        // pie chart
        super(ChartType.PIE);

        setCaption("Popular Movies");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.PIE);
        setWidth("100%");
        setHeight("90%");

        DataSeries series = new DataSeries();

        for (int i = 0; i < 6; i++) {
            series.add(new DataSeriesItem(i + "-x", i));
        }
        getConfiguration().setSeries(series);
    }

}
