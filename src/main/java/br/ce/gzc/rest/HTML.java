package br.ce.gzc.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.hamcrest.xml.HasXPath;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class HTML {
    @Test
    public void deveFazerBuscasComHTML() {
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/v2/users")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.HTML)
            .body("html.body.div.table.tbody.tr.size()", Matchers.is(3))
            .body("html.body.div.table.tbody.tr[1].td[2]", Matchers.is("25"))
            .appendRootPath("html.body.div.table.tbody")
            .body("tr.find{it.toString().startsWith('2')}.td[1]", Matchers.is("Maria Joaquina"));
    }

    @Test
    public void deveFazerBuscasComXpathEmHTML() {
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/v2/users?format=clean")
        .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(Matchers.hasXPath("count(//table/tr)", Matchers.is("4")))
                .body(Matchers.hasXPath("//td[text() = '2']/../td[2]", Matchers.is("Maria Joaquina")));
    }
}
