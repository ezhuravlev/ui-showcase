package ru.ventra.recruitment.ui.charts;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Series;

public class TopGrossingMoviesChart extends Chart {
    private static final long serialVersionUID = 1L;

    public TopGrossingMoviesChart() {
        // TODO this don't actually visualize top grossing movies, but just
        // makes a
        // bar chart of movie scores

        setCaption("Top Grossing Movies");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.BAR);
        getConfiguration().getxAxis().getLabels().setEnabled(false);
        getConfiguration().getxAxis().setTickWidth(0);
        setWidth("100%");
        setHeight("90%");

        List<Series> series = new ArrayList<Series>();
        for (int i = 0; i < 6; i++) {
            series.add(new ListSeries(i + "-x", i));
        }
        getConfiguration().setSeries(series);
    }
}
