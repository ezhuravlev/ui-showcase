package ru.ventra.recruitment.ui;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

public class ApplicationServlet extends VaadinServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) {
		
        return new VaadinServletService(this, deploymentConfiguration) {
			private static final long serialVersionUID = 1L;

			@Override
            public ClassLoader getClassLoader() {
				return Thread.currentThread().getContextClassLoader();
                //return getClass().getClassLoader();
            }
        };
    }	
}
