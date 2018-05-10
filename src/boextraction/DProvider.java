package boextraction;

public class DProvider {
	
	private String documentId;
	private String dataProviderId;
	private String dataProviderName;
	private String dataConnectionName;
	private String fhsql;
		
	public DProvider() {
		super();
	}

	public DProvider(String documentId, String dataProviderId, String dataProviderName, String dataConnectionName,String fhsql) {
		super();
		this.documentId = documentId;
		this.dataProviderId = dataProviderId;
		this.dataProviderName = dataProviderName;
		this.dataConnectionName = dataConnectionName;
		this.fhsql = fhsql;
	}
	
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDataProviderId() {
		return dataProviderId;
	}
	public void setDataProviderId(String dataProviderId) {
		this.dataProviderId = dataProviderId;
	}
	public String getFhsql() {
		return fhsql;
	}
	public void setFhsql(String fhsql) {
		this.fhsql = fhsql;
	}
	public String getDataProviderName() {
		return dataProviderName;
	}
	public void setDataProviderName(String dataProviderName) {
		this.dataProviderName = dataProviderName;
	}
	public String getDataConnectionName() {
		return dataConnectionName;
	}
	public void setDataConnectionName(String dataConnectionName) {
		this.dataConnectionName = dataConnectionName;
	}
	
	
}
