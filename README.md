# ModsenEventsREST
Hello! This is my RESTFull application for MODSEN internship

To prepare the application for work, you need:

# First step: 
You need to create database: for example 'modsen_events';

    CREATE DATABASE IF NOT EXISTS modsen_events;
# Second step: 
You need to create you own application.properties file and fill property fields:


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

  
  
