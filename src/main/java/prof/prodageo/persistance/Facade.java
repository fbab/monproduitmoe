package prof.prodageo.persistance;
import java.util.* ;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import prof.prodageo.persistance.DtoProf ;

public class Facade
{

  // connect to the database, populate with data
  ArrayList list_prof = new ArrayList () ;
  // ArrayList list_prof_surname = new ArrayList () ;
  private static final Logger log = LoggerFactory.getLogger(Facade.class);

  public Facade ()
  {
  	super();
    // simulate population of tables
    DtoProf the_prof1 = new DtoProf ( "MALANDAIN" , "Nicolas" ) ;
    list_prof.add ( the_prof1 ) ;
    DtoProf the_prof2 = new DtoProf ( "DELESTRE" , "Nicolas" ) ;
    list_prof.add ( the_prof2 ) ;

  }

  // return true if only one prof has been found
  public DtoProf select_1_prof ( String name )
  {

    DtoProf the_prof = (DtoProf) list_prof.get(0) ;
    log.info("select_1_prof  will return : " + the_prof.surname);
  	return the_prof ;
  }

  public Collection<DtoProf> select_N_prof ( String name )
  {
    return list_prof ;
  }

  public boolean insert_1_prof ( String name , String surname )
  {
    return true ;
  }


}
