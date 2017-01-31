package org.kie.workbench.client.demo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.Image;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Dependent
@Templated
public class LogoWidget implements IsElement {

    @Inject
    @DataField
    Div div;

    @Inject
    @DataField
    Image logo;

    public void init( final String logoURL,
                      final String textAlign,
                      final String height,
                      final String width ) {
        logo.setAttribute( "src", logoURL );
        logo.setAttribute( "height", height );
        logo.setAttribute( "width", width );
        div.setAttribute( "style", "text-align:" + textAlign );
    }
}
