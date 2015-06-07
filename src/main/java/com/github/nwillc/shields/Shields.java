package com.github.nwillc.shields;

import static spark.Spark.get;
/**
 * Hello world!
 *
 */
public class Shields
{
        public static void main(String[] args) {
            get("/ping", (request, response) -> "PONG");
        }

}
