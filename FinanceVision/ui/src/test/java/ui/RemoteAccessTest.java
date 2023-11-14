package ui;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.gson.Gson;
import core.Account;
import core.User;
import filesaving.JsonFileSaving;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testclass for RemoteAccess. Uses WireMockServer to mock the server-responses to isolate
 * testing the logic in the RemoteAccess class.
 */
public class RemoteAccessTest {

    private WireMockConfiguration config;
    private WireMockServer mockserver;
    private Gson gson = new JsonFileSaving().createGson();

    private RemoteFinanceVisionModelAccess access;

    /**
     * Starts the WireMock-server.
     *
     * @throws URISyntaxException if the URI has invalid syntax
     */
    @BeforeEach
    public void startWireMockServerAndSetup() throws URISyntaxException {
        config = WireMockConfiguration.wireMockConfig().port(8089);
        mockserver = new WireMockServer(config.portNumber());
        mockserver.start();
        WireMock.configureFor("localhost", config.portNumber());
        access = new RemoteFinanceVisionModelAccess(new URI("http://localhost:" + mockserver.port() + "/fv/"));
    }

    @AfterEach
    public void stopWireMockServer() {
        mockserver.stop();
    }

    @Test
    public void testIsConnected() throws Exception {
        stubFor(get(urlEqualTo("/fv/"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("true")
        )
        );
        Boolean succes = access.isConnected();
        assertTrue(succes);
    }

    @Test 
    public void testPutUser() throws Exception {
        User user = new User(
            "testuser", "password", "Test User", "test@g.com", new Account(1000));
        String json = gson.toJson(user, User.class);
        stubFor(put(urlEqualTo("/fv/user/testuser"))
            .withHeader("Accept", equalTo("application/json"))
            .withRequestBody(equalTo(json))
            .willReturn(aResponse()
                .withStatus(200)
        )
        );
        assertDoesNotThrow(() -> access.putUser(user));
    }

    @Test
    public void testRemoveUser() {
        User user = new User(
            "testuser", "password", "Test User", "test@g.com", new Account(1000));
        String json = gson.toJson(user, User.class);
        stubFor(delete(urlEqualTo("/fv/user/testuser"))
            .withHeader("Accept", equalTo("application/json"))
            .withRequestBody(equalTo(json))
            .willReturn(aResponse()
                .withStatus(200)
        )
        );
        assertDoesNotThrow(() -> access.removeUser(user));
    }

    @Test
    public void testContainsUser() throws Exception {
        stubFor(get(urlEqualTo("/fv/user/testuser/exists"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("true")
        )
        );
        Boolean succes = access.containsUser("testuser");
        assertTrue(succes);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User(
            "testuser", "password", "Test User", "test@g.com", new Account(1000));
        String json = gson.toJson(user, User.class);
        stubFor(get(urlEqualTo("/fv/user/testuser?password=password"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(json)
        )
        );
       
        User responseUser = access.getUser("testuser", "password");
        assertTrue(responseUser.equals(user));
    }

    

    
}
