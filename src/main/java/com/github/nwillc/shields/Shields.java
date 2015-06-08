package com.github.nwillc.shields;

import com.github.nwillc.shields.repositories.JCenter;
import com.github.nwillc.shields.repositories.RepositoryAccess;

import static spark.Spark.get;

public class Shields
{
    static RepositoryAccess[] repos = new RepositoryAccess[]{new JCenter()};

    public static void main(String[] args) {
        get("/ping", (request, response) -> "PONG");
        for (RepositoryAccess repo : repos) {
            get("/shield/" + repo.getPath(), repo::getShield);
            get("/homepage/" + repo.getPath(), repo::getHomepage);
        }
    }

}
