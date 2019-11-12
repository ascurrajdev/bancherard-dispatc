package py.com.bancard.microservice.cit.dto;

public class InvRequestDto {

	private String docNumber;

	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	@Override
	public String toString() {
		return "InvRequestDto [docNumber=" + docNumber + "]";
	}
}