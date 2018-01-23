package com.msauvee.dsn.anonymization;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import java.util.stream.Stream;

public class AnonymizerApplication extends Application<AnonymizerConfiguration> {

    public static void main(String[] args) throws Exception {
        new AnonymizerApplication().run(args); 
    }    
    
    @Override
    public void initialize(Bootstrap<AnonymizerConfiguration> boostrap) {
        boostrap.addCommand(new AnonymizerCommand());
    }

    @Override
    public void run(AnonymizerConfiguration t, io.dropwizard.setup.Environment e) throws Exception {
    }

}
