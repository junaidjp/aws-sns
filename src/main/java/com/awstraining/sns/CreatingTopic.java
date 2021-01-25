package com.awstraining.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClient;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;

public class CreatingTopic {
	static String ACCESS_KEY = "AKIAJMMYMHBAO2QUZVKQ";
	static String SECRET_KEY = "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";
	
	public static void main(String args[]) {

		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_2)
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		final CreateTopicRequest createTopicRequest = new CreateTopicRequest("MyTopic");
		final CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);

		// Print the topic ARN.
		System.out.println("TopicArn:" + createTopicResult.getTopicArn());
		    
		// Print the request ID for the CreateTopicRequest action.
		System.out.println("CreateTopicRequest: " + snsClient.getCachedResponseMetadata(createTopicRequest));
		
		
		

	}
}