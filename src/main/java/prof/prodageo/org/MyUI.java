package prof.prodageo.org;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* import for explicit declaration of callback */
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Button.ClickEvent;

import com.vaadin.data.* ;
import com.vaadin.data.Property.ValueChangeEvent ;

import prof.prodageo.persistance.* ;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("prof.prodageo.org.MyAppWidgetset")
public class MyUI extends UI {

        private static final Logger log = LoggerFactory.getLogger(MyUIServlet.class);

        private static final Facade ma_facade = new Facade();

    /* explicit declaration as attributes of graphical components for GenMyModel */
        final VerticalLayout layout = new VerticalLayout();
        final TextField name = new TextField();
        final TextField surname = new TextField();
        Button button = new Button("Click Me") ;
        

    /* explicit callback */
    /* https://vaadin.com/docs/-/part/framework/application/application-events.html */
    public class ClickMeClass implements Button.ClickListener
    {
        public void buttonClick(ClickEvent event) 
        {
            layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
            log.info("Button clicked with value : " + name.getValue());
        }
    }
        

    public class SelectComboClass implements Property.ValueChangeListener
    {
        public void valueChange(ValueChangeEvent event) 
        {
            log.info("Index selected in combo : " + event.getProperty().getValue());
        }
    }





    @Override
    protected void init(VaadinRequest vaadinRequest) {

        
        // final VerticalLayout layout = new VerticalLayout();
        
        // final TextField name = new TextField();
        String prof_name = "MALANDAIN" ;
        DtoProf the_prof = ma_facade.select_1_prof(prof_name);
       

        name.setCaption("Type your name here:" + the_prof.surname );

        /*
        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
            log.info("Button clicked with value : " + name.getValue());
        });
        */
        ClickMeClass callback = new ClickMeClass() ;
        button.addClickListener( callback ) ;

        ComboBox select = new ComboBox("My Select");

        Collection li = ma_facade.select_N_prof ( String name ) ;

        String one_prof_name ;

        for(ListIterator li=al.listIterator(); li.hasNext();)
        {
            one_prof_name = String li.next() ;
            select.addItem( one_prof_name );
        }

        /*
        select.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
            event.getProperty().getValue()))); */

        // Handle selection change
        SelectComboClass callback_combo = new SelectComboClass() ;
        select.addValueChangeListener( callback_combo ) ;

        layout.addComponents(name, button, select);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
