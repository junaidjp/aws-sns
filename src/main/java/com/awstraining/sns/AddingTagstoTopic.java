package com.awstraining.sns;

//IGNORE THIS PROGRAM 

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.model.Tag;
import com.amazonaws.services.kms.model.TagResourceRequest;
import com.amazonaws.services.sns.model.*;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class AddingTagstoTopic {
	static String ACCESS_KEY = "AKIAJMMYMHBAO2QUZVKQ";
	static String SECRET_KEY = "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";
	final static String topicArn = "arn:aws:sns:us-east-2:264455084456:MyTopic";
	public static void main(String args[]) {

		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_2)
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		// Subscribe an email endpoint to an Amazon SNS topic.
		/**
		final com.amazonaws.services.kms.model.Tag tagTeam = new com.amazonaws.services.kms.model.Tag();
		tagTeam.setTagKey("Team");
		tagTeam.setTagValue("Development");
		
		final com.amazonaws.services.kms.model.Tag[] tagList = new  com.amazonaws.services.kms.model.Tag[2];
		tagList[0] = tagTeam;
		
		final TagResourceRequest tagResourceRequest = new TagResourceRequest();
		tagResourceRequest.withTags(tagList);
		**/
		
		
		final Tag tagTeam = new Tag();
		tagTeam.withTagKey("Team").withTagValue("Development");
		
		
		     
		final List<Tag> tagList = new ArrayList<Tag>();
		tagList.add(tagTeam);
		
		final TagResourceRequest tagResourceRequest =  new TagResourceRequest();

		
		final SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, "email",
		"junaid.jp@gmail.com");
		snsClient.subscribe(subscribeRequest);
		// Print the request ID for the SubscribeRequest action.
		System.out.println("SubscribeRequest: " +
		snsClient.getCachedResponseMetadata(subscribeRequest));
		System.out.println(" check your email. To confirm the subscription,");
		
		

	}
}
