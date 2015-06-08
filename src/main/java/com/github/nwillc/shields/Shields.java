package com.github.nwillc.shields;

import com.github.nwillc.shields.repositories.JCenter;
import com.github.nwillc.shields.repositories.RepositoryAccess;
import org.apache.commons.cli.*;

import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.SparkBase.port;

public class Shields
{
    private final static Logger LOGGER = Logger.getLogger(Shields.class.getSimpleName());
    private final static RepositoryAccess[] REPOS = new RepositoryAccess[]{new JCenter()};

    enum CLI {
        help,
        port
    }

    public static void main(String[] args) {

        // Process command line
        Options options = setupOptions();
        CommandLineParser commandLineParser = new DefaultParser();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            if (commandLine.hasOption(CLI.help.name())) {
                help(options, 0);
            }

            if (commandLine.hasOption(CLI.port.name())) {
                LOGGER.info("Configuring port: " + commandLine.getOptionValue(CLI.port.name()));
                port(Integer.parseInt(commandLine.getOptionValue(CLI.port.name())));
            }

        } catch (ParseException e) {
            LOGGER.severe("Failed to parse command line: " + e);
            System.exit(2);
        }

        // Setup routes
        get("/ping", (request, response) -> "PONG");
        for (RepositoryAccess repo : REPOS) {
            get("/shield/" + repo.getPath(), repo::getShield);
            get("/homepage/" + repo.getPath(), repo::getHomepage);
        }
    }

    private static void help(Options options, int status) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java com.github.nwillc.shields.Shields [options]", options);
        System.exit(status);
    }

    private static Options setupOptions() {
        Option option;

        Options options = new Options();

        option = new Option(CLI.help.name().substring(0, 1), CLI.help.name(), false, "Get command line help.");
        option.setRequired(false);
        options.addOption(option);

        option = new Option(CLI.port.name().substring(0, 1), CLI.port.name(), true, "Port number to listen to.");
        option.setArgName("port_no");
        option.setArgs(1);
        option.setType(Integer.class);
        option.setRequired(false);
        options.addOption(option);

        return options;
    }
}
