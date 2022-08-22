package br.ce.gzc.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class OlaMundoTeste {

    @Test
    public void testeOlaMundo() {
        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        Assert.assertTrue((response.getBody().asString().equals("Ola Mundo!")));
        Assert.assertTrue("O status deve ser 200", response.statusCode() == 200);

        Assert.assertEquals(200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured() {
        get("http://restapi.wcaquino.me/ola").then().statusCode(200);

        given()
                // Pré condições
        .when()
                // Ações
                .get("http://restapi.wcaquino.me/ola")
        .then()
                // Verififações
                .statusCode(200);
    }

    @Test
    public void devoConhecerMatchersHamcrest() {
        Assert.assertThat("Maria", Matchers.is("Maria"));
        Assert.assertThat(12, Matchers.isA(Integer.class));
        Assert.assertThat(128d, Matchers.greaterThan(120d));
        Assert.assertThat(128d, Matchers.lessThan(130d));

        List<Integer> impares = Arrays.asList(1,3,5,7,9);
        Assert.assertThat(impares, Matchers.hasSize(5));
        Assert.assertThat(impares, Matchers.contains(1, 3, 5, 7, 9));
        Assert.assertThat(impares, Matchers.containsInAnyOrder(1, 9, 5, 3, 7));
        Assert.assertThat(impares, Matchers.hasItem(3));
        Assert.assertThat(impares, Matchers.hasItems(1, 5));

        Assert.assertThat("Maria", Matchers.is(Matchers.not("João")));
        Assert.assertThat("Maria", Matchers.anyOf(Matchers.is("Maria"), Matchers.is("João")));
        Assert.assertThat("Joaquina", Matchers.allOf(Matchers.startsWith("Joa"), Matchers.endsWith("ina"), Matchers.containsString("qui")));
    }

    @Test
    public void devoValidarBody() {
        given()
        .when()
                .get("http://restapi.wcaquino.me/ola")
        .then()
                .statusCode(200)
                .body(Matchers.is("Ola Mundo!"))
                .body(Matchers.containsString("Mundo"))
                .body(Matchers.is(Matchers.not(Matchers.nullValue())));
    }

}
