package com.msauvee.dsn.anonymization;

import io.dropwizard.cli.Command;
import io.dropwizard.setup.Bootstrap;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnonymizerCommand extends Command {

    private static Logger LOG = LoggerFactory.getLogger(AnonymizerCommand.class);
    
    private static final String OUTPUT_ENCODING = "ISO-8859-1";
    private final DataFactory df = new DataFactory();
    private PrintStream output = null;
    private final Map<String, String> idDictionnary = new HashMap<>();

    public AnonymizerCommand() {
        super("anonymize", "Anonymize a DSN file");     
        try {
            output = new PrintStream(System.out, true, OUTPUT_ENCODING);
        } catch (UnsupportedEncodingException ex) {
            LOG.error("Enable to output in encoding " + OUTPUT_ENCODING, ex);
        }
    }
    
    private void processLine(String line) {
        String[] items = line.split(",");
        String key = items[0];
        String value = items[1].substring(1, items[1].length()-1);
        
        switch(items[0]) {
            case "S10.G00.01.001":
            case "S10.G00.03.001":
            case "S21.G00.06.001":
                // SIREN
                value = df.getCompany().siren;
                break;
            case "S10.G00.01.002":  
            case "S10.G00.03.002":
            case "S21.G00.06.002":
                // NIC
                value = df.getCompany().nic;
                break;
            case "S21.G00.11.001":
                // NIC
                value = df.getSubsidiary().nic;
                break;
            case "S21.G00.40.019":
            case "S21.G00.41.012":
            case "S21.G00.60.600":
                // siret
                value = df.getCompany().siret;
                break;   
            case "S10.G00.01.003":
                // Company
                value = df.getCompany().name;
                break;                
            case "S21.G00.30.001":                
            case "S21.G00.31.008":
                df.nextPeople();
                // social number. keep sex and birth year
                value = df.getPeople().getSocialNumber(value.substring(0,3));
                break;
            case "S21.G00.30.002":
            case "S21.G00.31.009":
                // name
                value = df.getPeople().lastName;
                break;
            case "S21.G00.30.003":
                // usage name
                value = df.getPeople().lastName;
                break;
            case "S21.G00.30.004":
            case "S21.G00.31.010":
                // first name
                value = df.getPeople().firstName;
                break; 
            case "S21.G00.30.007":
                // birth city
                value = df.getPeople().birthCity;
                break;  
            case "S10.G00.01.004":
            case "S21.G00.06.004":
                // address
                value = df.getCompany().address.address;
                break;
            case "S21.G00.11.003":
                // address
                value = df.getSubsidiary().address.address;
                break;
            case "S21.G00.30.008":
            case "S21.G00.85.001":
            case "S21.G00.85.003":
                // address
                value = df.getPeople().address.address;
                break;
            case "S10.G00.01.005":
            case "S21.G00.06.005":
                // zip code
                value = df.getSubsidiary().address.zipcode;
                break;
            case "S21.G00.11.004":
                // zip code
                value = df.getCompany().address.zipcode;
                break;
            case "S21.G00.30.009":
            case "S21.G00.85.004":
                // zip code
                value = df.getPeople().address.zipcode;
                break;     
            case "S10.G00.01.006":
            case "S21.G00.06.006":
                // city
                value = df.getSubsidiary().address.city;
                break;
            case "S21.G00.11.005":
                // city
                value = df.getCompany().address.city;
                break;
            case "S21.G00.30.010":
            case "S21.G00.85.005":
                // city
                value = df.getPeople().address.city;
                break; 
            case "S10.G00.01.009":
            case "S10.G00.01.010": 
            case "S21.G00.06.007":
            case "S21.G00.06.008":
                // additional address
                value = df.getCompany().address.address2;
                break;                    
            case "S21.G00.11.006":
            case "S21.G00.11.007":
                // additional address
                value = df.getSubsidiary().address.address2;
                break;    
            case "S21.G00.30.016":
            case "S21.G00.30.017":
            case "S21.G00.85.008":
            case "S21.G00.85.009":
                // additional address
                value = df.getPeople().address.address2;
                break;        
            case "S10.G00.02.002":
            case "S20.G00.07.001":
                // name and first name
                df.nextPeople();
                value = df.getPeople().firstName + " " + df.getPeople().lastName;
                break; 
            case "S10.G00.02.004":
            case "S10.G00.03.003":
            case "S20.G00.07.003":
            case "S21.G00.30.018":
                // email 
                value = df.getPeople().email;
                break; 
            case "S10.G00.02.005":
            case "S10.G00.02.006":
            case "S20.G00.07.002":
                // phone
                value = df.getPeople().phoneNumber;
                break;                
            case "S21.G00.30.019":
            case "S21.G00.30.020":
                // id # and temp #
                value = RandomStringUtils.randomNumeric(value.length());
                break; 
            case "S21.G00.40.009":
            case "S21.G00.51.010":
            case "S21.G00.52.006":
                // contract #
                value = anonymizeValue(idDictionnary, value, RandomStringUtils.randomAlphanumeric(value.length()));
                break;       
            case "S21.G00.20.004":
            case "S21.G00.60.007":
                // IBAN
                value = anonymizeValue(idDictionnary, value, RandomStringUtils.randomNumeric(25));
                break;
            case "S21.G00.20.003":
            case "S21.G00.60.008":
                // BIC
                value = anonymizeValue(idDictionnary, value, RandomStringUtils.randomNumeric(11));
                break;
            case "S21.G00.23.006":
            case "S21.G00.81.005":
                // INSEE City
                value = RandomStringUtils.randomNumeric(3);
                break;
            case "S21.G00.06.003":
            case "S21.G00.85.002":
                // APEN/T Code
                value = df.getCompany().ape;
                break; 
            case "S21.G00.11.002":
                // APEN/T Code
                value = df.getSubsidiary().ape;
                break;                                     
        }
        output.println(key + ",'" + value + "'");                       
    }

    private String anonymizeValue(Map<String, String> dictionnary, String value, String proposedValue) {
        String newValue = dictionnary.get(value);
        if (newValue == null) {
            newValue = proposedValue;
            dictionnary.put(value, newValue);
        }
        return newValue;
    }

    @Override
    public void configure(Subparser subparser) {
        subparser.addArgument("-in", "--input")
            .dest("input")
            .type(String.class)
            .required(true)
            .help("The DSN input file to anonymize");
        
        subparser.addArgument("-l", "--loop")
            .dest("loop")
            .type(Integer.class)
            .required(false)
            .help("The number a time the DSN input file will be used. Default is once.");                
    }

    @Override
    public void run(Bootstrap<?> btstrp, Namespace namespace) throws Exception {
        df.nextCompany();  

        int subsidiariesCount = 1;
        if (namespace.getInt("loop") != null) {
            subsidiariesCount = namespace.getInt("loop");
        }
        
        if (namespace.getString("input") != null) {
            for (int i = 0; i < subsidiariesCount; i++) {
                Stream<String> input = Files.lines(Paths.get(namespace.getString("input")), Charset.forName("iso-8859-1"));
                input.forEachOrdered(line -> processLine(line));
                df.nextSubsidiary();
            }            
        }  
    }
}
