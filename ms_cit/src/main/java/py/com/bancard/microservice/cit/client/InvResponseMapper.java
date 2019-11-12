package py.com.bancard.microservice.cit.client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import py.com.bancard.microservice.cit.dto.InvResponseDto;

public class InvResponseMapper implements RowMapper<InvResponseDto> {

    @Override
    public InvResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        InvResponseDto response = new InvResponseDto();
        response.setNroOperacion(rs.getInt("nroOperacion"));
        response.setDesOperacion(rs.getString("desOperacion"));
        response.setNroCuota(rs.getInt("nroCuota"));
        response.setPunitorio(rs.getString("punitorio"));
        response.setMora(rs.getString("mora"));
        response.setInteres(rs.getString("interes"));
        response.setCapital(rs.getString("capital"));
        response.setGastos(rs.getString("gastos"));
        response.setIva10(rs.getString("iva10"));
        response.setIva5(rs.getString("iva5"));
        response.setTotalDeuda(rs.getString("totalDeuda"));
        response.setMoneda(rs.getString("moneda"));
        response.setFechaVencimiento(rs.getDate("fechaVencimiento"));
        response.setApenom(rs.getString("apenom"));
        return response;

    }
}
