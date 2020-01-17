package com.cloud.zuul.swagger;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/10/29 10:27
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Component
@Primary
public class SwaggerApi implements SwaggerResourcesProvider {

	@Override
	public List<SwaggerResource> get() {
		List resources = new ArrayList<>();
		resources.add(swaggerResource("api-systemManagement-impl", "/systemManagement/v2/api-docs", "2.0"));
		resources.add(swaggerResource("api-order-impl", "/order/v2/api-docs", "2.0"));
		resources.add(swaggerResource("api_garden_impl", "/garden/v2/api-docs", "2.0"));
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}

}
