package com.sud.aws_appconfig_demo.model.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sud.aws_appconfig_demo.model.OpcoConfiguration;

import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationRequest;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

@Configuration
public class LoadAWSAppConfig implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(LoadAWSAppConfig.class);
	public static final List<OpcoConfiguration> opcoConfigList = new ArrayList<>();

	@Autowired
	private Environment env;

	private void loadOpcoConfiguration() {
		loadAWSAppOpcoConfiguration();
	}

	private void loadAWSAppOpcoConfiguration() {
		logger.info("loadAWSAppOpcoConfiguration() : start at : {}", new Date());
		Optional<GetConfigurationResponse> response = getConfiguration();

		if (Optional.ofNullable(response).isPresent()) {
			String appConfigResponse = response.get().content().asUtf8String();
			try {
				final JSONArray jsonResponseObject = new JSONObject(appConfigResponse)
						.getJSONArray(env.getProperty("json.array.name"));

				Iterator<Object> it = jsonResponseObject.iterator();

				while (it.hasNext()) {
					OpcoConfiguration opcoConfig = new ObjectMapper().readValue(it.next().toString(),
							OpcoConfiguration.class);
					opcoConfigList.add(opcoConfig);

					logger.info("Result : "+ opcoConfigList);
					logger.info("All Initial Opco Configuration loaded successfully From AWS AppConfig !!!");
				}
			} catch (JsonMappingException e) {
				logger.info("Exception in loadAWSAppOpcoConfiguration() : {}", e.getMessage());
			} catch (JsonProcessingException e) {
				logger.info("Exception in loadAWSAppOpcoConfiguration() : {}", e.getMessage());
			} catch (JSONException e) {
				logger.info("Exception in loadAWSAppOpcoConfiguration() : {}", e.getMessage());
			}

		}
		logger.info("loadAWSAppOpcoConfiguration() : start at : {}", new Date());
	}

	@SuppressWarnings("deprecation")
	private Optional<GetConfigurationResponse> getConfiguration() {
		try {
			AppConfigClient client = AppConfigClient.builder().build();
			return Optional.of(client.getConfiguration(
					GetConfigurationRequest.builder().application(env.getProperty("appconfig.application"))
							.environment(env.getProperty("appconfig.environment"))
							.configuration(env.getProperty("appconfig.config"))
							.clientId(env.getProperty("app.config.client.id")).build()));
		} catch (Exception e) {
			logger.info("Exception in getConfiguration() : {}", e.getMessage());
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loadOpcoConfiguration();
	}

}
