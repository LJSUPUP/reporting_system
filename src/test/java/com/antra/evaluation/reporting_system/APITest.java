package com.antra.evaluation.reporting_system;

import com.antra.evaluation.reporting_system.endpoint.ExcelGenerationController;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.anyString;


public class APITest {
    @Mock
    ExcelService excelService;

    @BeforeEach
    public void configMock() {
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.standaloneSetup(new ExcelGenerationController(excelService));
    }


    @Test
    public void testExcelGeneration() throws Exception {
        given().accept("application/json").contentType(ContentType.JSON).body("{\"description\":\"test\", \"headers\":[\"Name\",\"Age\"], \"data\":[[\"Teresa\",\"5\"],[\"Daniel\",\"1\"]]}").post("/excel").peek().
                then().assertThat()
                .statusCode(200);
    }
    @Test
    public void testFileDownload() throws FileNotFoundException {
        //Mockito.when(excelService.getExcelBodyById(anyString())).thenReturn(new FileInputStream("temp.xlsx"));
        given().accept("application/json").get("/excel/abc123/content").peek().
                then().assertThat()
                .statusCode(200);
    }

    @Test
    public void testListFiles() throws FileNotFoundException {
       // Mockito.when(excelService.getExcelBodyById(anyString())).thenReturn(new FileInputStream("temp.xlsx"));
        given().accept("application/json").get("/excel").peek().
                then().assertThat()
                .statusCode(200);
    }
    @Test
    public void testBatchGeneration() throws FileNotFoundException {
        given().accept("application/json").contentType(ContentType.JSON).body("[{\"description\":\"test\", \"headers\":[\"Name\",\"Age\"], \"data\":[[\"Teresa\",\"5\"],[\"Daniel\",\"1\"]]},{\"description\":\"test\", \"headers\":[\"Name\",\"Age\"], \"data\":[[\"Teresa\",\"5\"],[\"Daniel\",\"1\"]]}]").post("/excel/batch").peek().
                then().assertThat()
                .statusCode(200);
    }
    @Test
    public void testZipDownload() throws FileNotFoundException {
        given().accept("application/json").get("/excel/abc123%12314125/content").peek().
                then().assertThat()
                .statusCode(200);
    }
    @Test
    public void testDeleteFile() throws FileNotFoundException {
        given().accept("application/json").delete("/excel/abc123").peek().
                then().assertThat()
                .statusCode(200);
    }


}
