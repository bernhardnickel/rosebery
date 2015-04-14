package at.ac.tuwien.infosys.rosebery.scenario.dsl;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;
import groovy.lang.GroovyShell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ScenarioDsl {
    private static final String TEMPLATE_FILE_NAME = "template.groovy";

    private static ScenarioDsl instance = null;

    private String scriptTemplate = null;

    private ScenarioDsl() {
        try {
            StringBuilder str = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(ScenarioDsl.class.getResourceAsStream(TEMPLATE_FILE_NAME)));

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                str.append(line).append(System.lineSeparator());
            }


            scriptTemplate = str.toString();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object evaluateDsl(String dsl) {
        return new GroovyShell().evaluate(scriptTemplate.concat(dsl));
    }

    private static ScenarioDsl getInstance() {
        if (instance == null) {
            synchronized (ScenarioDsl.class) {
                if (instance == null) {
                    instance = new ScenarioDsl();
                }
            }
        }

        return instance;
    }


    public static <T> Scenario<T> evaluate(String dsl) {
        return (Scenario<T>)getInstance().evaluateDsl(dsl);
    }
}
