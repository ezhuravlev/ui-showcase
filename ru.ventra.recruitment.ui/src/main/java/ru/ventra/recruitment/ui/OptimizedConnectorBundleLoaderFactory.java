package ru.ventra.recruitment.ui;


public class OptimizedConnectorBundleLoaderFactory {}/* extends ConnectorBundleLoaderFactory {
    private Set<String> eagerConnectors = new HashSet<String>();
    {
        eagerConnectors.add(PasswordFieldConnector.class.getName());
        eagerConnectors.add(VerticalLayoutConnector.class.getName());
        eagerConnectors.add(HorizontalLayoutConnector.class.getName());
        eagerConnectors.add(ButtonConnector.class.getName());
        eagerConnectors.add(UIConnector.class.getName());
        eagerConnectors.add(CssLayoutConnector.class.getName());
        eagerConnectors.add(TextFieldConnector.class.getName());
        eagerConnectors.add(PanelConnector.class.getName());
        eagerConnectors.add(LabelConnector.class.getName());
        eagerConnectors.add(WindowConnector.class.getName());
    }

    @Override
    protected LoadStyle getLoadStyle(JClassType connectorType) {
        if (eagerConnectors.contains(connectorType.getQualifiedBinaryName())) {
            return LoadStyle.EAGER;
        } else {
            // Loads all other connectors immediately after the initial view has
            // been rendered
            return LoadStyle.DEFERRED;
        }
    }
}*/
