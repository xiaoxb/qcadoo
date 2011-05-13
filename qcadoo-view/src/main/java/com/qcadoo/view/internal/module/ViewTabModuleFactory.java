package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;

public class ViewTabModuleFactory extends ModuleFactory<ViewTabModule> {

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Autowired
    private ViewDefinitionParser viewDefinitionParser;

    @Override
    protected ViewTabModule parseElement(final String pluginIdentifier, final Element element) {
        String resource = getRequiredAttribute(element, "resource");

        return new ViewTabModule(pluginIdentifier, new ClassPathResource(pluginIdentifier + "/" + resource),
                viewDefinitionService, viewDefinitionParser);
    }

    @Override
    public String getIdentifier() {
        return "view-tab";
    }

}
