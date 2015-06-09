package com.github.nwillc.shields;

import com.github.nwillc.shields.repositories.*;
import org.apache.commons.cli.*;

import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.SparkBase.port;
import static com.github.nwillc.shields.CommandLineInterface.CLI;

public class Shields {
    private final static Logger LOGGER = Logger.getLogger(Shields.class.getSimpleName());
    private final static RepositoryAccess[] REPOS = new RepositoryAccess[]{
            new JCenter(),
            new MavenCentral(),
            new GradlePlugin(),
            new Github()};

    public static void main(String[] args) {

        // Process command line
        Options options = CommandLineInterface.getOptions();
        CommandLineParser commandLineParser = new DefaultParser();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            if (commandLine.hasOption(CLI.help.name())) {
                CommandLineInterface.help(options, 0);
            }

            if (commandLine.hasOption(CLI.port.name())) {
                LOGGER.info("Configuring port: " + commandLine.getOptionValue(CLI.port.name()));
                port(Integer.parseInt(commandLine.getOptionValue(CLI.port.name())));
            }

        } catch (ParseException e) {
            LOGGER.severe("Failed to parse command line: " + e);
            CommandLineInterface.help(options, 1);
        }

        // Setup routes
        get("/ping", (request, response) -> "PONG");
        for (RepositoryAccess repo : REPOS) {
            get("/shield/" + repo.getPath(), repo::getShield);
            get("/homepage/" + repo.getPath(), repo::getHomepage);
        }
    }


}
