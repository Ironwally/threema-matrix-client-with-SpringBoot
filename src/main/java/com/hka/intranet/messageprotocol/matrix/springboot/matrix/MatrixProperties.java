package com.hka.intranet.messageprotocol.matrix.springboot.matrix;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Matrix client access.
 */
@Configuration
@ConfigurationProperties(prefix = "matrix")
public class MatrixProperties {

    //TODO MatrixProperties exists already in Matrix-Communication-Client SDK! Need to somehow add to class not make my own

  /** Base URL of the homeserver, e.g. http://localhost:8008/_matrix/client/v3 */
  private String baseUrl;

  /** Access token (user or appservice); if absent we rely on environment variable MATRIX_ACCESS_TOKEN. */
  private String accessToken;

  public String getBaseUrl() {
    if (baseUrl == null || baseUrl.isBlank()) {
      // Allow direct env override
      String envBase = System.getenv("MATRIX_BASE_URL");
      if (envBase != null && !envBase.isBlank()) {
        return envBase;
      }
      // Compose from MATRIX_HOST if present
      String host = System.getenv().getOrDefault("MATRIX_HOST", "localhost");
      return "http://" + host + ":8008/_matrix/client/v3";
    }
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getAccessToken() {
    if (accessToken == null || accessToken.isBlank()) {
       String localAccessToken = System.getenv("MATRIX_ACCESS_TOKEN");
       
       if (localAccessToken == null || localAccessToken.isBlank()) {
           localAccessToken = //TODO MatrixProperties exists already in Matrix-Communication-Client SDK! Need to somehow add to class not make my own;
       }
    }

    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
