package br.ce.gzc.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthTeste {
    @Test
    public void deveAcessarSWAPI() {
        given()
                .log().all()
        .when()
                .get("https://swapi.dev/api/people/1")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.is("Luke Skywalker"));
    }

    @Test
    public void deveObterClima() {
        // API com chave
        given()
                .log().all()
                .queryParam("q", "Porto Alegre")
                .queryParam("appid", "af7ae0e1c9104833b38f9392687eb0cc")
                .queryParam("units", "metric")
        .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.is("Porto Alegre"));
    }

    @Test
    public void naoDeveAcessarSemSenha() {
        // API com chave
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void deveFazerAutenticacaoBasica() {
        // API com chave
        given()
                .log().all()
        .when()
                // usuario e login na URL
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", Matchers.is("logado"));
    }

    @Test
    public void deveFazerAutenticacaoBasica2() {
        // API com chave
        given()
                .log().all()
                .auth().basic("admin", "senha")
        .when()
                // usuario e login na URL
                .get("https://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", Matchers.is("logado"));
    }

    @Test
    public void deveFazerAutenticacaoBasicaChallenge() {
        // API com chave
        given()
                .log().all()
                .auth().preemptive().basic("admin", "senha")
                .when()
                // usuario e login na URL
                .get("https://restapi.wcaquino.me/basicauth2")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", Matchers.is("logado"));
    }

    @Test
    public void deveFazerAutenticacaoComToken() {
        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "gabrielzc@ciandt.com");
        login.put("senha", "123456");

        // extract salva o valor na variável toke para utilizar depois
        String token = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                // usuario e login na URL
                .post("https://barrigarest.wcaquino.me/signin")
        .then()
                .log().all()
                .statusCode(200)
                .extract().path("token");

        given()
                .log().all()
                .header("Authorization", "JWT " + token)
        .when()
                // usuario e login na URL
                .get("https://barrigarest.wcaquino.me/contas")
        .then()
                .log().all()
                .statusCode(200);
    }

}
