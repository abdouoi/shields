package com.github.nwillc.shields;

import com.github.nwillc.shields.GradlePlugin;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class Shields
{
    private static GradlePlugin gradlePlugin = new GradlePlugin();

        public static void main(String[] args) {
            get("/image", gradlePlugin::response);
            get("/hello", (request, response) -> "Hello World!");
        }

}
