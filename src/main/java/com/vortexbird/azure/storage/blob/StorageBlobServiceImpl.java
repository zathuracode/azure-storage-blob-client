package com.vortexbird.azure.storage.blob;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

@Component
public class StorageBlobServiceImpl implements StorageBlobService {
	
	private final static Logger log=LoggerFactory.getLogger(StorageBlobServiceImpl.class);
	
	@Autowired
	VortexAzureCredentials vortexAzureCredentials;
	
	public  BlobContainerClient createBlobContainer(String containerName)throws Exception{
		// Create a BlobServiceClient object which will be used to create a container client
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(vortexAzureCredentials.getAzureStorageConnectionString()).buildClient();
		// Create the container and return a container client object
		BlobContainerClient containerClient = blobServiceClient.createBlobContainer(containerName);
		
		return containerClient;		
	}

	@Override
	public void deleteContainer(String containerName) throws Exception {
		BlobContainerClient containerClient = getContainerClient(containerName);
		
		log.info("Deleting blob container...");
			containerClient.delete();
		log.info("blob container Deleted");
	}

	

	@Override
	public void downloadFileInContainer(String containerName, String localPath, String fileName)throws Exception {
		// Get a reference to a blob
		BlobContainerClient containerClient = getContainerClient(containerName);
		BlobClient blobClient = containerClient.getBlobClient(fileName);
		log.info("\nDownloading blob to\n\t " + localPath + fileName);
		blobClient.downloadToFile(localPath + fileName);
	}

	@Override
	public List<String> listBlobsInContainer(String containerName) throws Exception {
		BlobContainerClient containerClient = getContainerClient(containerName);
		List<String> listBlobsInContainer=new ArrayList<>();
		for (BlobItem blobItem : containerClient.listBlobs()) {
			listBlobsInContainer.add(blobItem.getName());
		}
		return listBlobsInContainer;
	}

	@Override
	public String uploadFile(String containerName, String localPath, String fileName)throws Exception {
		BlobContainerClient containerClient = getContainerClient(containerName);
		
		BlobClient blobClient = containerClient.getBlobClient(fileName);
		log.info("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

		// Upload the blob
		blobClient.uploadFromFile(localPath + fileName);
		
		return blobClient.getBlobUrl().toString();		
	}
	
	private BlobContainerClient getContainerClient(String containerName) {
		// Create a BlobServiceClient object which will be used to create a container client
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(vortexAzureCredentials.getAzureStorageConnectionString()).buildClient();
		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
		return containerClient;
	}
	
	

}
