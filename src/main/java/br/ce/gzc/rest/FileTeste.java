package br.ce.gzc.rest;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;

public class FileTeste {
    @Test
    public void deveObrigarEnvioArquivo() {
        given()
                .log().all()
        .when()
                .post("https://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(404)
                .body("error", Matchers.is("Arquivo não enviado"));
    }

    @Test
    public void deveFazerUploadArquivo() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/imagem.png"))
        .when()
                .post("https://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.is("imagem.png"));
    }

    @Test
    public void naoDeveFazerUploadArquivoGrande() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/Tela.pdf"))
        .when()
                .post("https://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .time(Matchers.lessThan(2000L))
                .statusCode(413);
    }

    @Test
    public void deveBaixarArquivo() throws IOException {
        byte[] image = given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/download")
                .then()
                .log().all()
                .statusCode(200)
                .extract().asByteArray();
        File imagem = new File("src/main/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

        System.out.println(imagem.length());
        Assert.assertThat(imagem.length(), Matchers.lessThan(100000L));

    }
}
