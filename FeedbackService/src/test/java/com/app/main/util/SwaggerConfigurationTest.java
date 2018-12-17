package com.app.main.util;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import springfox.documentation.spring.web.plugins.Docket;


@RunWith(MockitoJUnitRunner.class)
public class SwaggerConfigurationTest {

	@InjectMocks
	private SwaggerConfiguration swaggerConfiguration;

	@Test
	public void testProduceApi() {
		assertThat(swaggerConfiguration.produceApi(), isA(Docket.class));
	}
}
