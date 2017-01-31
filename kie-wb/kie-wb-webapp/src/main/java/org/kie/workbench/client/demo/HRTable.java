package org.kie.workbench.client.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.kie.workbench.shared.Applicant;
import org.kie.workbench.shared.ApplicantService;
import org.uberfire.ext.widgets.table.client.UberfirePagedTable;
import org.uberfire.paging.AbstractPageRow;

@Dependent
public class HRTable extends Composite implements RequiresResize {

    @Inject
    private Caller<ApplicantService> imp;

    private static final int PADDING = 30;

    protected final UberfirePagedTable<Row> dataGrid = new UberfirePagedTable<Row>( 10, null, true, true, true );
    protected final FlowPanel panel = new FlowPanel();
    protected final Button addButton = new Button();
    protected final List<Row> data = new ArrayList<Row>();
    protected final AsyncDataProvider<Row> dataProvider = new AsyncDataProvider<Row>() {
        @Override
        protected void onRangeChanged( final HasData<Row> display ) {
            final ColumnSortList columnSortList = dataGrid.getColumnSortList();
            Collections.sort( data, new Comparator<Row>() {
                @Override
                public int compare( final Row o1,
                                    final Row o2 ) {
                    if ( columnSortList == null || columnSortList.size() == 0 || columnSortList.get( 0 )
                            .isAscending() == false ) {
                        return o1.getName().compareTo( o2.getName() );
                    } else {
                        return o2.getName().compareTo( o1.getName() );
                    }
                }
            } );
            Scheduler.get().scheduleFixedDelay( new Scheduler.RepeatingCommand() {
                @Override
                public boolean execute() {
                    updateRowCount( data.size(), true );
                    updateRowData( 0, data );
                    return false;
                }
            }, 1000 );

        }
    };

    @PostConstruct
    public void init() {
        dataGrid.setEmptyTableCaption( "No data" );

        final Column<Row, String> nameColumn = new Column<Row, String>( new TextCell() ) {
            @Override
            public String getValue( Row row ) {
                return row.getName();
            }

            @Override
            public void onBrowserEvent( Cell.Context context,
                                        Element elem,
                                        Row object,
                                        NativeEvent event ) {
                super.onBrowserEvent( context, elem, object, event );
                if ( "click".equals( event.getType() ) ) {
                    GWT.log( "Click on " + object.getName() );
                }
            }
        };
        final Column<Row, String> ageColumn = new Column<Row, String>( new TextCell() ) {
            @Override
            public String getValue( Row row ) {
                return row.getAge();
            }
        };

        final Column<Row, String> countryColumn = new Column<Row, String>( new TextCell() ) {
            @Override
            public String getValue( Row row ) {
                return row.getCountry();
            }
        };

        ButtonCell previewButton = new ButtonCell();
        Column<Row, String> preview = new Column<Row, String>( previewButton ) {
            public String getValue( Row object ) {
                return "Hire " + object.getName();
            }
        };
        preview.setFieldUpdater( ( index, object, value ) -> Window.alert( "Hire " + object.getName() ) );
        nameColumn.setSortable( true );
        dataGrid.addColumn( nameColumn, "Name" );
        dataGrid.addColumn( ageColumn, "Age" );
        dataGrid.addColumn( countryColumn, "Country" );
        dataGrid.addColumn( preview, "Hire" );

        addButton.setText( "New Row" );
        addButton.setIcon( IconType.PLUS );
        addButton.getElement().getStyle().setMarginLeft( 10, Style.Unit.PX );
        addButton.addClickHandler( event -> {
//                data.add( new Row( data.size() ) );
            dataGrid.refresh();
        } );
        dataProvider.addDataDisplay( dataGrid );

        dataGrid.addColumnSortHandler( new ColumnSortEvent.AsyncHandler( dataGrid ) );

        panel.add( dataGrid );
//        panel.add( addButton );
        initWidget( panel );

    }

    public void filter( final Map<String, String> filter ) {
        imp.call( new RemoteCallback<Void>() {
            @Override
            public void callback( final Void applicants ) {
                dataGrid.refresh();
//                applicants.forEach( p -> {
//                    data.add( new Row( p.getName(), String.valueOf( p.getAge() ), p.getCountry() ) );
//                    dataGrid.refresh();
//                } );
            }
        } ).filterApplicants( filter );
    }

    class Row extends AbstractPageRow {

        private final String name;
        private final String age;
        private final String country;

        public Row( String name,
                    String age,
                    String country ) {
            this.name = name;
            this.age = age;
            this.country = country;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public String getCountry() {
            return country;
        }

        @Override
        public int compareTo( AbstractPageRow o ) {
            return getName().compareTo( ( (Row) o ).getName() );
        }
    }

    @Override
    public void onResize() {
        int height = getParent().getOffsetHeight() - PADDING;
        int width = getParent().getOffsetWidth() - PADDING;
        setPixelSize( width, height );
    }

}