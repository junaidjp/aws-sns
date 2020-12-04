package com.awstraining.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

//A2p
public class SNSApplication2Person {

	static String ACCESS_KEY = "AKIAJMMYMHBAO2QUZVKQ";
	static String SECRET_KEY = "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";
	final static String topicArn = "arn:aws:sns:us-east-2:264455084456:MyTopic";
	
	public static void main(String args[]) {

		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_2)
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		// Subscribe an email endpoint to an Amazon SNS topic.
		String msg="TestingPayloadPublishing To Topic";
		
		final PublishRequest publishRequest = new PublishRequest(topicArn, msg);
		final PublishResult publishResponse = snsClient.publish(publishRequest);
		// Print the MessageId of the message.
		System.out.println("MessageId: " + publishResponse.getMessageId());
}
}