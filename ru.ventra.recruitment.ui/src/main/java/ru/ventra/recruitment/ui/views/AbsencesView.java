package ru.ventra.recruitment.ui.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.cssinject.CSSInject;

import com.google.gson.JsonObject;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.event.CalendarEventProvider;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.BackwardEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.BackwardHandler;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClickHandler;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventResize;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.ForwardEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.ForwardHandler;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.MoveEvent;
import com.vaadin.addon.calendar.ui.CalendarTargetDetails;
import com.vaadin.addon.calendar.ui.handler.BasicEventMoveHandler;
import com.vaadin.addon.calendar.ui.handler.BasicEventResizeHandler;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableTransferable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Configurable
public class AbsencesView extends CssLayout implements View {
    private static final long serialVersionUID = 1L;

    private CssLayout catalog;

    private Window popup;

    private CSSInject css;

    private Calendar cal;

    public AbsencesView() {
        super();
        
        setSizeFull();
        addStyleName("schedule");

        css = new CSSInject(UI.getCurrent());

        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();
        tabs.addStyleName("borderless");
        addComponent(tabs);
        
        tabs.addComponent(buildCalendarView());

        catalog = new CssLayout();
        catalog.setCaption("Catalog");
        catalog.addStyleName("catalog");
        tabs.addComponent(catalog);
    }

    private MovieEventProvider provider = new MovieEventProvider();

    private Component buildCalendarView() {
        VerticalLayout calendarLayout = new VerticalLayout();
        calendarLayout.setCaption("Calendar");
        calendarLayout.addStyleName("dummy");
        calendarLayout.setMargin(true);

        cal = new Calendar(provider);
        cal.setWidth("100%");
        cal.setHeight("1000px");

        // cal.setStartDate(new Date());
        // cal.setEndDate(new Date());

        cal.setHandler(new EventClickHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void eventClick(EventClick event) {
                hideTray();
                getUI().removeWindow(popup);
                buildPopup((MovieEvent) event.getCalendarEvent());
                getUI().addWindow(popup);
                popup.focus();
                // if (!helpShown) {
                // ((QuickTicketsDashboardUI) getUI())
                // .getHelpManager()
                // .addOverlay(
                // "Change the movie",
                // "Try to drag the movie posters from the tray onto the poster in the window",
                // "poster").center();
                // helpShown = true;
                // }
            }
        });
        calendarLayout.addComponent(cal);

        cal.setFirstVisibleHourOfDay(11);
        cal.setLastVisibleHourOfDay(23);

        cal.setHandler(new BackwardHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void backward(BackwardEvent event) {
                createEvents();
            }
        });

        cal.setHandler(new ForwardHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void forward(ForwardEvent event) {
                createEvents();
            }
        });

        cal.setDropHandler(new DropHandler() {
            private static final long serialVersionUID = 1L;

            public void drop(DragAndDropEvent event) {
                CalendarTargetDetails details = (CalendarTargetDetails) event.getTargetDetails();
                TableTransferable transferable = (TableTransferable) event.getTransferable();

                createEvent(details, transferable);
            }

            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }

        });

        cal.setHandler(new BasicEventMoveHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void eventMove(MoveEvent event) {
                CalendarEvent calendarEvent = event.getCalendarEvent();
                if (calendarEvent instanceof MovieEvent) {
                    MovieEvent editableEvent = (MovieEvent) calendarEvent;

                    Date newFromTime = event.getNewStart();

                    // Update event dates
                    long length = editableEvent.getEnd().getTime() - editableEvent.getStart().getTime();
                    setDates(editableEvent, newFromTime, new Date(newFromTime.getTime() + length));
                    showTray();
                }
            }

            protected void setDates(MovieEvent event, Date start, Date end) {
                event.start = start;
                event.end = end;
            }
        });
        cal.setHandler(new BasicEventResizeHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void eventResize(EventResize event) {
                Notification.show("You're not allowed to change the movie duration");
            }
        });

        createEvents();

        return calendarLayout;
    }

    private void createEvent(CalendarTargetDetails details, TableTransferable transferable) {
        Date start = details.getDropTime();
        Date end = details.getDropTime();
        int endHour = (int) (1 + Math.round(Math.random()));
        int endMinutes = (int) (45 + Math.random() * 30);
        end.setHours(end.getHours() + endHour);
        end.setMinutes(endMinutes);
        MovieEvent newEvent = new MovieEvent(details.getDropTime(), end, null);// FIXME
        provider.addEvent(newEvent);
    }

    boolean[] created = new boolean[366];

    void createEvents() {
        Date startDate = cal.getStartDate();

        int k = (startDate.getMonth()) * 30 + startDate.getDate();
        if (!created[k]) {

            for (int i = 0; i < 7; i++) {
                createEventsForDay(startDate);
                startDate.setDate(startDate.getDate() + 1);
            }

            created[k] = true;
        }

        // Add all movie cover images as classes to CSSInject
        String styles = "";
        for (Movie m : new ArrayList<Movie>()) {//FIXME
            WebBrowser webBrowser = Page.getCurrent().getWebBrowser();

            String bg = "url(VAADIN/themes/" + UI.getCurrent().getTheme() + "/img/event-title-bg.png), url(" + m.posterUrl + ")";

            // IE8 doesn't support multiple background images
            if (webBrowser.isIE() && webBrowser.getBrowserMajorVersion() == 8) {
                bg = "url(" + m.posterUrl + ")";
            }

            styles += ".v-calendar-event-" + m.titleSlug() + " .v-calendar-event-content {background-image:" + bg + ";}";
        }
        css.setStyles(styles);
    }

    void createEventsForDay(Date day) {
        List<Movie> movies = new ArrayList<Movie>() {
            {
                add(new Movie("11", "12", "13", "14", new JsonObject(), new JsonObject()));
                add(new Movie("21", "22", "23", "24", new JsonObject(), new JsonObject()));
            }
        };
        boolean[] used = new boolean[movies.size()];

        Date date = new Date(day.getTime());
        // Start from noon
        date.setHours(11);
        date.setMinutes(0);
        date.setSeconds(0);

        while (date.getHours() < 23) {
            // Get "random" movie

            int i = -1;
            int reallyStupidStuffForCodeThatIDontReallyUnderStandWTFItIsDoing = 0;
            do {
                i = (int) (Math.random() * movies.size());
                if (!used[i]) {
                    used[i] = true;
                    break;
                }
                if (reallyStupidStuffForCodeThatIDontReallyUnderStandWTFItIsDoing++ > used.length) {
                    break;
                }
            } while (true);

            Movie m = movies.get(i);

            Date start = new Date(date.getTime());
            Date end = new Date(start.getTime());
            // int endHour = (int) (1 + Math.round(Math.random()));
            // int endMinutes = (int) (45 + Math.random() * 30);
            // end.setHours(end.getHours() + endHour);
            end.setMinutes(end.getMinutes() + m.duration);

            MovieEvent e = new MovieEvent(start, end, m);
            provider.addEvent(e);

            date.setDate(end.getDate());
            date.setHours(end.getHours());
            date.setMinutes((int) (end.getMinutes() + 15 + (Math.random() * 60)));

            if (date.getDate() > day.getDate())
                break;
        }

    }

    class MovieEventProvider implements CalendarEventProvider {
        private static final long serialVersionUID = 1L;

        private List<CalendarEvent> events = new ArrayList<CalendarEvent>();

        @Override
        public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
            return events;
        }

        public void addEvent(CalendarEvent MovieEvent) {
            events.add(MovieEvent);
        }

    }

    class MovieEvent implements CalendarEvent {
        private static final long serialVersionUID = 1L;

        Date start;
        Date end;
        String caption;
        Movie movie;

        public MovieEvent(Date start, Date end, Movie movie) {
            this.start = start;
            this.end = end;
            this.caption = movie.title;
            this.movie = movie;
        }

        @Override
        public Date getStart() {
            return start;
        }

        @Override
        public Date getEnd() {
            return end;
        }

        @Override
        public String getCaption() {
            return caption;
        }

        @Override
        public String getDescription() {
            return "";
        }

        @Override
        public String getStyleName() {
            return movie.titleSlug();
        }

        @Override
        public boolean isAllDay() {
            return false;
        }

    }

    void buildPopup(final MovieEvent event) {
        popup = new MovieDetailsWindow(event.movie, event);
    }

    HorizontalLayout tray;

    void buildTray() {
        if (tray != null)
            return;

        tray = new HorizontalLayout();
        tray.setWidth("100%");
        tray.addStyleName("tray");
        tray.setSpacing(true);
        tray.setMargin(true);

        Label warning = new Label("You have unsaved changes made to the schedule");
        warning.addStyleName("warning");
        warning.addStyleName("icon-attention");
        tray.addComponent(warning);
        tray.setComponentAlignment(warning, Alignment.MIDDLE_LEFT);
        tray.setExpandRatio(warning, 1);

        ClickListener close = new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                tray.removeStyleName("v-animate-reveal");
                tray.addStyleName("v-animate-hide");
            }
        };

        Button confirm = new Button("Confirm");
        confirm.addStyleName("wide");
        confirm.addStyleName("default");
        confirm.addClickListener(close);
        tray.addComponent(confirm);
        tray.setComponentAlignment(confirm, Alignment.MIDDLE_LEFT);

        Button discard = new Button("Discard");
        discard.addStyleName("wide");
        discard.addClickListener(close);
        tray.addComponent(discard);
        tray.setComponentAlignment(discard, Alignment.MIDDLE_LEFT);
    }

    // boolean helpShown = false;

    void showTray() {
        buildTray();
        tray.removeStyleName("v-animate-hide");
        tray.addStyleName("v-animate-reveal");
        addComponent(tray);
    }

    void hideTray() {
        if (tray != null)
            removeComponent(tray);
    }

    public static class Movie {
        public final String title;
        public final String synopsis;
        public final String thumbUrl;
        public final String posterUrl;
        /** In minutes */
        public final int duration;
        public Date releaseDate = null;

        public int score;
        public double sortScore = 0;

        Movie(String title, String synopsis, String thumbUrl, String posterUrl, JsonObject releaseDates, JsonObject critics) {
            this.title = title;
            this.synopsis = synopsis;
            this.thumbUrl = thumbUrl;
            this.posterUrl = posterUrl;
            this.duration = (int) ((1 + Math.round(Math.random())) * 60 + 45 + (Math.random() * 30));
            try {
                String datestr = releaseDates.get("theater").getAsString();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                releaseDate = df.parse(datestr);
                score = critics.get("critics_score").getAsInt();
                sortScore = 0.6 / (0.01 + (System.currentTimeMillis() - releaseDate.getTime()) / (1000 * 60 * 60 * 24 * 5));
                sortScore += 10.0 / (101 - score);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public String titleSlug() {
            return title.toLowerCase().replace(' ', '-').replace(":", "").replace("'", "").replace(",", "").replace(".", "");
        }

        public void reCalculateSortScore(java.util.Calendar cal) {
            if (cal.before(releaseDate)) {
                sortScore = 0;
                return;
            }
            sortScore = 0.6 / (0.01 + (cal.getTimeInMillis() - releaseDate.getTime()) / (1000 * 60 * 60 * 24 * 5));
            sortScore += 10.0 / (101 - score);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
