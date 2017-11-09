package org.rtb.vexing.handler;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;


public class StatusHandlerTest {

    @Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private RoutingContext routingContext;
    @Mock
    private HttpServerResponse httpResponse;

    @Test
    public void shouldRespondHttp200Ok() {
        // given
        given(routingContext.response()).willReturn(httpResponse);

        // when
        new StatusHandler().status(routingContext);

        // then
        verify(httpResponse, only()).end();
    }
}