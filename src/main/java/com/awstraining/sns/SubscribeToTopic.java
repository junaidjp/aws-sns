package com.awstraining.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SubscribeToTopic {
	
	
	static String ACCESS_KEY = "AKIAJMMYMHBAO2QUZVKQ";
	static String SECRET_KEY = "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";
	final static String topicArn = "arn:aws:sns:us-east-2:264455084456:MyTopic";
	public static void main(String args[]) {

		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_2)
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		// Subscribe an email endpoint to an Amazon SNS topic.
		
	
		final SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, "email",
		"junaid.jp@gmail.com");
		snsClient.subscribe(subscribeRequest);
		// Print the request ID for the SubscribeRequest action.
		System.out.println("SubscribeRequest: " +
		snsClient.getCachedResponseMetadata(subscribeRequest));
		System.out.println(" check your email. To confirm the subscription,");
		
		

	}

}
