package com.fsoft.libms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( { "com.fsoft.libms" } )
public class LibMsApplication {

    public static void main( String[] args ) {

	SpringApplication.run( LibMsApplication.class, args );
    }

}