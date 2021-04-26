package com.vortexbird.azure.storage.blob;

import java.util.List;

import com.azure.storage.blob.BlobContainerClient;

public interface StorageBlobService {
	
	public  BlobContainerClient createBlobContainer(String containerName)throws Exception;
	
	public  void deleteContainer(String containerName) throws Exception;
		
	public  void downloadFileInContainer(String containerName,String localPath, String fileName) throws Exception;

	public  List<String> listBlobsInContainer(String containerName) throws Exception;

	//TODO se debe cambia para que solo cargue el archivo de una ruta
	public  String uploadFile(String containerName, String localPath, String fileName) throws Exception;

}
