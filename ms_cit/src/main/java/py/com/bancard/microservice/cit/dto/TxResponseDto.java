package py.com.bancard.microservice.cit.dto;

public class TxResponseDto {
	private String codigoError;
	private String descripcionError;

	public String getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	@Override
	public String toString() {
		return "TxResponseDto [codigoError=" + codigoError + ", descripcionError=" + descripcionError + "]";
	}
}
