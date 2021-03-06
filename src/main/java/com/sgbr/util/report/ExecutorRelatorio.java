package com.sgbr.util.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.jdbc.Work;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ExecutorRelatorio implements Work {
	
	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	
	private String nomeArquivoSaida;
	
	public ExecutorRelatorio(String caminhoRelatorio, HttpServletResponse response, Map<String, Object> parametros, String nomeArquivoSaida) 
	{
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
	}
	@Override
	public void execute(Connection connection) throws SQLException{
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
			
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
			
			JRExporter exportador = new JRPdfExporter();
			exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
						
			System.out.println("Relatório impresso:" + print.getPages().size());
			exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);
	
			response.setContentType("application/pdf");
           // Serve para fazer o download automatico do PDF
		   // response.setHeader("Content-Disposition", "attachment; filename=\"" + this.nomeArquivoSaida + "\"" );
			exportador.exportReport();
		} catch (Exception e) {
			throw new SQLException("Erro ao executar relatorio" + this.caminhoRelatorio, e);
		}
	}
}
