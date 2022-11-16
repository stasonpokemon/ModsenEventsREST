# ModsenEventsREST
Hello! This is my RESTFull application for MODSEN internship

To prepare the application for work, you need:

# First step: 
You need to create database for main app: for example 'modsen_events';

And you need to create database for test app: for example 'modsen_events_test';

    CREATE DATABASE IF NOT EXISTS modsen_events;
    CREATE DATABASE IF NOT EXISTS modsen_events_test;
    
# Second step: 
You need to create you own application.properties file (to main/resources folder) and application-test.properties (to test/resources folder) and then fill in property fields for both files:


  #Data source configuration
  
    spring.datasource.driver-class-name=
    
    spring.datasource.url=
    
    spring.datasource.username=
    
    spring.datasource.password=
    
  
  #Jpa / Hibernate configuration
   
    spring.jpa.show-sql=
    spring.jpa.hibernate.ddl-auto=none
    spring.jpa.properties.hibernate.dialect=
    spring.jpa.properties.hibernate.current_session_context_class=org.springframework.orm.hibernate5.SpringSessionContext
   
  #Server port for localhost connection
  
    server.port=8081

  
  
