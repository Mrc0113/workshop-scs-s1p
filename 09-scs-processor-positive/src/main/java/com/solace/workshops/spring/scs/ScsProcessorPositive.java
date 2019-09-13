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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import com.solace.workshop.Tweet;

@SpringBootApplication
@EnableBinding(Processor.class)
public class ScsProcessorPositive {
	private static final Logger log = LoggerFactory.getLogger(ScsProcessorPositive.class);
	private static final Map<String, String> negToPosMap;

	//TODO - Add replacement positive words below! Have some fun!!
	static {
		negToPosMap = new HashMap<String, String>();
		negToPosMap.put("awful", "awesome"); // DON'T CHANGE THIS ONE, but if you want to then change the "expectedOutput" in the unit test
		negToPosMap.put("sucks", "");
		negToPosMap.put("worst", "");
		negToPosMap.put("hate", "");
		negToPosMap.put("lit on fire", "");
		negToPosMap.put("disappointing", "");
		negToPosMap.put("exploded in", "");
		negToPosMap.put("horrendous", "");
		negToPosMap.put("wrong", "");
		negToPosMap.put("lacking", "");
		negToPosMap.put("broke down", "");
		negToPosMap.put("drove off", "");
	}

	public static void main(String[] args) {
		SpringApplication.run(ScsProcessorPositive.class, args);
	}

	// We define an INPUT to receive and an OUTPUT to send to!
	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Tweet handle(Tweet tweet) {
		log.info("Input: " + tweet.toString());
		// Note that even though we output a Tweet POJO under the covers the message
		// will be in JSON so it doesn't have to be received by a Java App
		return turnPositive(tweet);
	}

	// Change negative words to positive
	private Tweet turnPositive(Tweet tweet) {
		String tweetText = tweet.getText();
		for (Map.Entry<String, String> entry : negToPosMap.entrySet()) {
			tweetText = tweetText.replaceAll("(?i)" + entry.getKey(), entry.getValue());
		}
		tweet.setText(tweetText);
		log.info("Output:" + tweet.toString());
		return tweet;
	}
	
//	static {
//		negToPosMap = new HashMap<String, String>();
//		negToPosMap.put("awful", "awesome");
//		negToPosMap.put("sucks", "rocks");
//		negToPosMap.put("worst", "best");
//		negToPosMap.put("hate", "love");
//		negToPosMap.put("lit on fire", "parked itself");
//		negToPosMap.put("disappointing", "exciting");
//		negToPosMap.put("exploded in", "made it up");
//		negToPosMap.put("horrendous", "amazing");
//		negToPosMap.put("wrong", "correct");
//		negToPosMap.put("lacking", "abundant");
//		negToPosMap.put("broke down", "saved me");
//		negToPosMap.put("drove off", "parked");
//	}

}
