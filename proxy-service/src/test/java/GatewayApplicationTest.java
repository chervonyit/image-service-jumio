import com.netflix.zuul.context.RequestContext;
import models.Image;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import com.jumio.proxy.GatewayApplication;
import com.jumio.services.ImageService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GatewayApplication.class)
public class GatewayApplicationTest {

    @Autowired
    private TestRestTemplate rest;

    static ConfigurableApplicationContext imageService;

    @BeforeClass
    public static void startBookService() {
        imageService = SpringApplication.run(ImageService.class,
                "--server.port=8090");
    }

    @AfterClass
    public static void closeImageService() {
        imageService.close();
    }

    @Before
    public void setup() {
        RequestContext.testSetCurrentContext(new RequestContext());
    }

    @Test
    public void imageBase64Test() {
        Image image = new Image();
        image.setImage("test");
        Image resp = rest.postForObject("/api/image-base64", image, Image.class);
        assertThat(resp.getImage()).isEqualTo("dGVzdA==");
    }

    @Test
    public void imageTest() {
        Image image = new Image();
        image.setImage("test");
        Image resp = rest.postForObject("/api/image", image, Image.class);
        assertThat(resp.getImage()).isEqualTo("test");
    }
}