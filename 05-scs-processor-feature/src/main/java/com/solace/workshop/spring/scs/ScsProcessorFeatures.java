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

package com.solace.workshop.spring.scs;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.solace.workshop.Tweet;
import com.solace.workshop.spring.scs.ScsProcessorFeatures.ProcessorOneInTwoOutBinding;

@SpringBootApplication
@EnableBinding(ProcessorOneInTwoOutBinding.class)
public class ScsProcessorFeatures {
	
	private static final Logger log = LoggerFactory.getLogger(ScsProcessorFeatures.class);

	
	@Autowired 
	private ProcessorOneInTwoOutBinding processor;
	
	private String feature = "#NewFeature";
	
	
	public static void main(String[] args) {
		SpringApplication.run(ScsProcessorFeatures.class, args);
	}
	
	@StreamListener(ProcessorOneInTwoOutBinding.INPUT)
	public void handle(Tweet tweet) {
		ArrayList<String> hashTags = tweet.getHashtags();
		if(hashTags.contains(feature) ) {
			log.info("Routing to output \"feature\" channel based on hashtags:"+tweet.getHashtags().toString());
			processor.outputFeature().send(message(tweet));
		} else {
			log.info("Routing to output \"NO feature\" channel based on hashtags:"+tweet.getHashtags().toString());
			processor.outputNoFeature().send(message(tweet));
		}
		
	}
	

	private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }


	/* 
	 * Custom Processor Binding Interface to allow for multiple outputs
	 */
	public interface ProcessorOneInTwoOutBinding {
		String INPUT = "input";

	    @Input
	    SubscribableChannel input();

	    @Output
	    MessageChannel outputFeature();

	    @Output
	    MessageChannel outputNoFeature();

	}

}
