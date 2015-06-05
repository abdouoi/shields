package com.github.nwillc;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class Shields
{
        public static void main(String[] args) {

            get("/hello", (request, response) -> "Hello World!");
        }

}
