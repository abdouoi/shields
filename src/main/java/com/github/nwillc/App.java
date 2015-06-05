package com.github.nwillc;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class App
{

        public static void main(String[] args) {

            get("/hello", (request, response) -> "Hello World!");
        }

}
