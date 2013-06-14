package ru.ventra.recruitment.ui;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class RecruitmentUIProvider extends UIProvider {
    private static final long serialVersionUID = 1L;

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        if (event.getRequest().getParameter("mobile") != null && event.getRequest().getParameter("mobile").equals("false")) {
            return RecruitmentUI.class;
        }

        /*if (event.getRequest().getHeader("user-agent").toLowerCase().contains("mobile") && !event.getRequest().getHeader("user-agent").toLowerCase().contains("ipad")) {
            return MobileCheckUI.class;
        }*/

        return RecruitmentUI.class;
    }
}
