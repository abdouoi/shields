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

import com.github.nwillc.shields.repositories.*;
import org.pmw.tinylog.Logger;
import spark.servlet.SparkApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static spark.Spark.*;

class ShieldsApplication implements SparkApplication {
    private final static RepositoryAccess[] REPOS = new RepositoryAccess[]{
            new Codecov(),
            new Github(),
            new GradlePlugin(),
            new JCenter(),
            new MavenCentral(),
            new TLDRLegal(),
            new TravisCI()
    };
    private String properties = "";

    public ShieldsApplication() {
        try (
                final InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/build.json"));
                final BufferedReader bufferedReader = new BufferedReader(isr)
        ) {
            properties = bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            Logger.warn("Could not load build info", e);
        }
    }

    @Override
    public void init() {
        // Static files
        staticFileLocation("/public");

        // Exception Handler for missing parameters
        exception(MissingParamException.class, (e, req, res) -> {
            Logger.info(String.format("Invalid request %s from %s: %s", req.url(), req.ip(), e.getMessage()));
            res.status(404);
            res.body(e.getMessage());
        });

        // Setup routes
        get("/ping", (request, response) -> "PONG");
        get("/properties", (request, response) -> properties);

        for (RepositoryAccess repo : REPOS) {
            Logger.info("Registering Repo Handler for: /" + repo.getPath() + "/ -> " + repo.getClass().getSimpleName());
            get("/shield/" + repo.getPath(), repo::getShield);
            get("/homepage/" + repo.getPath(), repo::getHomepage);
        }
    }
}
