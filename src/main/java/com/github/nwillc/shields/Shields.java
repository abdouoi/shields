/*
 * Copyright (c) 2015, nwillc@gmail.com
 *
 *  Permission to use, copy, modify, and/or distribute this software for any
 *  purpose with or without fee is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.nwillc.shields;

import org.apache.commons.cli.*;
import org.pmw.tinylog.Logger;

import static com.github.nwillc.shields.CommandLineInterface.CLI;
import static spark.Spark.ipAddress;
import static spark.Spark.port;

public class Shields {
    private final static ShieldsApplication application = new ShieldsApplication();

    // Run the app with embedded Jetty
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
                Logger.info("Configuring port: " + commandLine.getOptionValue(CLI.port.name()));
                port(Integer.parseInt(commandLine.getOptionValue(CLI.port.name())));
            }

            if (commandLine.hasOption(CLI.address.name())) {
                Logger.info("Configuring address: " + commandLine.getOptionValue(CLI.address.name()));
                ipAddress(commandLine.getOptionValue(CLI.address.name()));
            }

        } catch (ParseException e) {
            Logger.error("Failed to parse command line: " + e);
            CommandLineInterface.help(options, 1);
        }

        // Initialize the app
        application.init();
    }
}
