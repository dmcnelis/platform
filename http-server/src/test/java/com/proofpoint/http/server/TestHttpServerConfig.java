/*
 * Copyright 2010 Proofpoint, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.proofpoint.http.server;

import com.google.common.collect.ImmutableMap;
import com.proofpoint.configuration.testing.ConfigAssertions;
import com.proofpoint.units.DataSize;
import com.proofpoint.units.DataSize.Unit;
import com.proofpoint.units.Duration;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.proofpoint.configuration.testing.ConfigAssertions.assertFullMapping;
import static com.proofpoint.configuration.testing.ConfigAssertions.assertLegacyEquivalence;
import static com.proofpoint.configuration.testing.ConfigAssertions.assertRecordedDefaults;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TestHttpServerConfig
{
    @Test
    public void testDefaults()
    {
        assertRecordedDefaults(ConfigAssertions.recordDefaults(HttpServerConfig.class)
                .setHttpEnabled(true)
                .setHttpPort(8080)
                .setHttpAcceptQueueSize(8000)
                .setHttpsEnabled(false)
                .setHttpsPort(8443)
                .setKeystorePath("etc/keystore.jks")
                .setKeystorePassword("keystore")
                .setLogPath("var/log/http-request.log")
                .setLogEnabled(true)
                .setLogMaxSegmentSize(new DataSize(100, Unit.MEGABYTE))
                .setLogMaxHistory(30)
                .setLogMaxTotalSize(new DataSize(1, Unit.GIGABYTE))
                .setHttpAcceptorThreads(null)
                .setHttpSelectorThreads(null)
                .setHttpsAcceptorThreads(null)
                .setHttpsSelectorThreads(null)
                .setMinThreads(2)
                .setMaxThreads(200)
                .setThreadMaxIdleTime(new Duration(1, TimeUnit.MINUTES))
                .setNetworkMaxIdleTime(new Duration(200, SECONDS))
                .setUserAuthFile(null)
                .setAdminEnabled(true)
                .setAdminPort(0)
                .setAdminMinThreads(2)
                .setAdminMaxThreads(200)
                .setStopTimeout(new Duration(30, SECONDS))
                .setMaxRequestHeaderSize(null)
                .setHttp2MaxConcurrentStreams(16384)
                .setShowStackTrace(false)
        );
    }
 
    @Test
    public void testExplicitPropertyMappings()
    {
        Map<String, String> properties = new ImmutableMap.Builder<String, String>()
                .put("http-server.http.enabled", "false")
                .put("http-server.http.port", "1")
                .put("http-server.accept-queue-size", "1024")
                .put("http-server.https.enabled", "true")
                .put("http-server.https.port", "2")
                .put("http-server.https.keystore.path", "/keystore")
                .put("http-server.https.keystore.key", "keystore password")
                .put("http-server.log.path", "/log")
                .put("http-server.log.enabled", "false")
                .put("http-server.log.max-size", "1GB")
                .put("http-server.log.max-history", "25")
                .put("http-server.log.max-total-size", "5GB")
                .put("http-server.http.acceptor-threads", "10")
                .put("http-server.http.selector-threads", "11")
                .put("http-server.https.acceptor-threads", "12")
                .put("http-server.https.selector-threads", "13")
                .put("http-server.threads.min", "100")
                .put("http-server.threads.max", "500")
                .put("http-server.threads.max-idle-time", "10m")
                .put("http-server.net.max-idle-time", "20m")
                .put("http-server.auth.users-file", "/auth")
                .put("http-server.admin.enabled", "false")
                .put("http-server.admin.port", "3")
                .put("http-server.admin.threads.min", "3")
                .put("http-server.admin.threads.max", "4")
                .put("http-server.stop-timeout", "1500ms")
                .put("http-server.max-request-header-size", "32kB")
                .put("http-server.http2.max-concurrent-streams", "1234")
                .put("http-server.show-stack-trace", "true")
                .build();

        HttpServerConfig expected = new HttpServerConfig()
                .setHttpEnabled(false)
                .setHttpPort(1)
                .setHttpAcceptQueueSize(1024)
                .setHttpsEnabled(true)
                .setHttpsPort(2)
                .setKeystorePath("/keystore")
                .setKeystorePassword("keystore password")
                .setLogPath("/log")
                .setLogEnabled(false)
                .setLogMaxSegmentSize(new DataSize(1, Unit.GIGABYTE))
                .setLogMaxHistory(25)
                .setLogMaxTotalSize(new DataSize(5, Unit.GIGABYTE))
                .setHttpAcceptorThreads(10)
                .setHttpSelectorThreads(11)
                .setHttpsAcceptorThreads(12)
                .setHttpsSelectorThreads(13)
                .setMinThreads(100)
                .setMaxThreads(500)
                .setThreadMaxIdleTime(new Duration(10, TimeUnit.MINUTES))
                .setNetworkMaxIdleTime(new Duration(20, TimeUnit.MINUTES))
                .setMaxRequestHeaderSize(new DataSize(32, DataSize.Unit.KILOBYTE))
                .setUserAuthFile("/auth")
                .setAdminEnabled(false)
                .setAdminPort(3)
                .setAdminMinThreads(3)
                .setAdminMaxThreads(4)
                .setStopTimeout(new Duration(1500, MILLISECONDS))
                .setHttp2MaxConcurrentStreams(1234)
                .setShowStackTrace(true);

        assertFullMapping(properties, expected);
    }

    @Test
    public void testLegacyProperties()
    {
        assertLegacyEquivalence(HttpServerConfig.class, ImmutableMap.<String, String>of());
    }
}
