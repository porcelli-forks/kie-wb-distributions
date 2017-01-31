package org.kie.workbench.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.api.KieServices;
import org.kie.api.command.KieCommands;
import org.kie.workbench.shared.Applicant;
import org.kie.workbench.shared.ApplicantService;

@Service
@ApplicationScoped
public class ApplicantServiceImpl implements ApplicantService {

    @Inject
    private DataLoader loader;

    private final List<Applicant> listFront = new ArrayList<>();
    private final List<Applicant> listBack = new ArrayList<>();
    private final KieCommands commandsFactory = KieServices.Factory.get().getCommands();

    private static final String CONTAINER_ID = "stateless-kjar1";
    private static final String KIE_SESSION = "kbase1.stateless";

    @PostConstruct
    public void setup() {

        listBack.add( createApplicant( "Alex", 35, "US", TypeInsert.ALL ) );
        listFront.add( createApplicant( "Alex", 35, "US", TypeInsert.ALL ) );
        listBack.add( createApplicant( "Michael", 36, "GB", TypeInsert.BACKEND_ALL ) );
        listFront.add( createApplicant( "Mauricio", 30, "GB", TypeInsert.FRONT ) );
        listBack.add( createApplicant( "Eder", 32, "Brazil", TypeInsert.BACKEND_DROOLS ) );
        listFront.add( createApplicant( "Paulo", 25, "Brazil", TypeInsert.FRONT_JS ) );

//        dataInsert.add( "Guilherme", 24, TypeInsert.JS ));
        listBack.add( createApplicant( "Kris", 55, "BE", TypeInsert.ALL ) );
        listFront.add( createApplicant( "Kris", 55, "BE", TypeInsert.ALL ) );

        listBack.add( createApplicant( "Geoffrey", 35, "BE", TypeInsert.ALL ) );
        listFront.add( createApplicant( "Geoffrey", 35, "BE", TypeInsert.ALL ) );

        listBack.add( createApplicant( "Mark", 36, "GB", TypeInsert.BACKEND_DROOLS ) );
        listFront.add( createApplicant( "Joe", 30, "US", TypeInsert.FRONT ) );
        listFront.add( createApplicant( "Sarah", 32, "US", TypeInsert.FRONT_JS ) );
        listBack.add( createApplicant( "Edson", 54, "Canada", TypeInsert.BACKEND_JAVA ) );

    }

    private Applicant createApplicant( final String name,
                                       final int age,
                                       final String country,
                                       final TypeInsert typeInsert ) {
        final Applicant applicant = new Applicant();
        applicant.setAge( age );
        applicant.setCountry( country );
        applicant.setName( name );
        if ( typeInsert == TypeInsert.FRONT ||
                typeInsert == TypeInsert.FRONT_JS ||
                typeInsert == TypeInsert.ALL ) {
            applicant.setHasCSSSkill( true );
            applicant.setHasHTMLSkill( true );
        }
        if ( typeInsert == TypeInsert.FRONT_JS ||
                typeInsert == TypeInsert.JS ||
                typeInsert == TypeInsert.ALL ) {
            applicant.setHasJavaScriptSkill( true );
        }
        if ( typeInsert == TypeInsert.BACKEND_JAVA ||
                typeInsert == TypeInsert.BACKEND_ALL ||
                typeInsert == TypeInsert.BACKEND_DROOLS ||
                typeInsert == TypeInsert.ALL ) {
            applicant.setHasJavaSkill( true );
        }
        if ( typeInsert == TypeInsert.BACKEND_JAVA ||
                typeInsert == TypeInsert.BACKEND_ALL ||
                typeInsert == TypeInsert.ALL ) {
            applicant.setHasJBPMSkill( true );
        }
        if ( typeInsert == TypeInsert.BACKEND_DROOLS ||
                typeInsert == TypeInsert.BACKEND_ALL ||
                typeInsert == TypeInsert.ALL ) {
            applicant.setHasDroolsSkill( true );
        }
        return applicant;
    }

    @Override
    public void filterApplicants( final Map<String, String> filter ) {
        loader.load();

        if ( filter == null || filter.isEmpty()
                || !filter.containsKey( "Rule" ) || !filter.containsKey( "EndPoint" ) ) {
            //return Collections.emptyList();
        }

        final String endpoint = filter.getOrDefault( "EndPoint", "http://localhost:8080/kie-server/services/rest/server" );
        final String rule = filter.getOrDefault( "Rule", "BACK" ).toUpperCase();

//        List<Command<?>> commands = new ArrayList<Command<?>>();
//        BatchExecutionCommand executionCommand = commandsFactory.newBatchExecution( commands, KIE_SESSION );
//
//        commands.add( commandsFactory.newInsertElements() );
//
//        final KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesRestClient( endpoint, "porcelli", "Kingandi@9669" );
//
//        final RuleServicesClient ruleClient = kieServicesClient.getServicesClient( RuleServicesClient.class );
//        ServiceResponse<ExecutionResults> reply = ruleClient.executeCommandsWithResults( CONTAINER_ID, executionCommand );

        final List<Applicant> list = new ArrayList<>();
        if ( rule.contains( "FRONT" ) ) {
            list.addAll( listFront );
        } else if ( rule.contains( "BACK" ) ) {
            list.addAll( listBack );
        }
        //return list;
    }

    public enum TypeInsert {
        ALL, FRONT, FRONT_JS, JS, BACKEND_JAVA, BACKEND_ALL, BACKEND_DROOLS
    }
}
