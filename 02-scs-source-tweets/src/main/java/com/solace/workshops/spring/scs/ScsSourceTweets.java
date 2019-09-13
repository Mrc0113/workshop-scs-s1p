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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

import com.solace.workshop.Tweet;

@SpringBootApplication
@EnableBinding(Source.class)
public class ScsSourceTweets {
	private static final Logger log = LoggerFactory.getLogger(ScsSourceTweets.class);

	private ArrayList<Tweet> tweetList;
	private Iterator<Tweet> tweetIterator;

	// Default constructor
	public ScsSourceTweets() {
		this("src/main/resources/static/canned_tweets.txt");
	}

	// Constructor that takes in a tweet file
	public ScsSourceTweets(String tweetFile) {
		String filename = tweetFile;
		if (null == filename) {
			throw new IllegalArgumentException("Must pass in a tweet file");
		}
		
		if (!new File(filename).isFile()) {
			//Passed in file doesn't exist; try default
			filename = "BOOT-INF/classes/static/canned_tweets.txt";
		}

		tweetList = readFile(filename);
		tweetIterator = tweetList.iterator();
		log.info("Loaded " + tweetList.size() + " tweet templates to send");
	}

	public static void main(String[] args) {
		SpringApplication.run(ScsSourceTweets.class, args);
	}

	// Define an output to send to; every fixedRate ms
	@InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedRate = "1000"))
	public Tweet sendTweet() {
		Tweet nextTweet;
		if (tweetIterator.hasNext()) {
			nextTweet = tweetIterator.next();
		} else {
			// No more tweets left in our list; start over from beginning
			tweetIterator = tweetList.iterator();
			nextTweet = tweetIterator.next();
		}
		nextTweet.updateTimestamp();
		nextTweet.updateUuid();
		log.info("Sending " + nextTweet.toString());
		return nextTweet;
	}

	// Create our list of tweets from the file 'filename'
	private ArrayList<Tweet> readFile(String filename) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			// Create a new tweet object for each line in the file
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(":::");
				tweets.add(new Tweet(parts[0], parts[1]));
			}
			reader.close();
			return tweets;
		} catch (IOException e) {
			log.error("Exception occurred trying to read " + filename);
			e.printStackTrace();
			return null;
		}
	}
}

