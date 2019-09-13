/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.solace.workshops.spring.scs;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.solace.workshop.Tweet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScsProcessorPositiveTests {

	@Autowired
	private Processor processor;

	@Autowired
	private MessageCollector collector;

	@Test
	public void testPositiveTweets() throws InterruptedException {
		String input = "This is an awful vehicle!";
		String expectedOutput = "This is an awesome vehicle!";
		Tweet tweet = new Tweet("username", input);

		Message<Tweet> msgInput = MessageBuilder.withPayload(tweet).build();

		processor.input().send(msgInput);

		Message<?> msgOutput = (Message<?>) collector.forChannel(processor.output()).poll(10, TimeUnit.SECONDS);
		String payload = (msgOutput != null) ? (String) msgOutput.getPayload() : null;

		assertNotNull(payload);
		assertThat((String) payload,
				allOf(containsString(expectedOutput), containsString("username"), containsString("text"),
						containsString("userMentions"), containsString("timestamp"), containsString("hashtags")));

	}
}
