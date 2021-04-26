package com.vortexbird.azure.storage.blob;




/**
 * Carga las credenciales configuradas en 
 * @author dgomez@vortexbird.com
 *
 */
public class VortexAzureCredentials{
	
	private String azureStorageConnectionString;

	public String getAzureStorageConnectionString() {
		return azureStorageConnectionString;
	}

	public void setAzureStorageConnectionString(String azureStorageConnectionString) {
		this.azureStorageConnectionString = azureStorageConnectionString;
	}

	public VortexAzureCredentials(String azureStorageConnectionString) {
		super();
		this.azureStorageConnectionString = azureStorageConnectionString;
	}

	public VortexAzureCredentials() {
		super();
	}
	
}
