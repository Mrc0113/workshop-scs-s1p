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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.solace.workshop.Tweet;

@SpringBootApplication
//@EnableBinding(Sink.class)
public class ScsSinkBossideas {
	
	private static final Logger log = LoggerFactory.getLogger(ScsSinkBossideas.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ScsSinkBossideas.class, args);
	}

//	@StreamListener(Sink.INPUT)
//	public void sink(Tweet tweet) {
//		log.info("Received: " + tweet.toString());
//	}
}
