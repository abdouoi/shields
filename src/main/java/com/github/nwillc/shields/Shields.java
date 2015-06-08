package com.github.nwillc.shields;

import com.github.nwillc.shields.repositories.JCenter;
import com.github.nwillc.shields.repositories.RepositoryAccess;

import static spark.Spark.get;

public class Shields
{
    static RepositoryAccess jcenter = new JCenter();

    public static void main(String[] args) {
        get("/ping", (request, response) -> "PONG");
        get("/shield/" + jcenter.getPath(), jcenter::getShield);
    }

}
