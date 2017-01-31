package org.kie.workbench.shared;

import java.util.List;
import java.util.Map;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface ApplicantService {

    void filterApplicants( final Map<String, String> filter );

}
