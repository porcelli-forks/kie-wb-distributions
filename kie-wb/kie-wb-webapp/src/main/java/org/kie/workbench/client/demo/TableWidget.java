package org.kie.workbench.client.demo;

import javax.enterprise.context.Dependent;

import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Dependent
@Templated
public class TableWidget implements IsElement {

    public void init( String logoURL,
                      String textAlign ) {
    }
}
