/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.apache.hop.opentelemetry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/** Configuration properties for using OTLP. */
public class OpenTelemetryConfig {

  @Setter @Getter private String serviceName;

  @Getter @Setter private String endpoint;

  /**
   * Set the OLTP transport protocol.
   *
   * <p>Options MUST be one of:
   *
   * <ul>
   *   <li>grpc
   *   <li>http/protobuf
   * </ul>
   */
  @Getter @Setter private String protocol;

  /** Custom HTTP headers you want to pass to the collector, for example auth headers. */
  private Map<String, String> headers;

  @Getter @Setter private Duration timeout;

  public OpenTelemetryConfig() {
    super();
    this.headers = new HashMap<>();
    this.timeout = Duration.ofSeconds(10);
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getHeadersAsSrtring() {
    StringBuilder builder = new StringBuilder();
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      if (builder.length() > 0) {
        builder.append(',');
      }
      builder.append(StringUtils.trim(entry.getKey()));
      builder.append('=');
      builder.append(StringUtils.trim(entry.getValue()));
    }

    return builder.toString();
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public void setHeadersAsString(String str) {
    this.headers = new HashMap<>();
    if (str == null) {
      return;
    }

    for (String header : str.split(",", 0)) {
      String[] pair = header.split("=", 2);
      if (pair.length == 2) {
        String name = pair[0];
        String value = pair[1];
        headers.put(name, value);
      }
    }
  }
}
