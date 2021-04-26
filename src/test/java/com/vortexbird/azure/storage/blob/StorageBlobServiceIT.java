package com.vortexbird.azure.storage.blob;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.azure.storage.blob.BlobContainerClient;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContextAZ.xml")
public class StorageBlobServiceIT {
	
	private final static Logger log=LoggerFactory.getLogger(StorageBlobServiceIT.class);
	
	@Autowired
	StorageBlobService storageBlobService;
	
	@Autowired
	VortexAzureCredentials vortexAzureCredentials;
	
	private static String localPath =null;
	private static String downloadLocalPath =null;
	private static String fileName="quickstart" + java.util.UUID.randomUUID() + ".txt";
	
	//@Test
	public void paths()throws Exception {
		File path = new File(StorageBlobServiceIT.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		localPath=path.getAbsolutePath()+File.separator;
		log.info(localPath);
		
		File downloadLocal=new File(localPath+"download");
		downloadLocal.mkdir();
		downloadLocalPath=downloadLocal.getAbsolutePath()+File.separator;
		log.info(downloadLocalPath);
	}

	@Test
	public void test()throws Exception {		
		paths();
		createFile();

		//Create a unique name for the container
		String containerName = "quickstartblobs" + java.util.UUID.randomUUID();

		// Create the container and return a container client object
		BlobContainerClient containerClient = storageBlobService.createBlobContainer(containerName);
		
		assertNotNull("No pudo crear el contenedor", containerClient);
		
		String blobUrl=storageBlobService.uploadFile(containerName,localPath,fileName);
		
		assertNotNull("No pudo hacer el upload", blobUrl);
		
		List<String> listBlobsInContainer=storageBlobService.listBlobsInContainer(containerName);
		
		assertNotNull("No pudo hacer el upload", listBlobsInContainer);
		
		storageBlobService.downloadFileInContainer(containerName,downloadLocalPath, fileName);
		
		storageBlobService.deleteContainer(containerName);		
		
	}
	
	private void createFile() throws Exception{
		// Create a local file in the ./data/ directory for uploading and downloading
		// Write text to the file
		FileWriter writer = new FileWriter(localPath + fileName, true);
		writer.write("Hello, World!");
		writer.close();		
	}

}
