/*-
 * #%L
 * Elastic APM Java agent
 * %%
 * Copyright (C) 2018 - 2019 Elastic and contributors
 * %%
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * #L%
 */
package co.elastic.apm.agent.jdbc.signature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SignatureParserTest {

    private SignatureParser signatureParser;
    private JsonNode testCases;

    @BeforeEach
    void setUp() throws Exception {
        signatureParser = new SignatureParser();
        testCases = new ObjectMapper().readTree(getClass().getResource("/signature_tests.json"));
    }

    @Test
    void testScanner() {
        for (JsonNode testCase : testCases) {
            System.out.println(testCase);
            assertThat(getSignature(testCase.get("input").asText())).isEqualTo(testCase.get("output").asText());
        }
    }

    String getSignature(String query) {
        final StringBuilder sb = new StringBuilder();
        signatureParser.querySignature(query, sb, false);
        return sb.toString();
    }
}
